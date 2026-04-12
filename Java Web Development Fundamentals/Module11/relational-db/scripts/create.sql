CREATE TABLE studios (
    studio_id INT AUTO_INCREMENT PRIMARY KEY,
    studio_name VARCHAR(100) NOT NULL,
    country VARCHAR(50) NOT NULL,
    founded_year INT NOT NULL,
    headquarters VARCHAR(100) NOT NULL
);

CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    release_year INT NOT NULL,
    genre VARCHAR(50) NOT NULL,
    studio_ID INT NOT NULL,
    FOREIGN KEY (studio_id) REFERENCES studios(studio_id)
);
