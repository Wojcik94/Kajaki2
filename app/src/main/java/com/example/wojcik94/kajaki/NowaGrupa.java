package com.example.wojcik94.kajaki;

import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;

public class NowaGrupa extends AppCompatActivity {

    private int hour, minute, date;
    //private int telefon, liczba_kajakow, stan;
    TextView godzina;
    EditText nazwisko, telefon, liczbaKajaków, uwagi;

    Spinner spinner1, spinner2;

    private static final String DB_URL = "jdbc:mysql://sql11.freemysqlhosting.net/sql11174569";
    private static final String USER = "sql11174569";
    private static final String PASS = "ZXfvTXCqCn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_grupa);

        Calendar c = Calendar.getInstance();
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.date=c.get(Calendar.YEAR)*10000+(c.get(Calendar.MONTH)+1)*100+c.get(Calendar.DAY_OF_MONTH);

        godzina = (TextView) findViewById(R.id.godzina);


        godzina.setText(hour+":"+String.format("%02d", minute));

        nazwisko = (EditText) findViewById(R.id.nazwisko);
        telefon = (EditText) findViewById(R.id.telefon);
        liczbaKajaków = (EditText) findViewById(R.id.liczba_kajakow);
        uwagi = (EditText) findViewById(R.id.uwagi);

        spinner1 = (Spinner) findViewById(R.id.trasa);
        spinner2 = (Spinner) findViewById(R.id.stan);
    }

    public void entry (View view) {
        Send objSend = new Send ();
        objSend.execute("");
    }

    public void check(View view){
        Toast.makeText(NowaGrupa.this,
                String.valueOf(spinner1.getSelectedItem()),
                Toast.LENGTH_SHORT).show();
    }

    public void setTime(View view){
        new TimePickerDialog(NowaGrupa.this, onTimeSetListener, hour, minute, true).show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int sminute) {
            hour=hourOfDay;
            minute=sminute;

            godzina.setText(hour+":"+minute);
        }
    };

    private class Send extends AsyncTask<String, String, String> {
        String msg = "";
        String text1 = nazwisko.getText().toString();
        String text2 = uwagi.getText().toString();
        int phone = Integer.parseInt(telefon.getText().toString());
        int kNumber = Integer.parseInt(liczbaKajaków.getText().toString());
        int id;
        ResultSet resultSet=null;
        Statement stmt=null;

        @Override
        protected String doInBackground (String... strings) {
            String strResult="";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                if (conn == null) {
                    msg = "Connection goes wrong conn";
                }
                else {
                    stmt=conn.createStatement();
                    resultSet= (ResultSet) stmt.executeQuery("select max(id) from grupy");

                    while(resultSet.next()) {
                        strResult = "" + resultSet.getString(1);
                    }
                    int id = Integer.parseInt(strResult) + 1;


                    String query = "INSERT INTO grupy (id, data, nazwisko, telefon, liczba_kajakow, trasa, stan, godzina, uwagi)" +
                            "VALUES("+id+", "+date+", '"+text1+"', "+phone+", "+kNumber+", '"
                            +String.valueOf(spinner1.getSelectedItem())+"', '"+String.valueOf(spinner2.getSelectedItem())
                            +"', "+(hour*100+minute)+", '"+text2+"');";
                    stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    msg = "Inserting Successful "+id;
                }
                conn.close();
            }
            catch (Exception e){
                msg = "Connection goes wrong exc";
                e.printStackTrace();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg){
            Toast.makeText(NowaGrupa.this, msg, Toast.LENGTH_SHORT).show();
            if(msg.toLowerCase().contains("inserting")) finish();
        }
    }

}
