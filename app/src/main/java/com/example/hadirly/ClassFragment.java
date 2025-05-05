package com.example.hadirly;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClassFragment extends Fragment {
    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private static final String TAG = "ClassFragment";
    SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ✅ Panggil SharedPreferences setelah context tersedia
        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String kelas = sharedPreferences.getString("kelas", null);

        // Inflate layout untuk fragment
        View rootView = inflater.inflate(R.layout.fragment_class, container, false);

        // Inisialisasi RecyclerView
        recyclerView = rootView.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("kelas").child(kelas);
        // Setup FirebaseRecyclerAdapter
        FirebaseRecyclerOptions<ClassData> options =
                new FirebaseRecyclerOptions.Builder<ClassData>()
                        .setQuery(databaseReference, ClassData.class)
                        .build();

        adapter = new ClassAdapter(options);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
