package com.example.hadirly.admin.kelas;

import static com.example.hadirly.R.*;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.hadirly.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahKelasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahKelasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TambahKelasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TambahKelasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TambahKelasFragment newInstance(String param1, String param2) {
        TambahKelasFragment fragment = new TambahKelasFragment();
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
        View view = inflater.inflate(R.layout.fragment_tambah_kelas, container, false);
        EditText matkul,kelas,dosen,tahun,semester,prodi;
        Button save;

        matkul = view.findViewById(R.id.matkul);
        kelas = view.findViewById(R.id.kelas2);
        dosen = view.findViewById(R.id.dosen);
        tahun = view.findViewById(R.id.tahun);
        semester = view.findViewById(id.semester);
        prodi = view.findViewById(id.prodi);
        save = view.findViewById(R.id.save);




        save.setOnClickListener(v -> {
            String isiMatkul = matkul.getText().toString();
            String isiKelas = kelas.getText().toString();
            String isiDosen = dosen.getText().toString();
            String isiTahun = tahun.getText().toString();
            String isiSemester = semester.getText().toString();
            String isiProdi = prodi.getText().toString();
            Log.d("Input Values", "Matkul: " + isiMatkul + ", Kelas: " + isiKelas + ", Dosen: " + isiDosen +
                    ", Tahun: " + isiTahun + ", Semester: " + isiSemester + ", Prodi: " + isiProdi);
            if (!isiMatkul.isEmpty() && !isiKelas.isEmpty() && !isiDosen.isEmpty() && !isiTahun.isEmpty() && !isiSemester.isEmpty( )&& !isiProdi.isEmpty()) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("kelas");
                usersRef.child(isiKelas).get().addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        long jumlahAnak = snapshot.getChildrenCount(); // jumlah m1, m2, dst
                        String newKey = "m" + (jumlahAnak + 1);
                        Map<String, Object> data = new HashMap<>();
                        data.put("nama", isiMatkul);
                        data.put("dosen", isiDosen);
                        data.put("semester", isiSemester);
                        data.put("kelas", isiProdi);
                        data.put("tahun", isiTahun);

                        usersRef.child(isiKelas).child(newKey).setValue(data)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(requireContext(), "Berhasil Memperbarui Matakuliah", Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();  // Menekan tombol kembali pada activity

                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Gagal Menambahkan" , Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        // Kalau TEKOM F belum ada, buat dulu
                        String newKey = "m1";

                        Map<String, Object> data = new HashMap<>();
                        data.put("nama", isiMatkul);
                        data.put("dosen", isiDosen);
                        data.put("semester", isiSemester);
                        data.put("tahun", isiTahun);
                        data.put("kelas",isiProdi);  // Menambahkan kelas langsung di sini

                        usersRef.child(isiKelas).child(newKey).setValue(data)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(requireContext(), "Berhasil Memperbarui Matakuliah", Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();  // Menekan tombol kembali pada activity

                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Gagal Menambahkan" , Toast.LENGTH_SHORT).show();
                                });
                    }
                });
            }else{
                Toast.makeText(getContext(), "Harap Mengisi Semua Field", Toast.LENGTH_LONG).show();
            }

        });


        return view;
    }



}