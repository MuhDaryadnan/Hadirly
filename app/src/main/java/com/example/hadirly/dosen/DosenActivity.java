package com.example.hadirly.dosen;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.ClassFragment;
import com.example.hadirly.HomeFragment;
import com.example.hadirly.R;
import com.example.hadirly.SettingsFragment;
import com.example.hadirly.databinding.ActivityMahasiswaBinding;

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