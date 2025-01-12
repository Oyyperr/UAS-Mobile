<?php
$conn = new mysqli("localhost", "root", "", "reservasi_badminton");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = isset($_POST['username']) ? $_POST['username'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';

    if (!empty($username) && !empty($password)) {
        $sql = "SELECT * FROM users WHERE username = '$username'";
        $result = $conn->query($sql);

        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            if (password_verify($password, $row['password'])) {
                // Login berhasil
                echo json_encode([
                    "status" => "success",
                    "message" => "Login berhasil",
                    "user_id" => $row['id'], // Kirim user ID untuk sesi
                    "username" => $row['username']
                ]);
            } else {
                echo json_encode([
                    "status" => "error",
                    "message" => "Password salah"
                ]);
            }
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "Username tidak ditemukan"
            ]);
        }
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Data tidak lengkap"
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Invalid request method"
    ]);
}

$conn->close();
?>
    