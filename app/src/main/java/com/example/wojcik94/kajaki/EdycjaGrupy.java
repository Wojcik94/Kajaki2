package com.example.wojcik94.kajaki;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class EdycjaGrupy extends AppCompatActivity {

    private int hour, minute, date, day, month, year;
    private String kajaki="";
    TextView godzina, kList, dateChoose;
    EditText nazwisko, telefon, liczbaKajakow, uwagi;

    Spinner spinner1, spinner2;

    KlientKonkret before, after;

    private static final String DB_URL = "jdbc:mysql://serwer1758474.home.pl:3306/24264124_1";
    private static final String USER = "24264124_1";
    private static final String PASS = "Quirell94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_grupa);

        Log.d("Odpalone EdycjaGrupy", "ok");

        before = new KlientKonkret((KlientKonkret) getIntent().getSerializableExtra("grupa"));
        after = (KlientKonkret) getIntent().getSerializableExtra("grupa");

        godzina = (TextView) findViewById(R.id.godzina);
        godzina.setText(""+(before.getGodzina()/100)+":"+before.getGodzina()%100);
        kList = (TextView) findViewById(R.id.kajaki);
        kList.setText(before.getKajaki());
        dateChoose = (TextView) findViewById(R.id.dateInd);
        dateChoose.setText(""+before.getData()/1000000+"."+before.getData()%1000000/10000+"."+before.getData()%1000);

        nazwisko = (EditText) findViewById(R.id.nazwisko);
        nazwisko.setText(before.getNazwisko());
        telefon = (EditText) findViewById(R.id.telefon);
        telefon.setText(""+before.getTelefon());
        liczbaKajakow = (EditText) findViewById(R.id.liczba_kajakow);
        liczbaKajakow.setText(""+before.getLiczbaKajakow());
        uwagi = (EditText) findViewById(R.id.uwagi);
        uwagi.setText(before.getUwagi());

        spinner1 = (Spinner) findViewById(R.id.trasa);

        switch(before.getTrasa()){
            case "Zakliczyn":
                spinner1.setSelection(1);
                break;
            case "Isep":
                spinner1.setSelection(2);
                break;
            case "Tarnów":
                spinner1.setSelection(3);
                break;
            case "Biała":
                spinner1.setSelection(4);
                break;
            case "Inna":
                spinner1.setSelection(5);
                break;
            default:
                spinner1.setSelection(0);
                break;
        }


        spinner2 = (Spinner) findViewById(R.id.stan);
        switch(before.getStan()){
            case "Czekamy":
                spinner2.setSelection(1);
                break;
            case "Wystartowali":
                spinner2.setSelection(2);
                break;
            case "Meldowali":
                spinner2.setSelection(3);
                break;
            case "Dopłynęli":
                spinner2.setSelection(4);
                break;
            case "Odebrani":
                spinner2.setSelection(5);
                break;
            default:
                spinner2.setSelection(0);
                break;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, LoadingWyborGrupy.class);
        startActivity(intent);
    }

    public void setDate (View view) {
        new DatePickerDialog(EdycjaGrupy.this, 0, onDateSetListener, before.getData()%1000, before.getData()%1000000/10000-1, before.getData()/1000000).show();
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
            dateChoose.setText(sDay+"."+sMonth+"."+sYear);
            after.setData(sYear+sMonth*10000+sDay*1000000);
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
                after.setKajaki(data.getStringExtra("kajaki"));
                kList.setText(after.getKajaki());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void setTime(View view){
        new TimePickerDialog(EdycjaGrupy.this, onTimeSetListener, before.getGodzina()/100, before.getGodzina()%100, true).show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int sminute) {

            after.setGodzina(hourOfDay*100+sminute);
            Log.d("Zmieniona godzina", "" + after.getGodzina());
            Log.d("Stara godzina", ""+before.getGodzina());
            godzina.setText(hourOfDay+":"+sminute);
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

            String valuesChanged="";
            boolean first=true;

            Log.d("Data", ""+after.getData());

            if(before.getData() != after.getData()) {
                valuesChanged=valuesChanged+"data = "+after.getData();
                first=false;
            }

            after.setNazwisko(nazwisko.getText().toString());
            Log.d("Nazwisko", after.getNazwisko());

            if(before.getNazwisko() != after.getNazwisko()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"nazwisko = '"+after.getNazwisko()+ "'";
            }

            if(!(telefon.getText().toString().matches(""))){
                after.setTelefon(Integer.parseInt(telefon.getText().toString()));
            }
            else {
                after.setTelefon(0);
            }

            if(before.getTelefon() != after.getTelefon()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"telefon = "+ after.getTelefon();
            }

            if(!(liczbaKajakow.getText().toString().matches(""))){
                after.setLiczbaKajakow(Integer.parseInt(liczbaKajakow.getText().toString()));
            }
            else {
                after.setLiczbaKajakow(0);
            }

            if(before.getLiczbaKajakow() != after.getLiczbaKajakow()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"liczba_kajakow = "+ after.getLiczbaKajakow();
            }

            after.setTrasa(String.valueOf(spinner1.getSelectedItem()));

            if(before.getTrasa() != after.getTrasa()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"trasa = '"+ after.getTrasa()+ "'";
            }

            after.setStan(String.valueOf(spinner2.getSelectedItem()));

            if(before.getStan() != after.getStan()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"stan = '"+ after.getStan()+ "'";
            }

            if(before.getGodzina() != after.getGodzina()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"godzina = "+ after.getGodzina();
            }

            if(before.getKajaki() != after.getKajaki()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"kajaki = '"+after.getKajaki()+ "'";
            }

            after.setUwagi(uwagi.getText().toString());

            if(before.getUwagi() != after.getUwagi()) {
                if(!first) valuesChanged=valuesChanged+", ";
                first=false;
                valuesChanged=valuesChanged+"uwagi = '"+ after.getUwagi()+ "'";
            }

            Log.d("dane", valuesChanged);

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                if (conn == null) {
                    msg = "Connection goes wrong conn";
                }
                else {
                    stmt=conn.createStatement();
                    String query = "UPDATE grupy SET " + valuesChanged + " WHERE id="+ before.getId() +";";
                    Log.d ("query", query);
                    stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    msg = "Inserting Successful "+before.getId();
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
            Toast.makeText(EdycjaGrupy.this, msg, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

}
