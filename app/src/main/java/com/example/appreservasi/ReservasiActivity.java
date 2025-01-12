package com.example.appreservasi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservasiActivity extends AppCompatActivity {

    private TextView tvLapangan, tvHarga;
    private EditText etTanggal, etWaktuMulai, etWaktuSelesai;
    private Button btnReservasi;
    private int lapanganId;
    private String lapanganNama;
    private double hargaLapangan = 50000;  // Misalnya harga per jam lapangan
    private int userId;  // User ID diteruskan dari LapanganActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        // Mendapatkan data dari intent
        Intent intent = getIntent();
        lapanganId = intent.getIntExtra("lapangan_id", -1);
        lapanganNama = intent.getStringExtra("nama_lapangan");
        userId = intent.getIntExtra("user_id", -1);

        // Inisialisasi views
        tvLapangan = findViewById(R.id.tvLapangan);
        tvHarga = findViewById(R.id.tvHarga);
        etTanggal = findViewById(R.id.etTanggal);
        etWaktuMulai = findViewById(R.id.etWaktuMulai);
        etWaktuSelesai = findViewById(R.id.etWaktuSelesai);
        btnReservasi = findViewById(R.id.btnReservasi);

        // Menampilkan nama lapangan yang dipilih
        tvLapangan.setText("Reservasi untuk: " + lapanganNama);

        // Menampilkan harga per jam lapangan
        tvHarga.setText("Harga per Jam: Rp " + hargaLapangan);

        // Tombol reservasi diklik
        btnReservasi.setOnClickListener(v -> {
            // Mengambil input data
            String tanggal = etTanggal.getText().toString();
            String waktuMulai = etWaktuMulai.getText().toString();
            String waktuSelesai = etWaktuSelesai.getText().toString();

            // Memeriksa apakah semua input sudah diisi
            if (tanggal.isEmpty() || waktuMulai.isEmpty() || waktuSelesai.isEmpty()) {
                Toast.makeText(ReservasiActivity.this, "Silakan isi semua data", Toast.LENGTH_SHORT).show();
            } else {
                // Menghitung harga berdasarkan durasi waktu
                double totalHarga = hitungHarga(waktuMulai, waktuSelesai);
                if (totalHarga != -1) {
                    tvHarga.setText("Total Harga: Rp " + totalHarga);  // Menampilkan total harga
                    // Membuat virtual account
                    String virtualAccount = buatVirtualAccount(userId, lapanganId, tanggal, totalHarga);
                    // Melakukan reservasi
                    reservasiLapangan(lapanganId, tanggal, waktuMulai, waktuSelesai, totalHarga, virtualAccount);
                } else {
                    Toast.makeText(ReservasiActivity.this, "Waktu selesai tidak bisa lebih awal dari waktu mulai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Fungsi untuk menghitung harga berdasarkan durasi waktu
    private double hitungHarga(String waktuMulai, String waktuSelesai) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  // Format jam dan menit
            Date mulai = sdf.parse(waktuMulai);
            Date selesai = sdf.parse(waktuSelesai);

            // Menghitung selisih waktu dalam jam
            long selisihMillis = selesai.getTime() - mulai.getTime();
            long selisihJam = selisihMillis / (60 * 60 * 1000);  // Menghitung dalam jam

            // Jika waktu selesai lebih awal dari waktu mulai
            if (selisihJam <= 0) {
                return -1;  // Mengembalikan -1 jika waktu selesai lebih awal dari waktu mulai
            }

            return selisihJam * hargaLapangan;  // Menghitung total harga berdasarkan durasi
        } catch (Exception e) {
            e.printStackTrace();
            return -1;  // Mengembalikan -1 jika ada error dalam parsing waktu
        }
    }

    // Fungsi untuk membuat virtual account
    private String buatVirtualAccount(int userId, int lapanganId, String tanggal, double totalHarga) {
        // Menggabungkan user_id, lapangan_id, tanggal, dan total harga untuk membuat virtual account
        return "VA" + userId + lapanganId + tanggal.replace("-", "") + String.format("%.0f", totalHarga);
    }

    private void reservasiLapangan(int lapanganId, String tanggal, String waktuMulai, String waktuSelesai, double totalHarga, String virtualAccount) {
        // URL untuk memproses reservasi
        String url = "http://10.0.2.2/reservasi_badminton/reservasi.php";  // Ganti dengan URL server Anda

        // Membuat request untuk mengirim data reservasi
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Menampilkan pesan jika berhasil
                        Toast.makeText(ReservasiActivity.this, "Reservasi berhasil!", Toast.LENGTH_SHORT).show();

                        // Mengarahkan ke halaman pembayaran setelah reservasi berhasil
                        Intent intent = new Intent(ReservasiActivity.this, PembayaranActivity.class);
                        intent.putExtra("lapangan_id", lapanganId);  // Mengirimkan ID lapangan
                        intent.putExtra("tanggal", tanggal);  // Mengirimkan tanggal reservasi
                        intent.putExtra("waktu_mulai", waktuMulai);  // Mengirimkan waktu mulai
                        intent.putExtra("waktu_selesai", waktuSelesai);  // Mengirimkan waktu selesai
                        intent.putExtra("total_harga", totalHarga);  // Mengirimkan total harga
                        intent.putExtra("virtual_account", virtualAccount);  // Mengirimkan virtual account
                        startActivity(intent);  // Memulai aktivitas pembayaran
                        finish(); // Menutup ReservasiActivity
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Menampilkan pesan error jika gagal
                        Toast.makeText(ReservasiActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Mengirim data melalui POST
                Map<String, String> params = new HashMap<>();
                params.put("lapangan_id", String.valueOf(lapanganId));
                params.put("tanggal", tanggal);
                params.put("waktu_mulai", waktuMulai);
                params.put("waktu_selesai", waktuSelesai);
                params.put("total_harga", String.valueOf(totalHarga));
                params.put("virtual_account", virtualAccount);  // Menambahkan virtual account ke params
                return params;
            }
        };

        // Menambahkan request ke queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
