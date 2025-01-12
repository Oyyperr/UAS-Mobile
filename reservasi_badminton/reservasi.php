<?php
// Connect to the database
$servername = "localhost";
$username = "root";  
$password = "";      
$dbname = "reservasi_badminton";  

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get the data from POST
$lapangan_id = $_POST['lapangan_id'];
$tanggal = $_POST['tanggal'];
$waktu_mulai = $_POST['waktu_mulai'];
$waktu_selesai = $_POST['waktu_selesai'];
$user_id = $_POST['user_id'];  // User ID from the client

// Calculate the total price (this can be dynamic or predefined)
$harga_per_jam = 50000;  // Assuming it's fixed for now
$selisih = strtotime($waktu_selesai) - strtotime($waktu_mulai);
$hours = $selisih / 3600;
$total_harga = $hours * $harga_per_jam;

// Insert the reservation data into the database
$sql = "INSERT INTO reservasi (lapangan_id, tanggal, waktu_mulai, waktu_selesai, user_id, total_harga) 
        VALUES ('$lapangan_id', '$tanggal', '$waktu_mulai', '$waktu_selesai', '$user_id', '$total_harga')";

// Execute query and check if it's successful
if ($conn->query($sql) === TRUE) {
    echo "Reservasi berhasil!";
} else {
    echo "Error: " . $conn->error;
}

// Close connection
$conn->close();
?>
