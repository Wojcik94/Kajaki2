package com.example.wojcik94.kajaki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

public class SetTime extends NowaGrupa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);
    }
}
