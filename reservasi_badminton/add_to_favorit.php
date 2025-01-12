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

// Mendapatkan user_id dan lapangan_id dari parameter POST
$user_id = $_POST['user_id'];
$lapangan_id = $_POST['lapangan_id'];

// Query untuk menambahkan lapangan ke favorit
$sql = "INSERT INTO favorit (user_id, lapangan_id) VALUES ('$user_id', '$lapangan_id')";

if ($conn->query($sql) === TRUE) {
    echo "Lapangan berhasil ditambahkan ke favorit!";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

// Menutup koneksi
$conn->close();
?>
