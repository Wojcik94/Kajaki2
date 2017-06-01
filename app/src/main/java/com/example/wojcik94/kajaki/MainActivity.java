package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nowaGrupa(View view){
        Intent intent = new Intent(this, NowaGrupa.class);
        startActivity(intent);
    }

    public void editGroup(View view){
        Intent intent = new Intent(this, EdycjaGrupy.class);
        startActivity(intent);
    }
}
