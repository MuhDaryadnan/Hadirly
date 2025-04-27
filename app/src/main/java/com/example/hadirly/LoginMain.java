package com.example.hadirly;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hadirly.dosen.DosenActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMain extends AppCompatActivity {
    TextView username, pass, forgot;
    EditText input_username, input_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // TextViews and EditTexts
        username = findViewById(R.id.usernametxt);
        pass = findViewById(R.id.password);
        input_username = findViewById(R.id.usernameEdit);
        input_pass = findViewById(R.id.password_edit);
        Button masukgan = findViewById(R.id.loginbtn);

        // Button Login click listener
        masukgan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = input_username.getText().toString();  // Getting NIM as username
                String password = input_pass.getText().toString();  // Getting password

                if (nim.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginMain.this, "NIM atau password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Access Firebase database once
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean loginSuccessful = false;

                        // Accessing the "mahasiswa" data
                        DataSnapshot mahasiswaSnapshot = dataSnapshot.child("mahasiswa");
                        loginSuccessful = checkLogin(mahasiswaSnapshot, nim, password);
                        if (loginSuccessful) {
                            Intent myIntent = new Intent(LoginMain.this, MahasiswaActivity.class);
                            startActivity(myIntent);
                            finish();
                            return;
                        }

                        // Accessing the "dosen" data
                        DataSnapshot dosenSnapshot = dataSnapshot.child("dosen");
                        loginSuccessful = checkLogin(dosenSnapshot, nim, password);
                        if (loginSuccessful) {
                            Intent myIntent = new Intent(LoginMain.this, DosenActivity.class);
                            startActivity(myIntent);
                            finish();
                            return;
                        }

                        // If login is unsuccessful
                        if (!loginSuccessful) {
                            Toast.makeText(LoginMain.this, "NIM atau Password salah", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginMain.this, "Gagal mengambil data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private boolean checkLogin(DataSnapshot snapshot, String nim, String password) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String storedNim = String.valueOf(data.child("nim").getValue()); // Mengonversi ke String
                    String storedPass = String.valueOf(data.child("pass").getValue()); // Mengonversi ke String
                    if (storedNim != null && storedPass != null && storedNim.equals(nim) && storedPass.equals(password)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}