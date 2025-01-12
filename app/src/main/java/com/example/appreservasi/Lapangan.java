package com.example.appreservasi;

public class Lapangan {
    private int id;
    private String namaLapangan;
    private String lokasi;
    private double harga;
    private String status;

    // Constructor
    public Lapangan(int id, String namaLapangan, String lokasi, double harga, String status) {
        this.id = id;
        this.namaLapangan = namaLapangan;
        this.lokasi = lokasi;
        this.harga = harga;
        this.status = status;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getNamaLapangan() {
        return namaLapangan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public double getHarga() {
        return harga;
    }

    public String getStatus() {
        return status;
    }
}
