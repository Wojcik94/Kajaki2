package com.example.wojcik94.kajaki;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EdycjaGrupy extends AppCompatActivity {

    ArrayList<String> names=new ArrayList<String>();
    ArrayList<Integer> ids=new ArrayList<Integer>();


    String msg = "";

    int id, j=0;
    Statement stmt=null;

    Button b;
    ScrollView scrollview;
    TextView text;

    Boolean notDone=true;

    private static final String DB_URL = "jdbc:mysql://serwer1758474.home.pl:3306/24264124_1";
    private static final String USER = "24264124_1";
    private static final String PASS = "Quirell94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Get objGet = new Get();
        objGet.execute("");

        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);

        text = new TextView(this);
        text.setText("GRUPY NA WODZIE:");
        text.setTextSize(24);

        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

        linearlayout.addView(text);

        while(notDone);

        for(int i = 0; i<5;i++)
        {
            /*LinearLayout linear1 = new LinearLayout(this);
            linear1.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(linear1);*/
            b = new Button(this);
            b.setText(""+names.get(i));
            b.setId(ids.get(i));
            b.setPadding(20, 3, 8, 3);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

            linearlayout.addView(b);

            /*CheckBox checkbox = new CheckBox(this);
            checkbox.setId(i);
            checkbox.setText("Wow..");
            linear1.addView(checkbox);

            checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Checked.."+ arg0.getId(), Toast.LENGTH_SHORT).show();
                }
            });*/

            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "Yipee.."+ v.getId(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        this.setContentView(scrollview);

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
                    final ResultSet resultSet= (ResultSet) stmt.executeQuery("SELECT id, nazwisko FROM grupy WHERE stan<>'Odebrani';");

                    while(resultSet.next()) {
                        names.add("" + resultSet.getString("id")+" "+resultSet.getString("nazwisko"));
                        ids.add(resultSet.getInt("id"));
                        j++;
                        Log.d("Grupa", resultSet.getString(2)+" " +j);
                    }
                }
                msg="Pobrano listę";
                conn.close();
                notDone=false;
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Connection goes wrong exc", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            Toast.makeText(EdycjaGrupy.this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
