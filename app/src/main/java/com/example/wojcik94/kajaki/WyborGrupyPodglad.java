package com.example.wojcik94.kajaki;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class WyborGrupyPodglad extends AppCompatActivity {

    //Intent intent=getIntent();
    Calendar c;

    Button b, dataButton;
    ScrollView scrollview;
    TextView text, dataText;

    int data;

    //ArrayList<klient> myList= new ArrayList<klient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ArrayList<klient> myList = new ArrayList<klient>();
        ArrayList<klient> myList = (ArrayList<klient>) getIntent().getSerializableExtra("lista");
        data= (int) getIntent().getIntExtra("data", 0);

        LinearLayout dataLayout= new LinearLayout(this);
        dataLayout.setOrientation(LinearLayout.VERTICAL);


        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);

        scrollview.addView(linearlayout);

        c = Calendar.getInstance();

        dataText= new TextView(this);
        dataText.setText(""+data/1000000+"."+data%1000000/10000+"."+data%1000);
        dataText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        linearlayout.addView(dataText);

        dataButton = new Button(this);
        dataButton.setText("Inna data");
        dataButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        dataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(WyborGrupyPodglad.this, 0, onDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        linearlayout.addView(dataButton);

        text = new TextView(this);
        text.setText("Podglad:");
        text.setTextSize(24);

        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        linearlayout.addView(text);

        for (int i = 0; i < myList.size(); i++) {
            b = new Button(this);
            b.setText("" + myList.get(i).getId() + " " + myList.get(i).getNazwisko());
            b.setId(myList.get(i).getId());
            b.setPadding(20, 3, 8, 3);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            linearlayout.addView(b);

            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), LoadingPodgladGrupy.class);
                    Log.d("Wysylam ID", "" + v.getId());
                    intent.putExtra("id", v.getId());
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });
        }
        this.setContentView(scrollview);

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent (this, MainActivity.class);
        startActivity(setIntent);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
            //dataText.setText(sDay+"."+sMonth+"."+sYear);
            data=sYear+(sMonth+1)*10000+sDay*1000000;
            Intent intent= new Intent();
            intent.setClass(getApplicationContext(), LoadingWyborGrupyPodglad.class);
            intent.putExtra("data", data);
            Log.d("Zmieniam","LoadingWyborPodglad");
            startActivity(intent);
        }
    };
}
