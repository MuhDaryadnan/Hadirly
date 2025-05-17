package com.example.hadirly.dosen;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadirly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LihatKehadiranActivity extends AppCompatActivity {
    private KehadiranAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<KehadiranModel> dataList;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private TextView PERTEMUAN_KE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lihat_kehadiran);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String prodi = getIntent().getStringExtra("prodi");
        String MATKUL = getIntent().getStringExtra("matkul");
        String PERTEMUAN = getIntent().getStringExtra("pertemuan");


        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KehadiranAdapter(dataList);
        recyclerView.setAdapter(adapter);

        PERTEMUAN_KE = findViewById(R.id.kehadiran);
        PERTEMUAN_KE.setText("Kehadiran " + PERTEMUAN);


        databaseReference = FirebaseDatabase.getInstance().getReference("kehadiran").child(prodi);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                Log.d("FirebaseCheck", "Snapshot children count: " + snapshot.getChildrenCount());

                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    String matkulProdi = datasnapshot.child("nama").getValue(String.class);
                    Log.d("FirebaseCheck", "Matkul Prodi: " + matkulProdi);
                    if (matkulProdi != null && matkulProdi.equals(MATKUL)) {
                        DataSnapshot hadirSnapshot = datasnapshot.child("hadir").child(PERTEMUAN);
                        Log.d("FirebaseCheck", "Pertemuan children count: " + hadirSnapshot.getChildrenCount());

                        for (DataSnapshot anakPertemuan : hadirSnapshot.getChildren()) {
                            String key = anakPertemuan.getKey();
                            if (!key.equals("code") && !key.equals("tanggal")) {
                                String namaMahasiswa = anakPertemuan.getValue(String.class);
                                String NIM = key;
                                String Status = "Hadir";
                                dataList.add(new KehadiranModel(namaMahasiswa, NIM, Status));
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}