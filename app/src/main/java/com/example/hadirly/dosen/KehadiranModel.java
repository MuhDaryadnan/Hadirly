package com.example.hadirly.dosen;

public class KehadiranModel {
    public KehadiranModel(String nama, String nim, String status) {
        Nama = nama;
        Nim = nim;
        Status = status;
    }


    private String Nama,Nim, Status;
    public String getStatus() {
        return Status;
    }

    public String getNama() {
        return Nama;
    }

    public String getNim() {
        return Nim;
    }


}
