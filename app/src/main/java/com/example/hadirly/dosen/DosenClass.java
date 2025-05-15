package com.example.hadirly.dosen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.hadirly.ClassFragmentInside;
import com.example.hadirly.DosenActivity;
import com.example.hadirly.dosen.DosenModel;
import com.example.hadirly.MahasiswaActivity;
import com.example.hadirly.R;
import com.example.hadirly.VerticalSpaceItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DosenClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DosenClass extends Fragment implements DosenAdapter.OnItemClickListener {
    Bundle bundle = new Bundle();
    private RecyclerView recyclerView;
    private DosenAdapter adapter;
    private ArrayList<DosenModel> dataList;
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private TextView namaDosenText;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DosenClass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DosenClass.
     */
    // TODO: Rename and change types and number of parameters
    public static DosenClass newInstance(String param1, String param2) {
        DosenClass fragment = new DosenClass();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dosen_class, container, false);

        // Ambil SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedNama = sharedPreferences.getString("nama", null);
        
        // Setup RecyclerView
        recyclerView = rootView.findViewById(R.id.recycleDosen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int spacingInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        // Add spacing between items
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));

        dataList = new ArrayList<>();
        adapter = new DosenAdapter(dataList, this);  // Pass the listener
        namaDosenText=rootView.findViewById(R.id.dosenText);
        namaDosenText.setText(savedNama);


        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("kelas");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Kosongkan dulu agar tidak dobel

                for (DataSnapshot kelasSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot : kelasSnapshot.getChildren()) {
                        DosenModel data = dataSnapshot.getValue(DosenModel.class);
                        if (data != null && savedNama.equals(data.getDosen())) {
                            String key = kelasSnapshot.getKey();
                            Log.d("prodinya ngab", key);
                            bundle.putString("prodi",key);
                            dataList.add(data);
                        }
                    }
                }

                // Panggil sekali di luar perulangan
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tangani error di sini
            }
        });


        return rootView;
    }

    @Override
    public void onItemClick(DosenModel classData) {
        // Create ClassFragmentInside and pass data if needed

        DosenClassInsideFragment classFragmentInside = new DosenClassInsideFragment();

        // If you need to pass data to the new fragment

        String ingfo = classData.getKelas() + " Semester " + classData.getSemester() + " - " + classData.getTahun();
        bundle.putString("info", classData.getKelas());
        bundle.putString("info", ingfo);
        bundle.putString("dosen", classData.getDosen());
        bundle.putString("matkul", classData.getNama());

        classFragmentInside.setArguments(bundle);

        // Change fragment using the activity
        if (getActivity() instanceof DosenActivity) {
            ((DosenActivity) getActivity()).gantiFragment(classFragmentInside);
        }
    }
}