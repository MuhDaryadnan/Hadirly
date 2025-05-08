package com.example.hadirly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hadirly.admin.AdminActivity;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String nim = sharedPreferences.getString("NIM", null);  // Retrieve NIM
                String role = sharedPreferences.getString("role", null);  // Retrieve role

                if (nim != null && role != null) {
                    // Now you can use NIM and role to display user-specific data
                    if (role.equals("mahasiswa")) {
                        startActivity(new Intent(SplashActivity.this, MahasiswaActivity.class));
                        finish();
                    } else if (role.equals("dosen")) {
                        startActivity(new Intent(SplashActivity.this, DosenActivity.class));
                        finish();
                    }else if (role.equals("admin")) {
                        startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                        finish();
                    }
                } else {
                    // User is not logged in, redirect to login activity
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }


            }
        },3000);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}