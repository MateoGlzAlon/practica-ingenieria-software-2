-- USERS
INSERT INTO users (username, email, password, about, avatar_url) VALUES
('alice', 'alice@example.com', 'hashed_pw1', 'Philosopher and writer', 'https://example.com/avatars/alice.png'),
('bob', 'bob@example.com', 'hashed_pw2', 'Lover of Stoicism', 'https://example.com/avatars/bob.png'),
('charlie', 'charlie@example.com', 'hashed_pw3', 'Tech and wisdom', 'https://example.com/avatars/charlie.png'),
('diana', 'diana@example.com', 'hashed_pw4', 'Meditation fan', 'https://example.com/avatars/diana.png'),
('edgar', 'edgar@example.com', 'hashed_pw5', 'Stoic entrepreneur', 'https://example.com/avatars/edgar.png'),
('fiona', 'fiona@example.com', 'hashed_pw6', 'Mindfulness advocate', 'https://example.com/avatars/fiona.png'),
('george', 'george@example.com', 'hashed_pw7', 'Writing about virtue', 'https://example.com/avatars/george.png'),
('hannah', 'hannah@example.com', 'hashed_pw8', 'Stoic mom', 'https://example.com/avatars/hannah.png'),
('ian', 'ian@example.com', 'hashed_pw9', 'Ethics enthusiast', 'https://example.com/avatars/ian.png'),
('julia', 'julia@example.com', 'hashed_pw10', 'Lover of logic', 'https://example.com/avatars/julia.png');

-- POSTS
INSERT INTO posts (user_id, title, content, likes) VALUES
(1, 'What is Stoicism?', 'A deep dive into ancient philosophy.', 10),
(2, 'Daily Stoic Habits', 'How I incorporate stoicism into my life.', 7),
(3, 'Meditation and Logos', 'Finding order within.', 5),
(4, 'How Epictetus Changed Me', 'Life lessons from Discourses.', 8),
(5, 'Seneca on Anger', 'Practical advice for temper.', 6),
(6, 'Marcus Aurelius Reflections', 'Journal-style wisdom.', 9),
(7, 'Virtue Over Pleasure', 'A stoic view.', 4),
(8, 'Modern Stoicism', 'Is it still relevant?', 11),
(9, 'Mindfulness vs Stoicism', 'Complement or conflict?', 3),
(10, 'Stoic Parenting', 'Teaching kids the stoic way.', 5);

-- POST IMAGES
INSERT INTO post_images (post_id, image_url) VALUES
(1, 'https://example.com/images/stoic1.jpg'),
(2, 'https://example.com/images/stoic2.jpg'),
(3, 'https://example.com/images/stoic3.jpg'),
(4, 'https://example.com/images/stoic4.jpg'),
(5, 'https://example.com/images/stoic5.jpg'),
(6, 'https://example.com/images/stoic6.jpg'),
(7, 'https://example.com/images/stoic7.jpg'),
(8, 'https://example.com/images/stoic8.jpg'),
(9, 'https://example.com/images/stoic9.jpg'),
(10, 'https://example.com/images/stoic10.jpg');

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

-- TAGS
INSERT INTO tags (name) VALUES
('stoicism'),
('philosophy'),
('meditation'),
('epictetus'),
('seneca'),
('marcus aurelius'),
('virtue'),
('mindfulness'),
('parenting'),
('daily routine');

-- POST-TAGS
INSERT INTO post_tags (post_id, tag_id) VALUES
(1, 1), (1, 2),
(2, 1), (2, 10),
(3, 1), (3, 3),
(4, 1), (4, 4),
(5, 1), (5, 5),
(6, 1), (6, 6),
(7, 1), (7, 7),
(8, 1), (8, 2),
(9, 1), (9, 8),
(10, 1), (10, 9);

