package com.example.hadirly;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.databinding.ActivityMahasiswaBinding;


public class MahasiswaActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String nim;
    String kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMahasiswaBinding binding = ActivityMahasiswaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        gantiFragment(new HomeFragment());

        // ✅ Panggil SharedPreferences setelah context tersedia
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        nim = sharedPreferences.getString("NIM", null);
        String kelas = sharedPreferences.getString("kelas", null);  // Retrieve role


        Toast.makeText(MahasiswaActivity.this, "kelas kamu: " + kelas, Toast.LENGTH_LONG).show();


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.setelan) {
                gantiFragment(new SettingsFragment());
            } else if (item.getItemId() == R.id.kelas) {
                gantiFragment(new ClassFragment());
            } else if (item.getItemId() == R.id.rumah) {
                gantiFragment(new HomeFragment());
            }

            return true;
        });
    }

    private void gantiFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
