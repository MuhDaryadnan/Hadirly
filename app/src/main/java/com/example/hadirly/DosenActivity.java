package com.example.hadirly;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.databinding.ActivityMahasiswaBinding;
import com.example.hadirly.dosen.DosenClass;
import com.example.hadirly.dosen.DosenHome_Frag;
import com.example.hadirly.dosen.DosenSettings;

public class DosenActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMahasiswaBinding binding = ActivityMahasiswaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        gantiFragment(new DosenHome_Frag());
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedRole = sharedPreferences.getString("role", null);

        Toast.makeText(DosenActivity.this, "kelas kamu: " + savedRole, Toast.LENGTH_LONG).show();


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.setelan) {
                // aksi untuk setelan
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                gantiFragment(new DosenSettings());
            } else if (item.getItemId() == R.id.prodi) {
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