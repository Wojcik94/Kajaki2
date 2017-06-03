package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        data=c.get(Calendar.DAY_OF_MONTH)*1000000 + (c.get(Calendar.MONTH)+1)*10000+c.get(Calendar.YEAR);
    }

    public void nowaGrupa(View view){
        Intent intent = new Intent(this, NowaGrupa.class);

        startActivity(intent);
    }

    public void editGroup(View view){
        Intent intent = new Intent(this, LoadingWyborGrupy.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    public void podglad(View view){
        Intent intent = new Intent(this, LoadingWyborGrupyPodglad.class);
        intent.putExtra("data", data);
        Log.d("Zmieniam","LoadingWyborPodglad");
        startActivity(intent);
    }
}
