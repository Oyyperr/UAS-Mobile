package com.example.appreservasi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnProfile, btnReservasi, btnFavorit, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi Views
        tvWelcome = findViewById(R.id.tvWelcome);
        btnProfile = findViewById(R.id.btnProfile);
        btnReservasi = findViewById(R.id.btnReservasi);
        btnFavorit = findViewById(R.id.btnFavorit);
        btnLogout = findViewById(R.id.btnLogout);

        // Ambil data username dari SharedPreferences
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = preferences.getString("username", null);

        // Tampilkan ucapan selamat datang
        if (username != null && !username.isEmpty()) {
            tvWelcome.setText("Selamat datang, " + username + "!");
        } else {
            tvWelcome.setText("Selamat datang!");
        }

        // Tombol Profil Pengguna
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Tombol Reservasi Lapangan
        btnReservasi.setOnClickListener(v -> {
            // Mengambil user_id dari SharedPreferences
            int userId = preferences.getInt("user_id", -1);  // Mengambil ID user yang sudah login

            // Intent untuk menuju LapanganActivity dan mengirimkan user_id
            Intent intent = new Intent(DashboardActivity.this, LapanganActivity.class);
            intent.putExtra("user_id", userId);  // Mengirimkan user_id
            startActivity(intent);  // Membuka LapanganActivity untuk memilih lapangan
        });

        // Tombol Daftar Favorit
        //btnFavorit.setOnClickListener(v -> {
           // Intent intent = new Intent(DashboardActivity.this );
          //  startActivity(intent);
       // });

        // Tombol Logout
        btnLogout.setOnClickListener(v -> {
            // Hapus data sesi
            preferences.edit().clear().apply();

            // Tampilkan pesan logout
            Toast.makeText(DashboardActivity.this, "Anda telah logout", Toast.LENGTH_SHORT).show();

            // Kembali ke LoginActivity
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
