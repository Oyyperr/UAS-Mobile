/*package com.example.appreservasi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreservasi.Lapangan;
import com.example.appreservasi.LapanganAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoritActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LapanganAdapter adapter;
    private List<Lapangan> lapanganFavoritList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        recyclerView = findViewById(R.id.recyclerViewFavorit);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lapanganFavoritList = new ArrayList<>();

        // Load lapangan favorit dari SharedPreferences
        loadFavorit();

        // Setup the adapter
        adapter = new LapanganAdapter(lapanganFavoritList, new LapanganAdapter.OnLapanganClickListener() {
            @Override
            public void onItemClick(Lapangan lapangan) {
                // Implement action when user clicks on an item
                Toast.makeText(FavoritActivity.this, "Lapangan: " + lapangan.getNamaLapangan(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public int getUserId() {
                return 0;  // Modify as needed for your app
            }
        }, this);

        // Set the adapter for RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private void loadFavorit() {
        SharedPreferences preferences = getSharedPreferences("UserFavorites", MODE_PRIVATE);
        // Load favorite lapangan from SharedPreferences
        for (int i = 0; i < 100; i++) {  // Loop through possible favorite lapangan
            int lapanganId = preferences.getInt("fav_lapangan_" + i, -1);
            if (lapanganId != -1) {  // If lapangan exists in favorites
                String name = preferences.getString("fav_lapangan_name_" + i, "Unknown");
                String location = preferences.getString("fav_lapangan_location_" + i, "Unknown");
                double price = preferences.getFloat("fav_lapangan_price_" + i, 0);
                String status = preferences.getString("fav_lapangan_status_" + i, "Unknown");

                Lapangan lapangan = new Lapangan(lapanganId, name, location, price, status);
                lapanganFavoritList.add(lapangan);
            }
        }

        // Notify the adapter that data has been loaded
        adapter.notifyDataSetChanged();
    }
}
*/