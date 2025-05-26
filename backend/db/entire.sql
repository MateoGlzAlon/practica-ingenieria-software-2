-- Volcando estructura para tabla public.users
CREATE TABLE IF NOT EXISTS "users" (
	"id" SERIAL NOT NULL,
	"name" VARCHAR(50) NOT NULL,
	"username" VARCHAR(50) NOT NULL,
	"email" VARCHAR(100) NOT NULL,
	"password" VARCHAR(500) NOT NULL,
	"github_link" VARCHAR(200),
	"twitter_link" VARCHAR(200),
	"website_link" VARCHAR(200),
	"about" TEXT,
	"avatar_url" TEXT,
	"role" VARCHAR(50) NOT NULL,
	"wallet" INTEGER DEFAULT 0 NOT NULL,
	"created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id"),
	CONSTRAINT users_username_key UNIQUE ("username"),
	CONSTRAINT users_email_key UNIQUE ("email")
);

-- Volcando estructura para tabla public.tags
CREATE TABLE IF NOT EXISTS "tags" (
	"id" SERIAL NOT NULL,
	"name" VARCHAR(50) NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT tags_name_key UNIQUE ("name")
);

-- Volcando estructura para tabla public.posts
CREATE TABLE IF NOT EXISTS "posts" (
	"id" SERIAL NOT NULL,
	"user_id" INTEGER DEFAULT NULL,
	"tag_id" INTEGER DEFAULT NULL,
	"title" TEXT NOT NULL,
	"content" TEXT NOT NULL,
	"votes" INTEGER DEFAULT 0,
	"state" VARCHAR(50) NOT NULL,
	"created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id"),
	CONSTRAINT "posts_tag_id_fkey" FOREIGN KEY ("tag_id") REFERENCES "tags" ("id") ON DELETE SET NULL,
	CONSTRAINT "posts_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

-- Volcando estructura para tabla public.comments
CREATE TABLE IF NOT EXISTS "comments" (
	"id" SERIAL NOT NULL,
	"post_id" INTEGER DEFAULT NULL,
	"user_id" INTEGER DEFAULT NULL,
	"content" TEXT NOT NULL,
	"votes" INTEGER DEFAULT 0,
	"created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id"),
	CONSTRAINT "comments_post_id_fkey" FOREIGN KEY ("post_id") REFERENCES "posts" ("id") ON DELETE CASCADE,
	CONSTRAINT "comments_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

-- Volcando estructura para tabla public.post_images
CREATE TABLE IF NOT EXISTS "post_images" (
	"id" SERIAL NOT NULL,
	"post_id" INTEGER DEFAULT NULL,
	"image_url" TEXT NOT NULL,
	"created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY ("id"),
	CONSTRAINT "post_images_post_id_fkey" FOREIGN KEY ("post_id") REFERENCES "posts" ("id") ON DELETE CASCADE
);

