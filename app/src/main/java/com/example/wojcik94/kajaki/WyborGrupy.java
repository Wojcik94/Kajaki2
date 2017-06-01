package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class WyborGrupy extends AppCompatActivity {

    //Intent intent=getIntent();


    Button b;
    ScrollView scrollview;
    TextView text;

    //ArrayList<klient> myList= new ArrayList<klient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ArrayList<klient> myList = new ArrayList<klient>();
        ArrayList<klient> myList = (ArrayList<klient>) getIntent().getSerializableExtra("lista");

        scrollview = new ScrollView(this);
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);

        text = new TextView(this);
        text.setText("GRUPY NA WODZIE:");
        text.setTextSize(24);

        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

        linearlayout.addView(text);

        for(int i = 0; i<myList.size();i++)
        {
            b = new Button(this);
            b.setText(""+myList.get(i).getId()+" "+myList.get(i).getNazwisko());
            b.setId(myList.get(i).getId());
            b.setPadding(20, 3, 8, 3);
            b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

            linearlayout.addView(b);

            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), WyborGrupy.class);
                    Log.d("Wysylam ID", ""+b.getId());
                    intent.putExtra("id", b.getId());
                    startActivity(intent);
                }
            });
        }
        this.setContentView(scrollview);

    }
}
