<?php
$conn = new mysqli("localhost", "root", "", "reservasi_badminton");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $user_id = isset($_POST['user_id']) ? $_POST['user_id'] : null;
    $username = isset($_POST['username']) ? $_POST['username'] : null;
    $email = isset($_POST['email']) ? $_POST['email'] : null;

    // Pastikan user_id, username, dan email tidak kosong
    if ($user_id && ($username || $email)) {
        // Mulai query untuk update
        $query = "UPDATE users SET ";
        $params = [];

        // Update username jika ada perubahan
        if ($username) {
            $query .= "username = ?, ";
            $params[] = $username;
        }

        // Update email jika ada perubahan
        if ($email) {
            $query .= "email = ?, ";
            $params[] = $email;
        }

        // Hapus koma di akhir query
        $query = rtrim($query, ", ");
        $query .= " WHERE id = ?";

        // Siapkan pernyataan
        $stmt = $conn->prepare($query);
        $params[] = $user_id; // Tambahkan user_id di akhir
        $stmt->bind_param(str_repeat("s", count($params) - 1) . "i", ...$params); // Bind dynamic parameters

        if ($stmt->execute()) {
            echo json_encode([
                "status" => "success",
                "message" => "Profil berhasil diperbarui"
            ]);
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "Gagal memperbarui profil"
            ]);
        }

        $stmt->close();
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Data tidak lengkap. Pastikan username atau email diisi."
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
