// Contoh get_lapangan.php
<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "reservasi_badminton";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM lapangan";  // Mengambil semua data lapangan
$result = $conn->query($sql);

$lapanganList = [];
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $lapanganList[] = $row;
    }
    echo json_encode($lapanganList);
} else {
    echo json_encode([]);
}

$conn->close();
?>
