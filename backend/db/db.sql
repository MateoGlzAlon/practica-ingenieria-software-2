-- STEP 1: Run this part connected to the default 'postgres' database
-- It drops and recreates the target database

DROP DATABASE IF EXISTS stoa_db;
CREATE DATABASE stoa_db;

-- STEP 2: Run this part after switching to 'stoa_db'

-- Usuarios
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
    about TEXT,
    avatar_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Publicaciones (Posts)
CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    likes INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Imágenes de publicaciones
CREATE TABLE IF NOT EXISTS post_images (
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comentarios / Respuestas
CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    likes INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Propinas
CREATE TABLE IF NOT EXISTS tips (
    id SERIAL PRIMARY KEY,
    sender_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    receiver_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    comment_id INTEGER REFERENCES comments(id) ON DELETE CASCADE,
    amount INTEGER NOT NULL CHECK (amount > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Etiquetas
CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Relación Post-Tag (muchos a muchos)
CREATE TABLE IF NOT EXISTS post_tags (
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);