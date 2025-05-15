package com.example.hadirly.dosen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadirly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DosenHomeFragment extends Fragment {

    private static final String TAG = "DosenHomeFragment";
    private TextView namaDosenText;
    private TextView countMataKuliah;
    SharedPreferences sharedPreferences;
    private RecyclerView recyclerViewKelas;
    private KelasAdapter kelasAdapter;
    private List<KelasModel> kelasList = new ArrayList<>();
    private List<String> list = new ArrayList<>();



    private DatabaseReference kelasRef;

    public DosenHomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dosen_home_, container, false);


        // Initialize views
        namaDosenText = view.findViewById(R.id.dosenText);
        countMataKuliah = view.findViewById(R.id.countMataKuliah);
        recyclerViewKelas = view.findViewById(R.id.recyclerViewKelas);

        // Setup RecyclerView
        recyclerViewKelas.setLayoutManager(new LinearLayoutManager(getContext()));
        kelasAdapter = new KelasAdapter(kelasList);
        recyclerViewKelas.setAdapter(kelasAdapter);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedNim = sharedPreferences.getString("NIM", null);
        String savedNama = sharedPreferences.getString("nama", null);

        namaDosenText.setText(savedNama);
        loadKelasList();


        return view;
    }

    private void loadKelasList() {
        // Structure: kelas -> TEKOM F -> m1 -> dosen = "Raihan S.Kom, M.Kom, Ph.D"
        kelasRef = FirebaseDatabase.getInstance().getReference("kelas");
        kelasRef.orderByChild("dosen").equalTo("savedNama");

        //menampilkan jumlah kelas
        kelasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            String savedNama = sharedPreferences.getString("nama", null);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int total = 0;
                list.clear();
                for (DataSnapshot kelasSnapshot : dataSnapshot.getChildren()) {
                    String kelas = kelasSnapshot.getKey();
                    for (DataSnapshot mSnapshot : kelasSnapshot.getChildren()) {
                        String dosen = mSnapshot.child("dosen").getValue(String.class);
                        String semester = mSnapshot.child("semester").getValue(String.class);
                        String nama = mSnapshot.child("nama").getValue(String.class);

                        if (savedNama.equals(dosen)) {
                            total++;
                            KelasModel kelasModel = new KelasModel(nama + " (" + kelas + ")", "Semester "+semester);
                            Log.d("Firebase", "dosen " + nama  + ": " + total);
                            kelasList.add(kelasModel);
                        }
                    }
                }
                kelasAdapter.notifyDataSetChanged();
                countMataKuliah.setText(String.valueOf(total));
                Log.d("Firebase", "Jumlah kelas dosen " + savedNama  + ": " + total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });




    }
}

