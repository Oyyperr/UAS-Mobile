package com.example.appreservasi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Tombol back
            getSupportActionBar().setDisplayShowTitleEnabled(false); // Menghilangkan judul
        }

        // Inisialisasi TextViews
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);

        // Mengambil data dari SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = preferences.getString("username", "Tidak ditemukan");
        String email = preferences.getString("email", "Tidak ditemukan");

        // Debugging: Pastikan email diambil dengan benar
        System.out.println("Email diambil: " + email);  // Gunakan log untuk melihat

        // Menampilkan data di TextView
        tvUsername.setText("Username: " + username);
        tvEmail.setText("Email: " + email);  // Menampilkan email
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Kembali ke halaman sebelumnya
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
