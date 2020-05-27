CREATE TABLE post
(
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE photo
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE candidate
(
    id SERIAL PRIMARY KEY,
    photo_id INT,
    name TEXT,
    FOREIGN KEY (photo_id) REFERENCES photo(id)
);

CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);