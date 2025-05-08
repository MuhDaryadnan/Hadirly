package com.example.hadirly.admin.kelas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

public class AdminKelasFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Button button;
    private KelasAdapter adapter;
    private List<KelasModel> kelasList;

    public AdminKelasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_kelas, container, false);
        Bundle bundle = new Bundle();

        // Initialize the UI elements
        recyclerView = view.findViewById(R.id.recycle);
        searchView = view.findViewById(R.id.search);
        button = view.findViewById(R.id.button);

        // Initialize the list and adapter
        kelasList = new ArrayList<>();;
        //item diclick
        adapter = new KelasAdapter(kelasList, new KelasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(KelasModel kelasModel) {
                // Kirim data ke DetailKelasFragment

                bundle.putString("nama", kelasModel.getNama());
                bundle.putString("dosen", kelasModel.getDosen());
                bundle.putString("kelas", kelasModel.getKelas());
                bundle.putString("semester", kelasModel.getSemester());
                bundle.putString("tahun", kelasModel.getTahun());

                GantiKelasFragment detailFragment = new GantiKelasFragment();
                detailFragment.setArguments(bundle);

                // Ganti fragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //tambah kelas
        button.setOnClickListener(v -> {
            TambahKelasFragment tambahKelasFragment = new TambahKelasFragment(); // Buat instance fragment
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, tambahKelasFragment);
            fragmentTransaction.addToBackStack(null); // Agar bisa kembali dengan tombol back
            fragmentTransaction.commit();
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("kelas");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                kelasList.clear();
                for (DataSnapshot data1 : snapshot.getChildren()) {
                    String kelas = data1.getKey();
                    for (DataSnapshot data : data1.getChildren()) {
                        String nama = data.child("nama").getValue(String.class);
                        String dosen = data.child("dosen").getValue(String.class);
                        String semester = data.child("semester").getValue(String.class);
                        String kelas2 = data.child("kelas").getValue(String.class);
                        String tahun = data.child("tahun").getValue(String.class);
                        String info= kelas2 + " Semester "+semester+" "+tahun;
                        kelasList.add(new KelasModel(nama, info, dosen, kelas,tahun,semester));
                        Log.e("Firebase", "Gagal mengambil data: " + nama);
                    }
                }
                adapter.updateList(kelasList); // Update adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Gagal mengambil data: " + error.getMessage());
            }
        });


        // Set up the search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        return view;
    }

    private void filterList(String query) {
        List<KelasModel> filteredList = new ArrayList<>();
        for (KelasModel kelas : kelasList) {
            if (kelas.getNama().toLowerCase().contains(query.toLowerCase()) || kelas.getKelas().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(kelas);
            }
        }
        adapter.updateList(filteredList);
    }

}