-- Volcando estructura para tabla public.post_votes
CREATE TABLE IF NOT EXISTS "post_votes" (
	"id" SERIAL NOT NULL,
	"post_id" INTEGER DEFAULT NULL,
	"user_id" INTEGER DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "post_votes_post_id_fkey" FOREIGN KEY ("post_id") REFERENCES "posts" ("id") ON DELETE CASCADE,
	CONSTRAINT "post_votes_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

-- Volcando estructura para tabla public.comment_votes
CREATE TABLE IF NOT EXISTS "comment_votes" (
	"id" SERIAL NOT NULL,
	"comment_id" INTEGER DEFAULT NULL,
	"user_id" INTEGER DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "comment_votes_comment_id_fkey" FOREIGN KEY ("comment_id") REFERENCES "comments" ("id") ON DELETE CASCADE,
	CONSTRAINT "comment_votes_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

-- Volcando estructura para tabla public.tips
CREATE TABLE IF NOT EXISTS "tips" (
    "id" SERIAL NOT NULL,
    "sender_id" INTEGER NOT NULL,
    "receiver_id" INTEGER NOT NULL,
    "amount" INTEGER NOT NULL CHECK (amount > 0),
    "created_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY ("id"),
    CONSTRAINT "tips_sender_id_fkey" FOREIGN KEY ("sender_id") REFERENCES "users" ("id") ON DELETE CASCADE,
    CONSTRAINT "tips_receiver_id_fkey" FOREIGN KEY ("receiver_id") REFERENCES "users" ("id") ON DELETE CASCADE
);


-- INSERTS: tags
INSERT INTO "tags" ("name") VALUES
('web development'),
('data science'),
('machine learning'),
('python'),
('javascript'),
('devops'),
('cybersecurity'),
('cloud computing'),
('blockchain'),
( 'mobile development');

-- INSERTS: users
INSERT INTO "users" ( "name", "username", "email", "password", "github_link", "twitter_link", "website_link", "about", "avatar_url", "role", "wallet","created_at") VALUES
('Alice Anderson', 'alice', 'alice@example.com', 'hashed_pw1', 'https://github.com/alice', 'https://twitter.com/alice', 'https://alice.dev', 'Philosopher and writer', 'https://placehold.co/600x400?text=User1', 'ADMIN', 1000, '2025-05-21 21:17:36.022143'),
('Bob Brown', 'bob', 'bob@example.com', 'hashed_pw2', 'https://github.com/bob', NULL, 'https://bob.dev', 'Lover of Stoicism', 'https://placehold.co/600x400?text=User2', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('Charlie Clark', 'charlie', 'charlie@example.com', 'hashed_pw3', NULL, NULL, NULL, 'Tech and wisdom', 'https://placehold.co/600x400?text=User3', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('Diana Dawson', 'diana', 'diana@example.com', 'hashed_pw4', 'https://github.com/diana', 'https://twitter.com/diana', 'https://diana.dev', 'Meditation fan', 'https://placehold.co/600x400?text=User4', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('Edgar Edwards', 'edgar', 'edgar@example.com', 'hashed_pw5', 'https://github.com/edgar', 'https://twitter.com/edgar', 'https://edgar.dev', 'Stoic entrepreneur', 'https://placehold.co/600x400?text=User5', 'MODERATOR', 1000, '2025-05-21 21:17:36.022143'),
('Fiona Fisher', 'fiona', 'fiona@example.com', 'hashed_pw6', 'https://github.com/fiona', 'https://twitter.com/fiona', 'https://fiona.dev', 'Mindfulness advocate', 'https://placehold.co/600x400?text=User6', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('George Green', 'george', 'george@example.com', 'hashed_pw7', 'https://github.com/george', 'https://twitter.com/george', 'https://george.dev', 'Writing about virtue', 'https://placehold.co/600x400?text=User7', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('Hannah Hill', 'hannah', 'hannah@example.com', 'hashed_pw8', 'https://github.com/hannah', 'https://twitter.com/hannah', 'https://hannah.dev', 'Stoic mom', 'https://placehold.co/600x400?text=User8', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('Ian Irving', 'ian', 'ian@example.com', 'hashed_pw9', 'https://github.com/ian', 'https://twitter.com/ian', 'https://ian.dev', 'Ethics enthusiast', 'https://placehold.co/600x400?text=User9', 'USER', 1000, '2025-05-21 21:17:36.022143'),
('Julia Jones', 'julia', 'julia@example.com', 'hashed_pw10', 'https://github.com/julia', 'https://twitter.com/julia', 'https://julia.dev', 'Lover of logic', 'https://placehold.co/600x400?text=User10', 'USER', 1000, '2025-05-21 21:17:36.022143');
-- INSERTS: posts
INSERT INTO "posts" ("user_id", "tag_id", "title", "content", "votes", "state", "created_at") VALUES
(2, 2, E'Intro to Python', E'Learn the basics of **Python programming**:
```python
def greet():
    print("Hello, world!")
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 3, 3, E'JavaScript Tips', E'Here are a few useful JavaScript tricks:
```js
const greet = () => console.log("Hello!");
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 4, 4, E'Deploy with Docker', E'How to deploy an app using **Docker**:
```bash
docker build -t myapp .
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 5, 5, E'Machine Learning 101', E'Start your ML journey with **scikit-learn**:
```python
from sklearn.ensemble import RandomForestClassifier
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 6, 6, E'Version Control with Git', E'Essential Git commands:
```bash
$ git status
$ git add .
$ git commit -m "init"
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 7, 7, E'Cloud with AWS', E'Host your project on AWS using EC2.
> AWS is flexible and powerful.', 0, 'open', '2025-05-21 21:17:36.026944'),

( 8, 8, E'Build APIs with Flask', E'Create APIs in Python using Flask:
```python
@app.route("/")
def home():
    return "Hello"
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 9, 9, E'Kubernetes Basics', E'Intro to **Kubernetes** orchestration:
```yaml
apiVersion: v1
kind: Pod
```', 0, 'open', '2025-05-21 21:17:36.026944'),

( 10, 10, E'Secure Your App', E'Best practices for app security:
- Use HTTPS
- Sanitize inputs
- Limit access', 0, 'open', '2025-05-21 21:17:36.026944'),

( 1, 1, E'Responsive Design', E'Make your site responsive with CSS:
```css
@media (max-width: 600px) {
  body { font-size: 14px; }
}
```', 0, 'open', '2025-05-21 21:17:36.026944');


-- INSERTS: comments
INSERT INTO "comments" ( "post_id", "user_id", "content", "votes", "created_at") VALUES
(1, 1, 'Very helpful.', 0, '2025-05-21 21:17:36.030344'),
(1, 2, 'I learned something new!', 0, '2025-05-21 21:17:36.030344'),
(2, 3, 'I learned something new!', 0, '2025-05-21 21:17:36.030344'),
(2, 4, 'Thanks for sharing.', 0, '2025-05-21 21:17:36.030344'),
(3, 5, 'Thanks for sharing.', 0, '2025-05-21 21:17:36.030344'),
(3, 6, 'Awesome tips!', 0, '2025-05-21 21:17:36.030344'),
(4, 7, 'Awesome tips!', 0, '2025-05-21 21:17:36.030344'),
(4, 8, 'Clean and clear explanation.', 0, '2025-05-21 21:17:36.030344'),
(5, 9, 'Clean and clear explanation.', 0, '2025-05-21 21:17:36.030344'),
( 5, 10, 'Nice code snippet.', 0, '2025-05-21 21:17:36.030344'),
( 6, 1, 'Nice code snippet.', 0, '2025-05-21 21:17:36.030344'),
( 6, 2, 'This is gold.', 0, '2025-05-21 21:17:36.030344'),
( 7, 3, 'This is gold.', 0, '2025-05-21 21:17:36.030344'),
( 7, 4, 'Exactly what I needed.', 0, '2025-05-21 21:17:36.030344'),
( 8, 5, 'Exactly what I needed.', 0, '2025-05-21 21:17:36.030344'),
( 8, 6, 'Love it!', 0, '2025-05-21 21:17:36.030344'),
( 9, 7, 'Love it!', 0, '2025-05-21 21:17:36.030344'),
( 9, 8, 'Great post!', 0, '2025-05-21 21:17:36.030344'),
( 10, 9, 'Great post!', 0, '2025-05-21 21:17:36.030344'),
( 10, 10, 'Very helpful.', 0, '2025-05-21 21:17:36.030344');

-- INSERTS: post_images
INSERT INTO "post_images" ( "post_id", "image_url", "created_at") VALUES
(1, 'https://placehold.co/600x400?text=Post1', '2025-05-21 21:17:36.028905'),
(2, 'https://placehold.co/600x400?text=Post2', '2025-05-21 21:17:36.028905'),
(3, 'https://placehold.co/600x400?text=Post3', '2025-05-21 21:17:36.028905'),
(4, 'https://placehold.co/600x400?text=Post4', '2025-05-21 21:17:36.028905'),
(5, 'https://placehold.co/600x400?text=Post5', '2025-05-21 21:17:36.028905'),
(6, 'https://placehold.co/600x400?text=Post6', '2025-05-21 21:17:36.028905'),
(7, 'https://placehold.co/600x400?text=Post7', '2025-05-21 21:17:36.028905'),
(8, 'https://placehold.co/600x400?text=Post8', '2025-05-21 21:17:36.028905'),
(9, 'https://placehold.co/600x400?text=Post9', '2025-05-21 21:17:36.028905'),
( 10, 'https://placehold.co/600x400?text=Post10', '2025-05-21 21:17:36.028905');

-- INSERTS: tips
INSERT INTO "tips" ("sender_id", "receiver_id", "amount", "created_at") VALUES
(2, 1, 250, '2025-05-21 21:17:36.032979'),
(3, 2, 300, '2025-05-21 21:17:37.032979'),
(4, 3, 150, '2025-05-21 21:17:38.032979'),
(5, 4, 500, '2025-05-21 21:17:39.032979'),
(6, 5, 100, '2025-05-21 21:17:40.032979'),
(7, 6, 200, '2025-05-21 21:17:41.032979'),
(8, 7, 180, '2025-05-21 21:17:42.032979'),
(9, 8, 130, '2025-05-21 21:17:43.032979'),
(10, 9, 170, '2025-05-21 21:17:44.032979'),
(1, 10, 220, '2025-05-21 21:17:45.032979');

-- 🛠️ Reset sequences to match the highest ID in each table
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('tags_id_seq', (SELECT MAX(id) FROM tags));
SELECT setval('posts_id_seq', (SELECT MAX(id) FROM posts));
SELECT setval('comments_id_seq', (SELECT MAX(id) FROM comments));
SELECT setval('post_images_id_seq', (SELECT MAX(id) FROM post_images));
SELECT setval('post_votes_id_seq', (SELECT MAX(id) FROM post_votes));
SELECT setval('comment_votes_id_seq', (SELECT MAX(id) FROM comment_votes));
SELECT setval('tips_id_seq', (SELECT MAX(id) FROM tips));
