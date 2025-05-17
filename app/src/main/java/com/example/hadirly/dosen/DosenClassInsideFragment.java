package com.example.hadirly.dosen;

import static android.app.Activity.RESULT_OK;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hadirly.R;
import com.example.hadirly.ScanActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DosenClassInsideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DosenClassInsideFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private String selectedTime = "00:00";

    private static final int SCAN_REQUEST_CODE = 100;
    Spinner mySpinner;
    private TextView namaText, infoText, dosenText;
    private Button absenBTN, HADIR_BTN;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DosenClassInsideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DosenClassInsideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DosenClassInsideFragment newInstance(String param1, String param2) {


        DosenClassInsideFragment fragment = new DosenClassInsideFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_dosen_class_inside, container, false);

        mySpinner = rootView.findViewById(R.id.spinner);
        String[] items = {"Pertemuan ke-", "Pertemuan ke 1","Pertemuan ke 2","Pertemuan ke 3","Pertemuan ke 4","Pertemuan ke 5","Pertemuan ke 6","Pertemuan ke 7","Pertemuan ke 8",
                "Pertemuan ke 9","Pertemuan ke 10","Pertemuan ke 11","Pertemuan ke 12","Pertemuan ke 13","Pertemuan ke 14","Pertemuan ke 15","Pertemuan ke 16",};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_minggu, items);
        adapter.setDropDownViewResource(R.layout.list_minggu);
        mySpinner.setAdapter(adapter);

        String tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        dosenText = rootView.findViewById(R.id.dosen);
        namaText = rootView.findViewById(R.id.nama);
        infoText = rootView.findViewById(R.id.info);

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedNama = sharedPreferences.getString("nama", null);

        absenBTN = rootView.findViewById(R.id.absen);
        HADIR_BTN = rootView.findViewById(R.id.lihat);

        Bundle bundle = getArguments();
        String info = bundle.getString("info", "");
        String prodi = bundle.getString("prodi", "");
        String dosen = bundle.getString("dosen", "");
        String matkul = bundle.getString("matkul", "");
        String selectedValue = mySpinner.getSelectedItem().toString();

        dosenText.setText(dosen);
        namaText.setText(matkul);
        infoText.setText(info);

        //BTN MENAMPILKAN DAFTAR
        if (!selectedValue.equals("Pertemuan ke-")){
            HADIR_BTN.setOnClickListener(v -> {
                String PERTEMUAN = mySpinner.getSelectedItem().toString();

                //KE LIHAT KEHADIRAN
                Intent intent = new Intent(getActivity(), LihatKehadiranActivity.class);
                intent.putExtra("prodi", prodi);
                intent.putExtra("matkul", matkul);
                intent.putExtra("pertemuan", PERTEMUAN);

                startActivity(intent);
            });
        }else{
            Toast.makeText(requireContext(), "Pilih Pertemuan Yang Ingin Dilihat!", Toast.LENGTH_LONG).show();
        }




        //btnabsen
        absenBTN.setOnClickListener(v -> {



            if (selectedValue.equals("Pertemuan ke-")) {
                Toast.makeText(requireContext(), "Pilih Pertemuan!", Toast.LENGTH_LONG).show();
            } else if (selectedTime.equals("00:00")) {
                Toast.makeText(requireContext(), "Pilih Jam terlebih dahulu!", Toast.LENGTH_LONG).show();
            } else {
                DatabaseReference kelasRef = FirebaseDatabase.getInstance().getReference()
                        .child("kehadiran").child(prodi);
                Log.d("wadawdas", prodi);

                kelasRef.get().addOnSuccessListener(snapshot -> {
                    boolean ketemu = false;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String namas = child.child("nama").getValue(String.class);
                        String doseng = child.child("dosen").getValue(String.class);
                        Log.d("hamas", namas);

                        if (namas != null && doseng != null && matkul != null && dosen != null &&
                                matkul.trim().equalsIgnoreCase(namas.trim()) &&
                                doseng.trim().equalsIgnoreCase(dosen.trim())) {

                            String mKey = child.getKey(); // misal: "m1"
                            DatabaseReference hadirRef = kelasRef
                                    .child(mKey)
                                    .child("hadir")
                                    .child(selectedValue);

                            String kodeUnik = matkul + "-" + selectedValue.replace(" ", "") + "-" + selectedTime;

                            Map<String, Object> dataF = new HashMap<>();
                            dataF.put("code", kodeUnik);
                            dataF.put("tanggal", tanggal);

                            hadirRef.updateChildren(dataF)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(getContext(), "Kehadiran disimpan", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), BarcodeActivity.class);
                                        intent.putExtra("kode", kodeUnik);
                                        intent.putExtra("time", selectedTime);
                                        startActivityForResult(intent, SCAN_REQUEST_CODE);

                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getContext(), "Gagal menyimpan", Toast.LENGTH_SHORT).show();
                                    });

                            ketemu = true;
                            break;
                        }
                    }


                    if (!ketemu) {
                        Toast.makeText(getContext(), "Mata kuliah tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button btnTimePicker = rootView.findViewById(R.id.btnTimePicker);
        btnTimePicker.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                    (view1, hourOfDay, minute1) -> {
                        selectedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        btnTimePicker.setText(selectedTime);
                    }, hour, minute, true);
            timePickerDialog.show();
        });

        return rootView;
    }
}


