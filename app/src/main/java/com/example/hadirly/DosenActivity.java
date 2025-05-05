package com.example.hadirly;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.databinding.ActivityMahasiswaBinding;
import com.example.hadirly.dosen.DosenClass;
import com.example.hadirly.dosen.DosenHome_Frag;
import com.example.hadirly.dosen.DosenSettings;

public class DosenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMahasiswaBinding binding = ActivityMahasiswaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        gantiFragment(new DosenHome_Frag());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.setelan) {
                // aksi untuk setelan
                gantiFragment(new DosenSettings());
            } else if (item.getItemId() == R.id.kelas) {
                // aksi untuk kelas
                gantiFragment(new DosenClass());
            } else if (item.getItemId() == R.id.rumah) {
                // aksi untuk rumah
                gantiFragment(new DosenHome_Frag());
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