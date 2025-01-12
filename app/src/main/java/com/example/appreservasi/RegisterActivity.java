package com.example.appreservasi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi View
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Klik tombol Daftar
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                registerUser(username, email, password);
            } else {
                Toast.makeText(RegisterActivity.this, "Isi semua kolom!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk Registrasi
    private void registerUser(String username, String email, String password) {
        String url = "http://10.0.2.2/reservasi_badminton/register.php"; // Ganti dengan URL server Anda

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle respons dari server
                    if (response.equals("success")) {
                        Toast.makeText(RegisterActivity.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                        finish(); // Kembali ke halaman sebelumnya
                    } else {
                        Toast.makeText(RegisterActivity.this, "Gagal registrasi: " + response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Handle error koneksi
                    Toast.makeText(RegisterActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Kirim data sebagai parameter POST
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Atur retry policy untuk koneksi
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, // Timeout dalam milidetik
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Tambahkan request ke antrian Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
