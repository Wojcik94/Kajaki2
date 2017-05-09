package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NowaGrupa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowa_grupa);
    }

    public void setTime(View view){
        Intent intent = new Intent(this, SetTime.class);
        startActivity(intent);
    }
}
