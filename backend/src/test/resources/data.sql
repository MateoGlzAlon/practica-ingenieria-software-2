-- === Schema Definitions ===

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    github_link VARCHAR(200),
    twitter_link VARCHAR(200),
    website_link VARCHAR(200),
    about TEXT,
    avatar_url TEXT,
    wallet INTEGER DEFAULT 0 NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    tag_id INT,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    votes INT DEFAULT 0,
    state VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS comments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    content TEXT NOT NULL,
    votes INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accepted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS post_images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    image_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS post_votes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    post_id INT,
    user_id INT,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment_votes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    comment_id INT,
    user_id INT,
    FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tips (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT,
    receiver_id INT,
    post_id INT,
    comment_id INT,
    amount INT NOT NULL CHECK (amount > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

-- === Insert Data ===

-- INSERTS: tags
INSERT INTO tags (id, name) VALUES
(1, 'web development'),
(2, 'data science'),
(3, 'machine learning'),
(4, 'python'),
(5, 'javascript'),
(6, 'devops'),
(7, 'cybersecurity'),
(8, 'cloud computing'),
(9, 'blockchain'),
(10, 'mobile development');

-- INSERTS: users
INSERT INTO users (id, name, username, email, password, github_link, twitter_link, website_link, about, avatar_url, role, created_at, wallet) VALUES
(1, 'Alice Anderson', 'alice', 'alice@example.com', 'hashed_pw1', 'https://github.com/alice', 'https://twitter.com/alice', 'https://alice.dev', 'Philosopher and writer', 'https://placehold.co/600x400?text=User1', 'ADMIN', '2025-05-21 21:17:36.022143', 1000),
(2, 'Bob Brown', 'bob', 'bob@example.com', 'hashed_pw2', 'https://github.com/bob', NULL, 'https://bob.dev', 'Lover of Stoicism', 'https://placehold.co/600x400?text=User2', 'USER', '2025-05-21 21:17:36.022143', 1000),
(3, 'Charlie Clark', 'charlie', 'charlie@example.com', 'hashed_pw3', NULL, NULL, NULL, 'Tech and wisdom', 'https://placehold.co/600x400?text=User3', 'USER', '2025-05-21 21:17:36.022143', 1000),
(4, 'Diana Dawson', 'diana', 'diana@example.com', 'hashed_pw4', 'https://github.com/diana', 'https://twitter.com/diana', 'https://diana.dev', 'Meditation fan', 'https://placehold.co/600x400?text=User4', 'USER', '2025-05-21 21:17:36.022143', 1000),
(5, 'Edgar Edwards', 'edgar', 'edgar@example.com', 'hashed_pw5', 'https://github.com/edgar', 'https://twitter.com/edgar', 'https://edgar.dev', 'Stoic entrepreneur', 'https://placehold.co/600x400?text=User5', 'MODERATOR', '2025-05-21 21:17:36.022143', 1000),
(6, 'Fiona Fisher', 'fiona', 'fiona@example.com', 'hashed_pw6', 'https://github.com/fiona', 'https://twitter.com/fiona', 'https://fiona.dev', 'Mindfulness advocate', 'https://placehold.co/600x400?text=User6', 'USER', '2025-05-21 21:17:36.022143', 1000),
(7, 'George Green', 'george', 'george@example.com', 'hashed_pw7', 'https://github.com/george', 'https://twitter.com/george', 'https://george.dev', 'Writing about virtue', 'https://placehold.co/600x400?text=User7', 'USER', '2025-05-21 21:17:36.022143', 1000),
(8, 'Hannah Hill', 'hannah', 'hannah@example.com', 'hashed_pw8', 'https://github.com/hannah', 'https://twitter.com/hannah', 'https://hannah.dev', 'Stoic mom', 'https://placehold.co/600x400?text=User8', 'USER', '2025-05-21 21:17:36.022143', 1000),
(9, 'Ian Irving', 'ian', 'ian@example.com', 'hashed_pw9', 'https://github.com/ian', 'https://twitter.com/ian', 'https://ian.dev', 'Ethics enthusiast', 'https://placehold.co/600x400?text=User9', 'USER', '2025-05-21 21:17:36.022143', 1000),
(10, 'Julia Jones', 'julia', 'julia@example.com', 'hashed_pw10', 'https://github.com/julia', 'https://twitter.com/julia', 'https://julia.dev', 'Lover of logic', 'https://placehold.co/600x400?text=User10', 'USER', '2025-05-21 21:17:36.022143', 1000);

-- INSERTS: posts
INSERT INTO posts (id, user_id, tag_id, title, content, votes, state, created_at) VALUES
(1, 2, 2, E'Intro to Python', E'Learn the basics of **Python programming**:
```python
def greet():
    print("Hello, world!")
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(2, 3, 3, E'JavaScript Tips', E'Here are a few useful JavaScript tricks:
```js
const greet = () => console.log("Hello!");
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(3, 4, 4, E'Deploy with Docker', E'How to deploy an app using **Docker**:
```bash
docker build -t myapp .
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(4, 5, 5, E'Machine Learning 101', E'Start your ML journey with **scikit-learn**:
```python
from sklearn.ensemble import RandomForestClassifier
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(5, 6, 6, E'Version Control with Git', E'Essential Git commands:
```bash
$ git status
$ git add .
$ git commit -m "init"
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(6, 7, 7, E'Cloud with AWS', E'Host your project on AWS using EC2.
> AWS is flexible and powerful.', 0, 'open', '2025-05-21 21:17:36.026944'),

(7, 8, 8, E'Build APIs with Flask', E'Create APIs in Python using Flask:
```python
@app.route("/")
def home():
    return "Hello"
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(8, 9, 9, E'Kubernetes Basics', E'Intro to **Kubernetes** orchestration:
```yaml
apiVersion: v1
kind: Pod
```', 0, 'open', '2025-05-21 21:17:36.026944'),

(9, 10, 10, E'Secure Your App', E'Best practices for app security:
- Use HTTPS
- Sanitize inputs
- Limit access', 0, 'open', '2025-05-21 21:17:36.026944'),

(10, 1, 1, E'Responsive Design', E'Make your site responsive with CSS:
```css
@media (max-width: 600px) {
  body { font-size: 14px; }
}
```', 0, 'open', '2025-05-21 21:17:36.026944');


-- INSERTS: comments
INSERT INTO comments (id, post_id, user_id, content, votes, created_at, accepted) VALUES
(1, 1, 1, 'Very helpful.', 0, '2025-05-21 21:17:36.030344', TRUE),
(2, 1, 2, 'I learned something new!', 0, '2025-05-21 21:17:36.030344', TRUE),
(3, 2, 3, 'I learned something new!', 0, '2025-05-21 21:17:36.030344', FALSE),
(4, 2, 4, 'Thanks for sharing.', 0, '2025-05-21 21:17:36.030344', FALSE),
(5, 3, 5, 'Thanks for sharing.', 0, '2025-05-21 21:17:36.030344', FALSE),
(6, 3, 6, 'Awesome tips!', 0, '2025-05-21 21:17:36.030344', FALSE),
(7, 4, 7, 'Awesome tips!', 0, '2025-05-21 21:17:36.030344', FALSE),
(8, 4, 8, 'Clean and clear explanation.', 0, '2025-05-21 21:17:36.030344', FALSE),
(9, 5, 9, 'Clean and clear explanation.', 0, '2025-05-21 21:17:36.030344', FALSE),
(10, 5, 10, 'Nice code snippet.', 0, '2025-05-21 21:17:36.030344', FALSE),
(11, 6, 1, 'Nice code snippet.', 0, '2025-05-21 21:17:36.030344', FALSE),
(12, 6, 2, 'This is gold.', 0, '2025-05-21 21:17:36.030344', FALSE),
(13, 7, 3, 'This is gold.', 0, '2025-05-21 21:17:36.030344', FALSE),
(14, 7, 4, 'Exactly what I needed.', 0, '2025-05-21 21:17:36.030344', FALSE),
(15, 8, 5, 'Exactly what I needed.', 0, '2025-05-21 21:17:36.030344', FALSE),
(16, 8, 6, 'Love it!', 0, '2025-05-21 21:17:36.030344', FALSE),
(17, 9, 7, 'Love it!', 0, '2025-05-21 21:17:36.030344', FALSE),
(18, 9, 8, 'Great post!', 0, '2025-05-21 21:17:36.030344', FALSE),
(19, 10, 9, 'Great post!', 0, '2025-05-21 21:17:36.030344', FALSE),
(20, 10, 10, 'Very helpful.', 0, '2025-05-21 21:17:36.030344', FALSE);

-- INSERTS: post_images
INSERT INTO post_images (id, post_id, image_url, created_at) VALUES
(1, 1, 'https://placehold.co/600x400?text=Post1', '2025-05-21 21:17:36.028905'),
(2, 2, 'https://placehold.co/600x400?text=Post2', '2025-05-21 21:17:36.028905'),
(3, 3, 'https://placehold.co/600x400?text=Post3', '2025-05-21 21:17:36.028905'),
(4, 4, 'https://placehold.co/600x400?text=Post4', '2025-05-21 21:17:36.028905'),
(5, 5, 'https://placehold.co/600x400?text=Post5', '2025-05-21 21:17:36.028905'),
(6, 6, 'https://placehold.co/600x400?text=Post6', '2025-05-21 21:17:36.028905'),
(7, 7, 'https://placehold.co/600x400?text=Post7', '2025-05-21 21:17:36.028905'),
(8, 8, 'https://placehold.co/600x400?text=Post8', '2025-05-21 21:17:36.028905'),
(9, 9, 'https://placehold.co/600x400?text=Post9', '2025-05-21 21:17:36.028905'),
(10, 10, 'https://placehold.co/600x400?text=Post10', '2025-05-21 21:17:36.028905');

-- INSERTS: tips
INSERT INTO tips (id, sender_id, receiver_id, amount, created_at) VALUES
(1, 2, 1,  250, '2025-05-21 21:17:36.032979'),
(2, 3, 2, 300, '2025-05-21 21:17:37.032979'),
(3, 4, 3, 150, '2025-05-21 21:17:38.032979'),
(4, 5, 4, 500, '2025-05-21 21:17:39.032979'),
(5, 6, 5, 100, '2025-05-21 21:17:40.032979'),
(6, 7, 6,  200, '2025-05-21 21:17:41.032979'),
(7, 8, 7,  180, '2025-05-21 21:17:42.032979'),
(8, 9, 8,  130, '2025-05-21 21:17:43.032979'),
(9, 10, 9, 170, '2025-05-21 21:17:44.032979'),
(10, 1, 10,  220, '2025-05-21 21:17:45.032979');

INSERT INTO post_votes (id, post_id, user_id) VALUES
(1, 1, 2),
(2, 2, 3),
(3, 3, 4),
(4, 4, 5),
(5, 5, 6),
(6, 6, 7),
(7, 7, 8),
(8, 8, 9),
(9, 9, 10),
(10, 10, 1);

INSERT INTO comment_votes (id, comment_id, user_id) VALUES
(1, 1, 3),
(2, 2, 4),
(3, 3, 5),
(4, 4, 6),
(5, 5, 7),
(6, 6, 8),
(7, 7, 9),
(8, 8, 10),
(9, 9, 1),
(10, 10, 2);

