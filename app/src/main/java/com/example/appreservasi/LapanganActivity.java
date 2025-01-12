package com.example.appreservasi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LapanganActivity extends AppCompatActivity {

    private ListView listView;
    private LapanganAdapter adapter;
    private List<Lapangan> lapanganList = new ArrayList<>();
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapangan);

        // Get user_id from the intent
        userId = getIntent().getIntExtra("user_id", -1);

        // Initialize ListView
        listView = findViewById(R.id.listViewLapangan);

        // Initialize the adapter and set the listener
        adapter = new LapanganAdapter(lapanganList, new LapanganAdapter.OnLapanganClickListener() {
            @Override
            public void onItemClick(Lapangan lapangan) {
                Intent intent = new Intent(LapanganActivity.this, ReservasiActivity.class);
                intent.putExtra("lapangan_id", lapangan.getId());
                intent.putExtra("nama_lapangan", lapangan.getNamaLapangan());
                intent.putExtra("user_id", getUserId());
                startActivity(intent);
            }

            @Override
            public int getUserId() {
                return userId;  // Return the user ID from the activity
            }
        }, LapanganActivity.this);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        // Load lapangan data
        loadLapangan();
    }

    private void loadLapangan() {
        String url = "http://10.0.2.2/reservasi_badminton/get_lapangan.php";  // Your server URL
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject lapanganObj = response.getJSONObject(i);
                                    String namaLapangan = lapanganObj.getString("nama_lapangan");
                                    String lokasi = lapanganObj.getString("lokasi");
                                    double harga = lapanganObj.getDouble("harga");
                                    String status = lapanganObj.getString("status");
                                    int id = lapanganObj.getInt("id");

                                    Lapangan lapangan = new Lapangan(id, namaLapangan, lokasi, harga, status);
                                    lapanganList.add(lapangan);
                                }
                                // Notify the adapter to refresh the list
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LapanganActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
