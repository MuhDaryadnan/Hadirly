package com.example.hadirly.admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.hadirly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHomeFragment extends Fragment {

    private TextView jumlahMahasiswaText, jumlahDosenText, jumlahKelasText;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        jumlahMahasiswaText = view.findViewById(R.id.jumlahMahasiswa);
        jumlahDosenText = view.findViewById(R.id.jumlahDosen);
        jumlahKelasText = view.findViewById(R.id.jumlahKelas);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                DataSnapshot mahasiswaSnapshot = snapshot.child("mahasiswa");
                for (DataSnapshot data : mahasiswaSnapshot.getChildren()) {
                    long jumlah = mahasiswaSnapshot.getChildrenCount();
                    jumlahMahasiswaText.setText(String.valueOf(jumlah));
                }

                DataSnapshot DossenSnapshot = snapshot.child("dosen");
                for (DataSnapshot data : DossenSnapshot.getChildren()) {
                    long jumlah = DossenSnapshot.getChildrenCount();
                    jumlahDosenText.setText(String.valueOf(jumlah));
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                jumlahMahasiswaText.setText("Gagal mengambil data");
            }
        });

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("kelas");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot kelasSnapshot : snapshot.getChildren()) {
                    // Menghitung jumlah kelas(mx)
                    long jumlah =  kelasSnapshot.getChildrenCount();
                    jumlahKelasText.setText(String.valueOf(jumlah));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                jumlahMahasiswaText.setText("Gagal mengambil data");
            }
        });

        return view;
    }
}
