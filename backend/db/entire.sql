-- USUARIOS
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
    about TEXT,
    avatar_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ETIQUETAS
CREATE TABLE IF NOT EXISTS tags (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- PUBLICACIONES (POSTS)
CREATE TABLE IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES tags(id) ON DELETE SET NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    likes INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- IMÁGENES DE PUBLICACIONES
CREATE TABLE IF NOT EXISTS post_images (
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- COMENTARIOS
CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    likes INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PROPINAS
CREATE TABLE IF NOT EXISTS tips (
    id SERIAL PRIMARY KEY,
    sender_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    receiver_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    comment_id INTEGER REFERENCES comments(id) ON DELETE CASCADE,
    amount INTEGER NOT NULL CHECK (amount > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- DATOS DE EJEMPLO
-- =====================

-- USERS
INSERT INTO users (username, email, password, about, avatar_url) VALUES
('alice', 'alice@example.com', 'hashed_pw1', 'Philosopher and writer', 'https://placehold.co/600x400?text=User1'),
('bob', 'bob@example.com', 'hashed_pw2', 'Lover of Stoicism', 'https://placehold.co/600x400?text=User2'),
('charlie', 'charlie@example.com', 'hashed_pw3', 'Tech and wisdom', 'https://placehold.co/600x400?text=User3'),
('diana', 'diana@example.com', 'hashed_pw4', 'Meditation fan', 'https://placehold.co/600x400?text=User4'),
('edgar', 'edgar@example.com', 'hashed_pw5', 'Stoic entrepreneur', 'https://placehold.co/600x400?text=User5'),
('fiona', 'fiona@example.com', 'hashed_pw6', 'Mindfulness advocate', 'https://placehold.co/600x400?text=User6'),
('george', 'george@example.com', 'hashed_pw7', 'Writing about virtue', 'https://placehold.co/600x400?text=User7'),
('hannah', 'hannah@example.com', 'hashed_pw8', 'Stoic mom', 'https://placehold.co/600x400?text=User8'),
('ian', 'ian@example.com', 'hashed_pw9', 'Ethics enthusiast', 'https://placehold.co/600x400?text=User9'),
('julia', 'julia@example.com', 'hashed_pw10', 'Lover of logic', 'https://placehold.co/600x400?text=User10');

-- TAGS
INSERT INTO tags (name) VALUES
('stoicism'),         -- 1
('philosophy'),       -- 2
('meditation'),       -- 3
('epictetus'),        -- 4
('seneca'),           -- 5
('marcus aurelius'),  -- 6
('virtue'),           -- 7
('mindfulness'),      -- 8
('parenting'),        -- 9
('daily routine');    -- 10

-- POSTS (asociando un solo tag a cada post)
INSERT INTO posts (user_id, tag_id, title, content, likes) VALUES
(1, 1, 'What is Stoicism?', 'A deep dive into ancient philosophy.', 10),
(2, 10, 'Daily Stoic Habits', 'How I incorporate stoicism into my life.', 7),
(3, 3, 'Meditation and Logos', 'Finding order within.', 5),
(4, 4, 'How Epictetus Changed Me', 'Life lessons from Discourses.', 8),
(5, 5, 'Seneca on Anger', 'Practical advice for temper.', 6),
(6, 6, 'Marcus Aurelius Reflections', 'Journal-style wisdom.', 9),
(7, 7, 'Virtue Over Pleasure', 'A stoic view.', 4),
(8, 2, 'Modern Stoicism', 'Is it still relevant?', 11),
(9, 8, 'Mindfulness vs Stoicism', 'Complement or conflict?', 3),
(10, 9, 'Stoic Parenting', 'Teaching kids the stoic way.', 5);

-- POST IMAGES
INSERT INTO post_images (post_id, image_url) VALUES
(1, 'https://placehold.co/600x400?text=Post1'),
(2, 'https://placehold.co/600x400?text=Post2'),
(3, 'https://placehold.co/600x400?text=Post3'),
(4, 'https://placehold.co/600x400?text=Post4'),
(5, 'https://placehold.co/600x400?text=Post5'),
(6, 'https://placehold.co/600x400?text=Post6'),
(7, 'https://placehold.co/600x400?text=Post7'),
(8, 'https://placehold.co/600x400?text=Post8'),
(9, 'https://placehold.co/600x400?text=Post9'),
(10, 'https://placehold.co/600x400?text=Post10');

-- COMMENTS
INSERT INTO comments (post_id, user_id, content, likes) VALUES
(1, 2, 'Really insightful post!', 3),
(1, 3, 'Thank you for sharing.', 2),
(2, 1, 'This inspired my routine.', 1),
(3, 4, 'Love the reflection!', 4),
(4, 5, 'Epictetus is so underrated.', 2),
(5, 6, 'Great summary of Seneca.', 2),
(6, 7, 'Beautifully written.', 5),
(7, 8, 'Totally agree with your view.', 3),
(8, 9, 'Good analysis.', 1),
(10, 10, 'This helps me as a parent.', 4);

-- TIPS
INSERT INTO tips (sender_id, receiver_id, post_id, comment_id, amount) VALUES
(2, 1, 1, 1, 250),
(3, 2, 2, 3, 300),
(4, 3, 3, 4, 150),
(5, 4, 4, 5, 500),
(6, 5, 5, 6, 100),
(7, 6, 6, 7, 200),
(8, 7, 7, 8, 180),
(9, 8, 8, 9, 130),
(10, 9, 9, 10, 170),
(1, 10, 10, 10, 220);
