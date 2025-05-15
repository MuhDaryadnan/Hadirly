package com.example.hadirly.dosen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hadirly.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String barcodeData = getIntent().getStringExtra("kode");
        String batasWaktu = getIntent().getStringExtra("time");


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_barcode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView batas = findViewById(R.id.batas);
        ImageView qrImage = findViewById(R.id.qrImage); // ImageView di layout ScanActivity

        batas.setText("Batas Absen: "+batasWaktu);

        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(barcodeData, BarcodeFormat.QR_CODE, 500, 500);
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace(); // Atau tampilkan pesan error ke user
        }

    }
}