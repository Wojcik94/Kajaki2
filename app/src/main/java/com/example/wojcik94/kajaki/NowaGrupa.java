package com.example.wojcik94.kajaki;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class NowaGrupa extends AppCompatActivity {

    protected int hour, minute;
    private int telefon, liczba_kajakow, stan;
    TextView godzina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_grupa);

        Calendar c = Calendar.getInstance();
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);

        godzina = (TextView) findViewById(R.id.godzina);

        godzina.setText(hour+":"+minute);
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
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

}
