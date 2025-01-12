<?php
$conn = new mysqli("localhost", "root", "", "reservasi_badminton");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Terima data melalui POST atau GET
$method = $_SERVER['REQUEST_METHOD'];
if ($method == 'POST' || $method == 'GET') {
    // Ambil data dari POST atau GET
    $username = isset($_REQUEST['username']) ? $_REQUEST['username'] : null;
    $email = isset($_REQUEST['email']) ? $_REQUEST['email'] : null;
    $password = isset($_REQUEST['password']) ? $_REQUEST['password'] : null;

    if (!empty($username) && !empty($email) && !empty($password)) {
        // Hash password (hanya untuk POST)
        if ($method == 'POST') {
            $password = password_hash($password, PASSWORD_DEFAULT);
        }

        // Insert data ke database
        $sql = "INSERT INTO users (username, email, password) VALUES ('$username', '$email', '$password')";

        if ($conn->query($sql) === TRUE) {
            echo "success";
        } else {
            echo "SQL Error: " . $conn->error;
        }
    } else {
        echo "Missing data. Please provide username, email, and password.";
    }
} else {
    echo "Invalid request method.";
}

$conn->close();
?>
