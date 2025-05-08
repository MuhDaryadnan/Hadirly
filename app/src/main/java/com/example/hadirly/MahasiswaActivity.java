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

import com.example.hadirly.databinding.ActivityMahasiswaBinding;
import com.google.android.material.navigation.NavigationView;

public class MahasiswaActivity extends AppCompatActivity {

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
        gantiFragment(new com.example.hadirly.HomeFragment());

        drawerLayout = findViewById(R.id.drawer_menu);

        // ✅ Panggil SharedPreferences setelah context tersedia
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        nim = sharedPreferences.getString("NIM", null);
        kelas = sharedPreferences.getString("kelas", null);  // Retrieve role
        String savedRole = sharedPreferences.getString("role", null);

        Toast.makeText(MahasiswaActivity.this, "kelas kamu: " + savedRole, Toast.LENGTH_LONG).show();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.setelan) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.prodi) {
                gantiFragment(new ClassFragment());
            } else if (item.getItemId() == R.id.rumah) {
                gantiFragment(new com.example.hadirly.HomeFragment());
            }
            return true;
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        //side nav
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                // Clear SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();

                // Navigate to login or main activity
                Intent intent = new Intent(MahasiswaActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                // Go to another activity
                Intent intent = new Intent(MahasiswaActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // Changed to public so it can be accessed from fragments
    public void gantiFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}