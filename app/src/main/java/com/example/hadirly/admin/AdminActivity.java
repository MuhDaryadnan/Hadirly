package com.example.hadirly.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hadirly.LoginActivity;
import com.example.hadirly.R;
import com.example.hadirly.admin.dosen.AdminDosenFragment;
import com.example.hadirly.admin.kelas.AdminKelasFragment;
import com.example.hadirly.admin.mahasiswa.AdminMahasiswaFragment;
import com.google.android.material.navigation.NavigationView;


public class AdminActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        gantiFragment(new AdminHomeFragment());
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Make sure to not use fullscreen or transparent status bar
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        drawerLayout = findViewById(R.id.drawer_layout_admin);
        navigationView = findViewById(R.id.nav_view_admin);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    gantiFragment(new AdminHomeFragment());
                } else if (id == R.id.mahasiswa) {
                    gantiFragment(new AdminMahasiswaFragment());
                } else if (id == R.id.dosen) {
                    gantiFragment(new AdminDosenFragment());
                } else if (id == R.id.prodi) {
                    gantiFragment(new AdminKelasFragment());
                } else if (id == R.id.logout) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(AdminActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();

                    // Navigate to login or main activity
                    Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return true;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
    public void gantiFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
