package com.example.hadirly.dosen;

public class DosenModel {
    private String dosen;
    private String nama;
    private String semester;
    private String tahun;
    private String kelas;

    // Constructor kosong (untuk Firebase)

    // Constructor dengan parameter
    public DosenModel(String dosen, String nama, String semester, String tahun,String kelas) {
        this.dosen = dosen;
        this.nama = nama;
        this.semester = semester;
        this.tahun = tahun;
        this.kelas = kelas;
    }

    public String getNama() {
        return nama;
    }

    // Constructor kosong (untuk Firebase)
    public DosenModel() {
        // kosong saja
    }


    public void setNama(String nama) {
        this.nama = nama;
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