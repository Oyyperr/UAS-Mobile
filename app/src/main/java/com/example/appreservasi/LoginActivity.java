package com.example.appreservasi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreservasi.DashboardActivity;
import com.example.appreservasi.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;  // Tambahkan variabel untuk tombol register

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);  // Mengambil referensi ke TextView Register

        // Tombol login
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // Lakukan validasi login (misalnya validasi username dan password)
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Username atau Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            } else {
                // Simulasikan login (harus ada pengecekan ke database)
                // Simpan username ke SharedPreferences
                SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", username);
                editor.putInt("user_id", 123);  // Ganti dengan ID pengguna yang benar
                editor.apply();

                // Arahkan ke DashboardActivity setelah login berhasil
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();  // Tutup LoginActivity setelah berhasil login
            }
        });

        // Link untuk pindah ke RegisterActivity saat klik "Daftar di sini"
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);  // Mengarahkan ke RegisterActivity
            startActivity(intent);
        });
    }
}
