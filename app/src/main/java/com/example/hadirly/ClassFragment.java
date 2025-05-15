package com.example.hadirly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassFragment extends Fragment implements ClassAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private ArrayList<ClassData> dataList;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Ambil SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String kelas = sharedPreferences.getString("kelas", null);

        // Inflate layout
        View rootView = inflater.inflate(R.layout.fragment_class, container, false);

        // Setup RecyclerView
        recyclerView = rootView.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int spacingInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        // Add spacing between items
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));

        dataList = new ArrayList<>();
        adapter = new ClassAdapter(dataList, this);  // Pass the listener
        recyclerView.setAdapter(adapter);

        // Ambil data dari Firebase
        if (kelas != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("kelas").child(kelas);
            ambilDataDariFirebase();
        }

        return rootView;
    }

    private void ambilDataDariFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Kosongkan dulu agar tidak dobel
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ClassData data = dataSnapshot.getValue(ClassData.class);
                    if (data != null) {
                        dataList.add(data);
                    }
                }
                // Use notifyDataSetChanged properly
                if (adapter != null) {
                    adapter.notifyDataSetChanged(); // Refresh adapter
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tangani error di sini
            }
        });
    }

    @Override
    public void onItemClick(ClassData classData) {
        // Create ClassFragmentInside and pass data if needed

        ClassFragmentInside classFragmentInside = new ClassFragmentInside();

        // If you need to pass data to the new fragment
        Bundle bundle = new Bundle();
        String ingfo = classData.getKelas() + " Semester " + classData.getSemester() + " - " + classData.getTahun();
        bundle.putString("info", ingfo);
        bundle.putString("dosen", classData.getDosen());
        bundle.putString("matkul", classData.getNama());

        classFragmentInside.setArguments(bundle);

        // Change fragment using the activity
        if (getActivity() instanceof MahasiswaActivity) {
            ((MahasiswaActivity) getActivity()).gantiFragment(classFragmentInside);
        }
    }
}