package com.example.hadirly;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.databinding.ActivityMahasiswaBinding;


public class MahasiswaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMahasiswaBinding binding = ActivityMahasiswaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        gantiFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.setelan) {
                // aksi untuk setelan
                gantiFragment(new SettingsFragment());
            } else if (item.getItemId() == R.id.kelas) {
                // aksi untuk kelas
                gantiFragment(new ClassFragment());
            } else if (item.getItemId() == R.id.rumah) {
                // aksi untuk rumah
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