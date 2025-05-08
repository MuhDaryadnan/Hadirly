package com.example.hadirly.admin.kelas;

public class KelasModel {
    private String nama;
    private String info;
    private String dosen;
    private String kelas;

    public KelasModel(String nama, String info, String dosen, String kelas, String tahun, String semester) {
        this.nama = nama;
        this.info = info;
        this.dosen = dosen;
        this.kelas = kelas;
        this.tahun = tahun;
        this.semester = semester;
    }

    private String tahun;

    public String getSemester() {
        return semester;
    }

    public String getTahun() {
        return tahun;
    }

    private String semester;

    public String getKelas() {
        return kelas;
    }

    public String getDosen() {
        return dosen;
    }

    public String getInfo() {
        return info;
    }

    public String getNama() {
        return nama;
    }


}
