package com.example.hadirly;

public class HomeModel {
    public String getDate() {
        return date;
    }

    public String getMatkul() {
        return matkul;
    }

    private String matkul;

    public HomeModel(String matkul, String date) {
        this.matkul = matkul;
        this.date = date;
    }

    private String date;

}
