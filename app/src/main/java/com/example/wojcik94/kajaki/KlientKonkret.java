package com.example.wojcik94.kajaki;

import java.io.Serializable;

/**
 * Created by Wojcik94 on 2017-06-01.
 */

public class KlientKonkret implements Serializable{
    private int id, data, telefon, liczbaKajakow, godzina;
    private String nazwisko, trasa, stan, kajaki, uwagi;

    public KlientKonkret(int id, int data, int telefon, int liczbaKajakow, int godzina, String nazwisko, String trasa, String stan, String kajaki, String uwagi) {
        this.id = id;
        this.data = data;
        this.telefon = telefon;
        this.liczbaKajakow = liczbaKajakow;
        this.godzina = godzina;
        this.nazwisko = nazwisko;
        this.trasa = trasa;
        this.stan = stan;
        this.kajaki = kajaki;
        this.uwagi = uwagi;
    }

    public KlientKonkret(KlientKonkret klientKonkret) {
        this.id = klientKonkret.id;
        this.data = klientKonkret.data;
        this.telefon = klientKonkret.telefon;
        this.liczbaKajakow = klientKonkret.liczbaKajakow;
        this.godzina = klientKonkret.godzina;
        this.nazwisko = klientKonkret.nazwisko;
        this.trasa = klientKonkret.trasa;
        this.stan = klientKonkret.stan;
        this.kajaki = klientKonkret.kajaki;
        this.uwagi = klientKonkret.uwagi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    public int getLiczbaKajakow() {
        return liczbaKajakow;
    }

    public void setLiczbaKajakow(int liczbaKajakow) {
        this.liczbaKajakow = liczbaKajakow;
    }

    public int getGodzina() {
        return godzina;
    }

    public void setGodzina(int godzina) {
        this.godzina = godzina;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTrasa() {
        return trasa;
    }

    public void setTrasa(String trasa) {
        this.trasa = trasa;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getKajaki() {
        return kajaki;
    }

    public void setKajaki(String kajaki) {
        this.kajaki = kajaki;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }
}
