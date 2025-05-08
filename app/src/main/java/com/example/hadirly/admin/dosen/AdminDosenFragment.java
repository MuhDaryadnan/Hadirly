package com.example.hadirly.admin.dosen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadirly.R;
import com.example.hadirly.admin.mahasiswa.MahasiswaAdapter;
import com.example.hadirly.admin.mahasiswa.MahasiswaModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDosenFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Button button;
    private DosenAdapter adapter;
    private List<DosenModel> mahasiswaList;

    public AdminDosenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_dosen, container, false);

        // Initialize the UI elements
        recyclerView = view.findViewById(R.id.recycle);
        searchView = view.findViewById(R.id.search);
        button = view.findViewById(R.id.button);

        // Initialize the list and adapter
        mahasiswaList = new ArrayList<>();;

        adapter = new DosenAdapter(mahasiswaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user/dosen");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mahasiswaList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String nama = data.child("nama").getValue(String.class);
                    String email = data.child("email").getValue(String.class);
                    String nim = data.child("nim").getValue(String.class);
                    String nohp = data.child("telpon").getValue(String.class);
                    mahasiswaList.add(new DosenModel(nama, nim, email,nohp));
                    Log.e("Firebase", "Gagal mengambil data: " + nama);
                }
                adapter.updateList(mahasiswaList); // Update adapter
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
        List<DosenModel> filteredList = new ArrayList<>();
        for (DosenModel mahasiswa : mahasiswaList) {
            if (mahasiswa.getNama().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(mahasiswa);
            }
        }
        adapter.updateList(filteredList);
    }

}
