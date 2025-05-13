package com.example.hadirly;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.ClassFragment;
import com.example.hadirly.LoginActivity;
import com.example.hadirly.ProfileActivity;
import com.example.hadirly.R;
import com.example.hadirly.databinding.ActivityMahasiswaBinding;
import com.google.android.material.navigation.NavigationView;

public class DosenActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    SharedPreferences sharedPreferences;
    String nim;
    String kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMahasiswaBinding binding = ActivityMahasiswaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // ✅ Ganti ke DosenHomeFragment di sini
        gantiFragment(new com.example.hadirly.dosen.DosenHomeFragment());

        drawerLayout = findViewById(R.id.drawer_menu);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        // PENTING: Simpan dengan key "nim" (huruf kecil) agar konsisten dengan DosenHomeFragment
        nim = sharedPreferences.getString("nim", null);
        if (nim == null) {
            // Jika "nim" tidak ditemukan, coba dengan key "NIM" (untuk kompatibilitas)
            nim = sharedPreferences.getString("NIM", null);
            // Simpan ulang dengan key yang benar
            if (nim != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nim", nim);
                editor.apply();
            }
        }

        kelas = sharedPreferences.getString("kelas", null);
        String savedRole = sharedPreferences.getString("role", null);

        Toast.makeText(DosenActivity.this, "Role kamu: " + savedRole, Toast.LENGTH_LONG).show();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.setelan) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.prodi) {
                gantiFragment(new ClassFragment());
            } else if (item.getItemId() == R.id.rumah) {
                // ✅ Ganti ke DosenHomeFragment saat tab rumah ditekan
                gantiFragment(new com.example.hadirly.dosen.DosenHomeFragment());
            }
            return true;
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DosenActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(DosenActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    public void gantiFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}