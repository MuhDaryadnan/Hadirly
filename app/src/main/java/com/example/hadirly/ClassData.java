package com.example.hadirly;

import com.google.firebase.database.PropertyName;

public class ClassData {
    private String dosen;
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    private String semester;
    private String tahun;
    private String kelas;

    // Constructor kosong (untuk Firebase)
    public ClassData() {}


    // Constructor dengan parameter
    public ClassData(String dosen, String nama, String semester, String tahun,String kelas) {
        this.dosen = dosen;
        this.nama = nama;
        this.semester = semester;
        this.tahun = tahun;
        this.kelas = kelas;
    }

    // Getter dan setter untuk properti
    public String getDosen() { return dosen; }
    public void setDosen(String dosen) { this.dosen = dosen; }


    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getTahun() { return tahun; }
    public void setTahun(String tahun) { this.tahun = tahun; }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

}
