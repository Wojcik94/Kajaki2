package com.example.wojcik94.kajaki;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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

    private int hour, minute, date, day, month, year;
    private String kajaki="";
    TextView godzina, kList, dateChoose;
    EditText nazwisko, telefon, liczbaKajaków, uwagi;

    Spinner spinner1, spinner2;

    private static final String DB_URL = "jdbc:mysql://serwer1758474.home.pl:3306/24264124_1";
    private static final String USER = "24264124_1";
    private static final String PASS = "Quirell94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_grupa);

        Calendar c = Calendar.getInstance();
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.date=c.get(Calendar.YEAR)+(c.get(Calendar.MONTH)+1)*10000+c.get(Calendar.DAY_OF_MONTH)*1000000;

        this.day=c.get(Calendar.DAY_OF_MONTH);
        this.month=c.get(Calendar.MONTH)+1;
        this.year=c.get(Calendar.YEAR);

        godzina = (TextView) findViewById(R.id.godzina);
        kList = (TextView) findViewById(R.id.kajaki);
        dateChoose = (TextView) findViewById(R.id.dateInd);

        godzina.setText(hour+":"+String.format("%02d", minute));
        dateChoose.setText(day+"."+month+"."+year);

        nazwisko = (EditText) findViewById(R.id.nazwisko);
        telefon = (EditText) findViewById(R.id.telefon);
        liczbaKajaków = (EditText) findViewById(R.id.liczba_kajakow);
        uwagi = (EditText) findViewById(R.id.uwagi);

        spinner1 = (Spinner) findViewById(R.id.trasa);
        spinner2 = (Spinner) findViewById(R.id.stan);
    }

    public void setDate (View view) {
        new DatePickerDialog(NowaGrupa.this, 0, onDateSetListener, year, month-1, day).show();
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
            year=sYear;
            month=sMonth+1;
            day=sDay;

            dateChoose.setText(day+"."+month+"."+year);
            date=year+month*10000+day*1000000;
        }
    };

    public void SendToServ (View view) {
        Send objSend = new Send ();
        objSend.execute("");
    }

    public void entry (View view) {
        Intent intent = new Intent(this, popup.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                kajaki=data.getStringExtra("kajaki");
                kList.setText(kajaki);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
        int phone = 0;
        int kNumber = 0;

        int id;
        ResultSet resultSet=null;
        Statement stmt=null;

        @Override
        protected String doInBackground (String... strings) {
            String strResult="";

            if(!(telefon.getText().toString().matches(""))){
                phone=Integer.parseInt(telefon.getText().toString());
            }
            if(!(liczbaKajaków.getText().toString().matches(""))){
                kNumber=Integer.parseInt(liczbaKajaków.getText().toString());
            }

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


                    String query = "INSERT INTO grupy (id, data, nazwisko, telefon, liczba_kajakow, trasa, stan, godzina, kajaki, uwagi)" +
                            "VALUES("+id+", "+date+", '"+text1+"', "+phone+", "+kNumber+", '"
                            +String.valueOf(spinner1.getSelectedItem())+"', '"+String.valueOf(spinner2.getSelectedItem())
                            +"', "+(hour*100+minute)+", '"+kajaki+"', '"+text2+"');";
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
