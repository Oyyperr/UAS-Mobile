package com.example.appreservasi;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class LapanganAdapter extends BaseAdapter {

    private List<Lapangan> lapanganList;
    private OnLapanganClickListener listener;
    private Context context;

    // Constructor
    public LapanganAdapter(List<Lapangan> lapanganList, OnLapanganClickListener listener, Context context) {
        this.lapanganList = lapanganList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lapanganList.size();
    }

    @Override
    public Object getItem(int position) {
        return lapanganList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_lapangan, null);
        }

        TextView tvNamaLapangan = convertView.findViewById(R.id.tvNamaLapangan);
        TextView tvLokasi = convertView.findViewById(R.id.tvLokasi);
        TextView tvHarga = convertView.findViewById(R.id.tvHarga);
        Button btnBookNow = convertView.findViewById(R.id.btnBookNow);

        final Lapangan lapangan = lapanganList.get(position);
        tvNamaLapangan.setText(lapangan.getNamaLapangan());
        tvLokasi.setText(lapangan.getLokasi());
        tvHarga.setText("Rp " + lapangan.getHarga());

        // Set the "Book Now" button action
        btnBookNow.setOnClickListener(v -> listener.onItemClick(lapangan));

        return convertView;
    }

    // Interface definition
    public interface OnLapanganClickListener {
        void onItemClick(Lapangan lapangan);  // When a lapangan is clicked
        int getUserId();  // Method to get the user ID from the activity
    }
}
