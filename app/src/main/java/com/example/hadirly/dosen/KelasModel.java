package com.example.hadirly.dosen;

public class KelasModel {
    public String nama;
    public String jadwal;

    public KelasModel() {
        // Diperlukan untuk Firebase
    }

    public KelasModel(String nama, String jadwal) {
        this.nama = nama;
        this.jadwal = jadwal;
    }
}

