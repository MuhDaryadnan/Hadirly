package com.example.hadirly.admin.dosen;

public class DosenModel {
    private String nama;
    private String nidn;
    private String email;

    public String getNama() {
        return nama;
    }

    public String getNidn() {
        return nidn;
    }

    public String getEmail() {
        return email;
    }

    public String getNohp() {
        return nohp;
    }

    private String nohp;

    public DosenModel(String nama, String nidn, String email, String nohp) {
        this.nama = nama;
        this.nidn = nidn;
        this.email = email;
        this.nohp = nohp;
    }


}
