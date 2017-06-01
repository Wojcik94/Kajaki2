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

public class LoadingWyborGrupy extends AppCompatActivity {

    private static final String DB_URL = "jdbc:mysql://serwer1758474.home.pl:3306/24264124_1";
    private static final String USER = "24264124_1";
    private static final String PASS = "Quirell94";

    String msg = "";
    Statement stmt=null;

    ArrayList<klient> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Get getData = new Get();
        getData.execute("");
    }

    private class Get extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {

            lista = new ArrayList<klient>();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

                if (conn == null) {
                    Toast.makeText(getApplicationContext(), "Connection goes wrong conn", Toast.LENGTH_SHORT).show();
                }
                else {
                    stmt=conn.createStatement();
                    final ResultSet resultSet= (ResultSet) stmt.executeQuery("SELECT id, nazwisko FROM grupy WHERE stan<>'Odebrani';");

                    while(resultSet.next()) {
                        lista.add(new klient(resultSet.getInt(1), resultSet.getString(2)));
                        Log.d("Grupa", resultSet.getString(2));
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
            intent.setClass(getApplicationContext(), WyborGrupy.class);
            Log.d("Wysyłanie", ""+lista.size());
            intent.putExtra("lista", lista);
            startActivity(intent);
        }
    }
}
