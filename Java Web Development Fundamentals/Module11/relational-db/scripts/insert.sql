INSERT INTO studios (studio_name, country, founded_year, headquarters)
VALUES
    ('Universal Pictures', 'USA', 1912, 'Universal City, CA'),
    ('Metro-Goldwyn-Mayer', 'USA', 1924, 'Beverly Hills, CA'),
    ('Pixar Animation Studios', 'USA', 1986, 'Emeryville, CA'),
    ('Lucasfilm', 'USA', 1971, 'San Francisco, CA'),
    ('Miramax Films', 'USA', 1979, 'Los Angeles, CA');

INSERT INTO Movies (title, release_year, genre, studio_id)
VALUES
    ('Apollo 13', 1995, 'Drama/Adventure', 1),
    ('Ben-Hur', 1959, 'Historical/Drama', 2),
    ('Toy Story', 1995, 'Animation/Comedy', 3),
    ('Star Wars: Episode IV', 1977, 'Sci-Fi/Adventure', 4),
    ('Pulp Fiction', 1994, 'Crime/Drama', 5);
