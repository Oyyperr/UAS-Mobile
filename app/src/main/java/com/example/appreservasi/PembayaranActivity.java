package com.example.appreservasi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PembayaranActivity extends AppCompatActivity {

    private TextView tvPembayaran, tvHarga, tvVirtualAccount;
    private Button btnBayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        // Mendapatkan data dari intent
        double totalHarga = getIntent().getDoubleExtra("total_harga", 0);
        String virtualAccount = getIntent().getStringExtra("virtual_account");

        tvPembayaran = findViewById(R.id.tvPembayaran);
        tvHarga = findViewById(R.id.tvHarga);
        tvVirtualAccount = findViewById(R.id.tvVirtualAccount);
        btnBayar = findViewById(R.id.btnBayar);

        // Menampilkan informasi pembayaran
        tvPembayaran.setText("Pembayaran untuk Reservasi");
        tvHarga.setText("Total Harga: Rp " + totalHarga);
        tvVirtualAccount.setText("Virtual Account: " + virtualAccount);

        btnBayar.setOnClickListener(v -> {
            Toast.makeText(PembayaranActivity.this, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
