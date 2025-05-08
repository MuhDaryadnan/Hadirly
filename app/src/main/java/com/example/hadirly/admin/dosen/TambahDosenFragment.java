package com.example.hadirly.admin.dosen;

import static com.example.hadirly.R.id;
import static com.example.hadirly.R.layout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahDosenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahDosenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TambahDosenFragment() {
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
    public static TambahDosenFragment newInstance(String param1, String param2) {
        TambahDosenFragment fragment = new TambahDosenFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(layout.fragment_tambah_dosen, container, false);
        EditText nama, nidn, email, pass, nohp;
        Button save;

        nama = view.findViewById(id.nama);
        nohp = view.findViewById(id.nohp);
        email = view.findViewById(id.email);
        pass = view.findViewById(id.password);
        nidn = view.findViewById(id.nidn);
        save = view.findViewById(id.save);


        save.setOnClickListener(v -> {
            String isinama = nama.getText().toString();
            String isinidn = nidn.getText().toString();
            String isiemail = email.getText().toString();
            String isipass = pass.getText().toString();
            String isinohp = nohp.getText().toString();

            if (!isinama.isEmpty() && !isinohp.isEmpty() && !isiemail.isEmpty() && !isipass.isEmpty() && !isinidn.isEmpty()) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user").child("dosen");
                usersRef.get().addOnSuccessListener(snapshot -> {
                    long jumlahAnak = snapshot.getChildrenCount(); // jumlah m1, m2, dst
                    String newKey = "d" + (jumlahAnak + 1);
                    Map<String, Object> data = new HashMap<>();
                    data.put("nama", isinama);
                    data.put("telpon", isinohp);
                    data.put("email", isiemail);
                    data.put("nim", isinidn);
                    data.put("pass", isipass);

                    usersRef.child(newKey).setValue(data)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(requireContext(), "Berhasil Memperbarui Matakuliah", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();  // Menekan tombol kembali pada activity

                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Gagal Menambahkan", Toast.LENGTH_SHORT).show();
                            });

                });

            }
        });
        return view;
    }
}



