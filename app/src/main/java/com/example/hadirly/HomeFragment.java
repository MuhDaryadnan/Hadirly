package com.example.hadirly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.hadirly.admin.dosen.DosenAdapter;
import com.example.hadirly.admin.dosen.DosenModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private CalendarView calendarView;
    private List<EventDay> eventDays = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private SearchView searchView;

    private HomeAdapter adapter;
    private List<HomeModel> mahasiswaList;
    List<String> tanggal_hadir = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedNim = sharedPreferences.getString("NIM", null);
        String savedNama = sharedPreferences.getString("nama", null);

        adapter = new HomeAdapter(mahasiswaList);




        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.resikel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        int spacingInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

// Add spacing between items
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixels));
        calendarView = view.findViewById(R.id.calendarView);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        //RECYCLE
        mahasiswaList = new ArrayList<>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("kehadiran");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot kelasSnapshot : snapshot.getChildren()) { // TEKOM F, TEKOM G, dll
                    for (DataSnapshot mahasiswaSnapshot : kelasSnapshot.getChildren()) { // m1, m2, dst
                        String nama = mahasiswaSnapshot.child("nama").getValue(String.class);
                        DataSnapshot hadirSnapshot = mahasiswaSnapshot.child("hadir");


                        for (DataSnapshot mingguSnapshot : hadirSnapshot.getChildren()) { // minggu 1, 2, dst


                            boolean ditemukan = false;
                            for (DataSnapshot child : mingguSnapshot.getChildren()) {
                                String key = child.getKey();
                                String value = child.getValue(String.class);

                                if (key.startsWith("h") && "Muh Daryadnan".equals(value)) {
                                    ditemukan = true;
                                    break;
                                }
                            }

                            if (ditemukan) {
                                String tanggal = mingguSnapshot.child("tanggal").getValue(String.class);
                                Log.d("Firebase", tanggal+  nama);
                                mahasiswaList.add(new HomeModel(nama, tanggal));
                            }

                        }
                    }
                }
                adapter.updateList(mahasiswaList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE_ERROR", error.getMessage());
            }
        });


        //KALENDER
        //inisialisasi
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("user")
                .child("mahasiswa");
        TextView nama;

        nama=view.findViewById(R.id.nama);
        nama.setText(savedNama);

        ref.orderByChild("nim").equalTo(savedNim)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot mahasiswaSnapshot : snapshot.getChildren()) {
                            DataSnapshot kehadiranSnapshot = mahasiswaSnapshot.child("kehadiran");

                            for (DataSnapshot matkulSnapshot : kehadiranSnapshot.getChildren()) {
                                for (DataSnapshot tanggalSnapshot : matkulSnapshot.getChildren()) {
                                    String tanggal = tanggalSnapshot.getKey();
                                    Boolean hadir = tanggalSnapshot.getValue(Boolean.class);

                                    if (Boolean.TRUE.equals(hadir)) {
                                        tanggal_hadir.add(tanggal);
                                    }
                                }
                            }
                            break; // sudah ketemu karena hanya satu hasil
                        }

                        // Tambahkan ke calendar
                        for (String dateString : tanggal_hadir) {
                            try {
                                Date date = dateFormat.parse(dateString);
                                if (date != null) {
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(date);
                                    eventDays.add(new EventDay(calendar, R.drawable.red_circle));
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Invalid date format: " + dateString, Toast.LENGTH_SHORT).show();
                            }
                        }

                        calendarView.setEvents(eventDays);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseError", error.getMessage());
                    }
                });



        return view;
    }
}
