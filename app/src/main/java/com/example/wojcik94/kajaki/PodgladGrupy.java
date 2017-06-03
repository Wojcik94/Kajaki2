package com.example.wojcik94.kajaki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PodgladGrupy extends AppCompatActivity {

    TextView data, nazwisko, telefon, lKajakow, trasa, stan, kajaki, uwagi;

    KlientKonkret grupa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podglad_grupy);

        grupa = new KlientKonkret((KlientKonkret) getIntent().getSerializableExtra("grupa"));

        data=(TextView) findViewById(R.id.dataPodgl);
        nazwisko=(TextView) findViewById(R.id.nazwiskoPodgl);
        telefon=(TextView) findViewById(R.id.telefonPodgl);
        lKajakow=(TextView) findViewById(R.id.liczbaKajakowPodgl);
        trasa= (TextView) findViewById(R.id.trasaPodgl);
        stan=(TextView) findViewById(R.id.stanPodgl);
        kajaki=(TextView) findViewById(R.id.kajakiPodgl);
        uwagi=(TextView) findViewById(R.id.uwagiPodgl);

        data.setText("Data: "+grupa.getData()/1000000+"."+grupa.getData()%1000000/10000+"."+grupa.getData()%1000);
        nazwisko.setText("Nazwisko: "+grupa.getNazwisko());
        if(grupa.getTelefon()==0)telefon.setText("Telefon: ---");
        else telefon.setText("Telefon: "+grupa.getTelefon());
        lKajakow.setText("Liczba kajaków: "+grupa.getLiczbaKajakow());
        trasa.setText("Trasa: "+ grupa.getTrasa());
        stan.setText("Ostatni stan: "+ grupa.getStan());
        kajaki.setText("Kajaki: "+ grupa.getKajaki());
        uwagi.setText("Uwagi: "+grupa.getUwagi());
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, LoadingWyborGrupyPodglad.class);
        intent.putExtra("data", grupa.getData());
        startActivity(intent);
    }
}
