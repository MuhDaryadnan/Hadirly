package com.example.hadirly.admin.kelas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hadirly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GantiKelasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GantiKelasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GantiKelasFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GantiKelasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GantiKelasFragment newInstance(String param1, String param2) {
        GantiKelasFragment fragment = new GantiKelasFragment();
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
        View view = inflater.inflate(R.layout.fragment_ganti_kelas, container, false);
        EditText nama,dosen,semester,tahun;
        TextView kelas;
        Button save;

        nama = view.findViewById(R.id.matkul);
        dosen = view.findViewById(R.id.dosen);
        kelas = view.findViewById(R.id.kelas);
        semester = view.findViewById(R.id.semester);
        tahun = view.findViewById(R.id.tahun);
        save = view.findViewById(R.id.save);

        Bundle bundle = getArguments();
        String kelasada = bundle.getString("kelas");
        String namaada = bundle.getString("nama");


        if (bundle != null) {
            nama.setText(namaada);
            semester.setText(bundle.getString("semester"));
            dosen.setText(bundle.getString("dosen"));
            kelas.setText(kelasada);
            tahun.setText(bundle.getString("tahun"));
        }

        save.setOnClickListener(v -> {
            String newNama = nama.getText().toString();
            String newDosen = dosen.getText().toString();
            String newSemester = semester.getText().toString();
            String newTahun = tahun.getText().toString();

            // Cek apakah ada perubahan
            if (!newNama.equals(bundle.getString("nama")) || !newDosen.equals(bundle.getString("dosen"))  || !newSemester.equals(bundle.getString("semester"))  || !newTahun.equals(bundle.getString("tahun"))) {
                // Akses reference ke node mahasiswa
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("kelas").child(kelasada);
                usersRef.orderByChild("nama").equalTo(namaada).get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        // Jika data ditemukan, update field nama dan email
                        for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                            userSnap.getRef().child("nama").setValue(newNama);
                            userSnap.getRef().child("dosen").setValue(newDosen);
                            userSnap.getRef().child("tahun").setValue(newTahun);
                            userSnap.getRef().child("semester").setValue(newSemester);
                            // Feedback ke pengguna
                            Toast.makeText(requireContext(), "Berhasil Memperbarui Matakuliah", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();  // Menekan tombol kembali pada activity

                        }
                    }

                }).addOnFailureListener(e -> {
                    // Jika query gagal
                    Log.d("FirebaseError", "Gagal mengambil data: " + e.getMessage());
                });
            } else {
                // Jika tidak ada perubahan
                Toast.makeText(requireContext(), "Tidak ada perubahan", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}