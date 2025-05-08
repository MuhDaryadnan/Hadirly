package com.example.hadirly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;

public class ClassFragmentInside extends Fragment {

    private TextView namaText, infoText, dosenText;
    private Button absenBTN;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int SCAN_REQUEST_CODE = 100;

    public ClassFragmentInside() {
        // Required empty public constructor
    }

    public static ClassFragmentInside newInstance(String param1, String param2) {
        ClassFragmentInside fragment = new ClassFragmentInside();
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
        View rootView = inflater.inflate(R.layout.fragment_class_inside, container, false);

        // Initialize views using rootView
        dosenText = rootView.findViewById(R.id.dosen);
        namaText = rootView.findViewById(R.id.nama);
        infoText = rootView.findViewById(R.id.info);

        absenBTN = rootView.findViewById(R.id.button);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String info = bundle.getString("info", "");
            String dosen = bundle.getString("dosen", "");
            String matkul = bundle.getString("matkul", "");

            dosenText.setText(dosen);
            namaText.setText(matkul);
            infoText.setText(info);
        }

        absenBTN.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScanActivity.class);
            startActivityForResult(intent, SCAN_REQUEST_CODE);
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            String result = data.getStringExtra("SCAN_RESULT");
            // Gunakan hasil QR code di sini
            Toast.makeText(getActivity(), "Hasil scan: " + result, Toast.LENGTH_LONG).show();
            // Jika Anda ingin menampilkan hasilnya di TextView, pastikan TextView tersebut ada di layout
            // TextView resultTextView = getView().findViewById(R.id.textViewResult);
            // resultTextView.setText("Hasil scan: " + result);
        }
    }
}