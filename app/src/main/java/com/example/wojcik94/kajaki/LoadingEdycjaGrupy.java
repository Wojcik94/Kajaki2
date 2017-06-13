package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mysql.jdbc.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadingEdycjaGrupy extends AppCompatActivity {

    private static final String DB_URL = "jdbc:mysql://URL";
    private static final String USER = "USER";
    private static final String PASS = "PASS";

    String msg = "";
    Statement stmt=null;

    KlientKonkret grupa;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Open Activity", "LoadingEdycjaGrupy");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        id = getIntent().getIntExtra("id", 1);

        Get getData = new Get();
        getData.execute("");
    }

    private class Get extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                if (conn == null) {
                    Toast.makeText(getApplicationContext(), "Connection goes wrong conn", Toast.LENGTH_SHORT).show();
                }
                else {
                    stmt=conn.createStatement();
                    final ResultSet resultSet= (ResultSet) stmt.executeQuery("SELECT * FROM grupy WHERE id="+id+";");

                    while(resultSet.next()) {
                        grupa=new KlientKonkret(resultSet.getInt("id"), resultSet.getInt("data"),
                                resultSet.getInt("telefon"), resultSet.getInt("liczba_kajakow"), resultSet.getInt("godzina"),
                                resultSet.getString("nazwisko"), resultSet.getString("trasa"), resultSet.getString("stan"),
                                resultSet.getString("kajaki"), resultSet.getString("uwagi"));
                    }
                }
                msg="Pobrano listę";
                conn.close();

            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Connection goes wrong exc", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            //Toast.makeText(WyborGrupy.this, msg, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), EdycjaGrupy.class);
            Log.d("Przekazanie do Edycja Grupy", "ok");
            intent.putExtra("grupa", grupa);
            startActivity(intent);
        }
    }
}
