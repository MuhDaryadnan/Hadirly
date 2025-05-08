package com.example.hadirly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    EditText nama,email;
    TextView nim;
    Button save;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nama = findViewById(R.id.nama_edit);
        email = findViewById(R.id.email_edit);
        nim = findViewById(R.id.nim_edit);
        save = findViewById(R.id.saveBTN);

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedNim = sharedPreferences.getString("NIM", null);
        String savedNama = sharedPreferences.getString("nama", null);
        String savedEmail = sharedPreferences.getString("email", null);
        String savedRole = sharedPreferences.getString("role", null);

        nama.setText(savedNama);
        email.setText(savedEmail);
        nim.setText(savedNim);

        //SIMPAN PERUBAHAN
        save.setOnClickListener(v -> {
            String newNama = nama.getText().toString();
            String newEmail = email.getText().toString();

            // Cek apakah ada perubahan
            if (!newNama.equals(savedNama) || !newEmail.equals(savedEmail)) {
                // Akses reference ke node mahasiswa
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("user").child(savedRole);  // Akses node role

                usersRef.orderByChild("nim").equalTo(savedNim).get().addOnSuccessListener(dataSnapshot -> {
                    if (dataSnapshot.exists()) {
                        // Jika data ditemukan, update field nama dan email
                        for (DataSnapshot userSnap : dataSnapshot.getChildren()) {
                            userSnap.getRef().child("nama").setValue(newNama);
                            userSnap.getRef().child("email").setValue(newEmail);

                            // Feedback ke pengguna
                            Toast.makeText(ProfileActivity.this, "Berhasil Memperbarui Profil", Toast.LENGTH_SHORT).show();

                            // Simpan perubahan di SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nama", newNama);
                            editor.putString("email", newEmail);
                            editor.apply();  // Simpan perubahan lokal
                        }
                    }

                }).addOnFailureListener(e -> {
                    // Jika query gagal
                    Log.d("FirebaseError", "Gagal mengambil data: " + e.getMessage());
                });
            } else {
                // Jika tidak ada perubahan
                Toast.makeText(ProfileActivity.this, "Tidak ada perubahan", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
