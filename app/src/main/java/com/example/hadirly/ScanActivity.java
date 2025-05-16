package com.example.hadirly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class ScanActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String tanggalSekarang = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String savedKelas = sharedPreferences.getString("kelas", null).toUpperCase();


        // Langsung mulai scanner saat activity dibuka
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan QR Code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = getIntent();
        String matkul = intent.getStringExtra("matkul");
        String pertemuan = intent.getStringExtra("pertemuan");
        String savedNim = sharedPreferences.getString("NIM", null);
        String savedNama = sharedPreferences.getString("nama", null);
        String savedKelas = sharedPreferences.getString("kelas", null).toUpperCase();
        Log.e("Firebase",savedKelas );

        String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Scan dibatalkan
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                String scanResult = result.getContents();  // NIM yang discan
                DatabaseReference kelasRef = FirebaseDatabase.getInstance().getReference()
                        .child("kehadiran")
                        .child(savedKelas);

                kelasRef.get().addOnSuccessListener(snapshot -> {
                    AtomicBoolean ketemu = new AtomicBoolean(false);
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String nama = child.child("nama").getValue(String.class);

                        if (nama != null && matkul != null && nama.trim().equalsIgnoreCase(matkul.trim())) {
                            // Ini dia mX yang cocok
                            String mKey = child.getKey(); // misal: "m2"

                            DatabaseReference hadirRef = kelasRef
                                    .child(mKey)
                                    .child("hadir")
                                    .child(pertemuan);
                            //MENGAMBIL KDOE BARCODE

                            hadirRef.get().addOnSuccessListener(snapshots -> {
                                String barkod = snapshots.child("code").getValue(String.class);
                                if (barkod != null && barkod.length() >= 5) {
                                    String batasTentu = barkod.substring(barkod.length() - 5);
                                    try {
                                        Date batas_TENTU = sdf.parse(batasTentu);

                                        // Contoh cek waktu sekarang dengan batas waktu
                                        Date sekarang = sdf.parse(sdf.format(new Date()));

                                        if (scanResult.equals(barkod) && sekarang.before(batas_TENTU)) {
                                            Map<String, Object> dataF = new HashMap<>();
                                            dataF.put(savedNim, savedNama);

                                            //MENYIMPAN KE NODE MAHASISWA

                                            DatabaseReference mahasiswaRef = FirebaseDatabase.getInstance().getReference()
                                                    .child("user")
                                                    .child("mahasiswa");

                                            mahasiswaRef.get().addOnSuccessListener(znapshot -> {
                                                boolean ketemuk = false;
                                                for (DataSnapshot anak : znapshot.getChildren()) {
                                                    String kelasMhs = anak.child("kelas").getValue(String.class);
                                                    String namaMhs = anak.child("nama").getValue(String.class);

                                                    if (kelasMhs != null && kelasMhs.equalsIgnoreCase(savedKelas)
                                                            && namaMhs != null && namaMhs.equalsIgnoreCase(savedNama)) {

                                                        ketemuk = true;
                                                        // Simpan kehadiran di node mahasiswa itu sendiri
                                                        DatabaseReference hadir_Ref = anak.getRef().child("kehadiran").child(matkul).child(tanggalSekarang);

                                                        hadir_Ref.setValue(true).addOnSuccessListener(unused -> {
                                                            Log.d("Firebase", "Kehadiran " + namaMhs + " tanggal " + tanggalSekarang + " berhasil disimpan");
                                                        }).addOnFailureListener(e -> {
                                                            Log.e("Firebase", "Gagal menyimpan kehadiran " + namaMhs, e);
                                                        });

                                                        break;  // sudah ketemu, keluar loop
                                                    }
                                                }
                                                if (!ketemuk) {
                                                    Log.d("Firebase", "Mahasiswa dengan nama " + savedNama + " dan kelas " + savedKelas + " tidak ditemukan");
                                                }
                                            });



                                            hadirRef.updateChildren(dataF)
                                                    .addOnSuccessListener(unused -> {
                                                        Toast.makeText(this, "Kehadiran disimpan", Toast.LENGTH_SHORT).show();
                                                        Intent intento = new Intent();
                                                        intento.putExtra("SCAN_RESULT", result.getContents());
                                                        setResult(RESULT_OK, intento);
                                                        finish();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(this, "Gagal menyimpan", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    });
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(this, "Format waktu tidak valid", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(this, "Data barcode tidak valid", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    }
                });

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }



    }
}