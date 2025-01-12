<?php
// Menghubungkan ke database
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "reservasi_badminton";  // Ganti dengan nama database Anda

// Membuat koneksi
$conn = new mysqli($servername, $username, $password, $dbname);

// Cek koneksi
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Mendapatkan user_id dari parameter GET
$user_id = $_GET['user_id'];

// Ambil data lapangan favorit
$sql = "SELECT lapangan.id, lapangan.nama_lapangan, lapangan.lokasi, lapangan.harga, lapangan.status
        FROM lapangan
        JOIN favorit ON lapangan.id = favorit.lapangan_id
        WHERE favorit.user_id = '$user_id'";

$result = $conn->query($sql);

$favoritList = [];
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $favoritList[] = $row;
    }
}

// Mengembalikan data dalam format JSON
echo json_encode($favoritList);

// Menutup koneksi
$conn->close();
?>
