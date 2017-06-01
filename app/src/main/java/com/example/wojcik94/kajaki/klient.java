package com.example.wojcik94.kajaki;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Wojcik94 on 2017-06-01.
 */

public class klient implements Serializable {
    private int id;
    private String nazwisko;

    public klient(int id, String nazwisko) {
        this.id = id;
        this.nazwisko = nazwisko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
