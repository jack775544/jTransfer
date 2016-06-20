CREATE TABLE IF NOT EXISTS users(
    username VARCHAR(50) PRIMARY KEY NOT NULL,
    password VARCHAR(200) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER' NOT NULL
);
INSERT INTO jtransfer.users (username, password, role) VALUES(
    'admin', 'f7d80fe50ffa3bac59072fa10fcf46e37e68e4682cfd38052a68d41232208ad8de3771d5b0a90a5cf5cc7cbb8085192738087c1fd542a1df821a0ced6d8e3449', 'ADMIN'
);