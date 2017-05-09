package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class NowaGrupa extends AppCompatActivity {

    protected int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_grupa);

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
    }

    public void setTime(View view){
        Intent intent = new Intent(this, SetTime.class);
        startActivity(intent);
    }

    protected void onResume(){
        final TextView godzina = (TextView) findViewById(R.id.godzina) ;
        godzina.setText(hour+":"+minute);
    }
}
