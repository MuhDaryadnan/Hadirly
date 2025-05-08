package com.example.hadirly.admin.mahasiswa;

public class MahasiswaModel {
    public MahasiswaModel(String name, String nim, String kelas) {
        this.name = name;
        this.nim = nim;
        this.kelas = kelas;
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getKelas() {
        return kelas;
    }

    private String name,nim,kelas;

}
