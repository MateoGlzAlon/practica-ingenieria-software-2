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
	"summary" TEXT NOT NULL,
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
	"accepted" BOOLEAN DEFAULT FALSE,
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
('Alice Anderson', 'alice', 'alice@example.com', 'hashed_pw1', 'https://github.com/alice', 'https://twitter.com/alice', 'https://alice.dev', 'Philosopher and writer', 'https://placehold.co/600x400?text=User1', 'ADMIN', 1000, '2025-05-22 21:17:36.022143'),
('Bob Brown', 'bob', 'bob@example.com', 'hashed_pw2', 'https://github.com/bob', NULL, 'https://bob.dev', 'Lover of Stoicism', 'https://placehold.co/600x400?text=User2', 'USER', 1000, '2025-05-23 21:17:36.022143'),
('Charlie Clark', 'charlie', 'charlie@example.com', 'hashed_pw3', NULL, NULL, NULL, 'Tech and wisdom', 'https://placehold.co/600x400?text=User3', 'USER', 1000, '2025-05-24 21:17:36.022143'),
('Diana Dawson', 'diana', 'diana@example.com', 'hashed_pw4', 'https://github.com/diana', 'https://twitter.com/diana', 'https://diana.dev', 'Meditation fan', 'https://placehold.co/600x400?text=User4', 'USER', 1000, '2025-05-25 21:17:36.022143'),
('Edgar Edwards', 'edgar', 'edgar@example.com', 'hashed_pw5', 'https://github.com/edgar', 'https://twitter.com/edgar', 'https://edgar.dev', 'Stoic entrepreneur', 'https://placehold.co/600x400?text=User5', 'MODERATOR', 1000, '2025-05-26 21:17:36.022143'),
('Fiona Fisher', 'fiona', 'fiona@example.com', 'hashed_pw6', 'https://github.com/fiona', 'https://twitter.com/fiona', 'https://fiona.dev', 'Mindfulness advocate', 'https://placehold.co/600x400?text=User6', 'USER', 1000, '2025-05-27 21:17:36.022143'),
('George Green', 'george', 'george@example.com', 'hashed_pw7', 'https://github.com/george', 'https://twitter.com/george', 'https://george.dev', 'Writing about virtue', 'https://placehold.co/600x400?text=User7', 'USER', 1000, '2025-05-28 21:17:36.022143'),
('Hannah Hill', 'hannah', 'hannah@example.com', 'hashed_pw8', 'https://github.com/hannah', 'https://twitter.com/hannah', 'https://hannah.dev', 'Stoic mom', 'https://placehold.co/600x400?text=User8', 'USER', 1000, '2025-05-29 21:17:36.022143'),
('Ian Irving', 'ian', 'ian@example.com', 'hashed_pw9', 'https://github.com/ian', 'https://twitter.com/ian', 'https://ian.dev', 'Ethics enthusiast', 'https://placehold.co/600x400?text=User9', 'USER', 1000, '2025-05-30 21:17:36.022143'),
('Julia Jones', 'julia', 'julia@example.com', 'hashed_pw10', 'https://github.com/julia', 'https://twitter.com/julia', 'https://julia.dev', 'Lover of logic', 'https://placehold.co/600x400?text=User10', 'USER', 1000, '2025-05-31 21:17:36.022143');


INSERT INTO "posts" ("user_id", "tag_id", "title", "summary", "content", "votes", "state", "created_at") VALUES
(2, 2, E'How do I write a simple function in Python?', E'An introductory guide to writing a basic function in Python.', E'I am learning **Python programming** and would like to know how to define and use a simple function.

Here is an example I found:

```python
def greet():
    print("Hello, world!")
```

How do I call this function, and are there any best practices for writing simple functions like this? Also, how can I pass arguments to functions or return values from them? Any advice on naming conventions or indentation rules in Python would be helpful.', 0, 'open', '2025-05-21 21:17:36.026944'),

(3, 3, E'What are some useful JavaScript tips for beginners?', E'Some handy JavaScript tips that help beginners write cleaner code.', E'I just started learning JavaScript and want to know what useful tips or tricks I should be aware of. For instance, how do arrow functions work?

```js
const greet = () => console.log("Hello!");
```

When should I use arrow functions, and are there any pitfalls I should avoid? Also, are there key differences between `let`, `const`, and `var` I should know about when declaring variables? Any general advice for writing clean, readable JS code is welcome.', 0, 'open', '2025-05-22 21:17:36.026944'),

(4, 4, E'How can I deploy an app using Docker?', E'A basic tutorial on deploying applications using Docker containers.', E'I want to deploy my app using **Docker**, but I am unsure how to begin. I found this command:

```bash
docker build -t myapp .
```

What are the next steps after building the image? How do I run the container and expose it properly? Also, how do I persist data using Docker volumes or bind mounts, and what should I know about managing environment variables securely within containers?', 0, 'open', '2025-05-23 21:17:36.026944'),

(5, 5, E'How do I get started with machine learning using scikit-learn?', E'Beginner steps to train a model using scikit-learn.', E'I want to start learning machine learning in Python and heard that **scikit-learn** is a good library to use.

I found this example:

```python
from sklearn.ensemble import RandomForestClassifier
clf = RandomForestClassifier()
X = [[0, 0], [1, 1]]
y = [0, 1]
clf.fit(X, y)
```

Can someone explain what this does and how I can evaluate the model? Additionally, how can I split my dataset into training and testing sets, and what metrics should I use to determine model accuracy?', 0, 'open', '2025-05-24 21:17:36.026944'),

(6, 6, E'What are the essential Git commands every developer should know?', E'Core Git commands for version control workflows.', E'I am new to Git and trying to understand the basic commands. I’ve seen examples like:

```bash
git status
git add .
git commit -m "init"
```

Can someone explain what each of these does and what a typical Git workflow looks like? Also, how does branching work in Git, and what are some best practices for managing feature branches and resolving merge conflicts?', 0, 'open', '2025-05-25 21:17:36.026944'),

(7, 7, E'How do I host a project on AWS using EC2?', E'Step-by-step process of deploying a project using Amazon EC2.', E'I would like to deploy my project to the cloud using AWS EC2. I’ve heard that EC2 instances can be used to host applications, but I’m not sure how to set everything up.

What are the steps to create an instance, set up SSH access, and deploy a simple application? What are the typical configurations (security groups, key pairs, etc.), and how do I monitor or scale my instance?', 0, 'open', '2025-05-21 21:17:36.026944'),

(8, 8, E'How do I build a simple API with Flask?', E'A tutorial on setting up and running a basic API using Flask.', E'I want to build a simple REST API in Python and heard that **Flask** is a good tool.

How can I define routes and return responses? Here’s what I have so far:

```python
@app.route("/")
def home():
    return "Hello"
```

How do I run this and test the endpoints? What are best practices for organizing routes, handling errors, and returning JSON data in Flask?', 0, 'open', '2025-05-26 21:17:36.026944'),

(9, 9, E'What is a Pod in Kubernetes and how do I define one?', E'Explanation of Pods and their YAML configuration in Kubernetes.', E'I’m learning **Kubernetes** and I’m a bit confused about how to define a basic pod.

I’ve seen this YAML example:

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: example-pod
spec:
  containers:
  - name: app
    image: nginx
```

Can someone explain what each part means and how I can deploy this? Also, how do I check the pod status, access its logs, and expose it as a service?', 0, 'open', '2025-05-27 21:17:36.026944'),

(10, 10, E'How can I secure my web application?', E'Best practices to protect web applications from common vulnerabilities.', E'I want to make sure my web application is secure. I’ve heard about using HTTPS and sanitizing inputs.

What are the most important steps to secure a basic web app? Are there tools or libraries that can help with this? Additionally, how can I implement authentication securely and protect against threats like XSS, CSRF, and SQL injection?', 0, 'open', '2025-05-28 21:17:36.026944'),

(1, 1, E'How do I make my website responsive using CSS?', E'Using media queries to make web pages mobile-friendly.', E'I want to make my website look good on both desktop and mobile. I’ve heard that CSS media queries are helpful.

Here’s an example:

```css
@media (max-width: 600px) {
  body { font-size: 14px; }
}
```

How do I apply these correctly, and what are other best practices for responsive design? Are there any frameworks or tools that simplify responsive web development?' , 0, 'open', '2025-05-29 21:17:36.026944');


-- INSERTS: comments
INSERT INTO "comments" ("post_id", "user_id", "content", "votes", "created_at", "accepted") VALUES
(1, 1, 'You can call the function by simply writing `greet()` after its definition. For arguments, define them like `def greet(name):` and use `return` to send values back.', 0, '2025-05-21 21:17:36.030344', TRUE),
(1, 2, 'To follow best practices, use snake_case for function names, keep them short and descriptive, and always indent with 4 spaces.', 0, '2025-05-22 21:17:36.030344', TRUE),

(2, 3, 'Arrow functions are best used for short, concise callbacks. Just remember they do not bind `this`, so avoid them when using object methods.', 0, '2025-05-23 21:17:37.030344', FALSE),
(2, 4, '`let` is block-scoped, `const` is for constants, and `var` is function-scoped. Prefer `let` and `const` to avoid scoping bugs.', 0, '2025-05-24 21:17:38.030344', FALSE),

(3, 5, 'After building the image, use `docker run -p 3000:3000 myapp` to run it. Make sure your app is listening on the correct port.', 0, '2025-05-25 21:17:39.030344', FALSE),
(3, 6, 'Use volumes like `-v /host:/container` to persist data. Also, use `.env` files with `--env-file` for environment variables.', 0, '2025-05-26 21:17:40.030344', FALSE),

(4, 7, 'The code creates and trains a simple model. Use `clf.predict([[0, 1]])` to make predictions.', 0, '2025-05-27 21:17:41.030344', FALSE),
(4, 8, 'Use `train_test_split` from `sklearn.model_selection` to split your data. Evaluate with `accuracy_score` or `confusion_matrix`.', 0, '2025-05-28 21:17:42.030344', FALSE),

(5, 9, '`git status` shows changes, `git add .` stages them, and `git commit` saves them. This is the basic workflow.', 0, '2025-05-29 21:17:43.030344', FALSE),
(5, 10, 'Use branches like `git checkout -b feature` to work in isolation. Merge with pull requests to keep history clean.', 0, '2025-05-30 21:17:44.030344', FALSE),

(6, 1, 'Launch an EC2 instance from the AWS console, choose a key pair, and connect via SSH using `ssh -i your-key.pem ec2-user@host`.', 0, '2025-05-31 21:17:45.030344', FALSE),
(6, 2, 'Configure security groups to allow HTTP/HTTPS. Use `systemctl` to run services and `CloudWatch` for monitoring.', 0, '2025-06-01 21:17:46.030344', FALSE),

(7, 3, 'To run the Flask app, add `app.run(debug=True)` at the end of your script and visit `http://localhost:5000`.', 0, '2025-06-02 21:17:47.030344', FALSE),
(7, 4, 'For better structure, organize routes in Blueprints and handle errors with Flask’s `@app.errorhandler` decorators.', 0, '2025-06-03 21:17:48.030344', FALSE),

(8, 5, 'Each section in the YAML defines a part of the pod. Use `kubectl apply -f pod.yaml` to deploy it.', 0, '2025-06-04 21:17:49.030344', FALSE),
(8, 6, 'Check pod status with `kubectl get pods`, access logs with `kubectl logs`, and expose using a `Service` definition.', 0, '2025-06-05 21:17:50.030344', FALSE),

(9, 7, 'Use HTTPS with a valid SSL certificate. You can get one from Let’s Encrypt using Certbot.', 0, '2025-06-06 21:17:51.030344', FALSE),
(9, 8, 'To prevent XSS and CSRF, sanitize all inputs and use tokens in forms. Flask-WTF or Helmet.js are helpful.', 0, '2025-06-07 21:17:52.030344', FALSE),

(10, 9, 'To make sites responsive, use media queries like `@media (max-width: 768px)` and mobile-first layout techniques.', 0, '2025-06-08 21:17:53.030344', FALSE),
(10, 10, 'Frameworks like Bootstrap or TailwindCSS make it easier to build responsive UIs without writing a lot of custom CSS.', 0, '2025-06-09 21:17:54.030344', FALSE);


-- INSERTS: post_images
INSERT INTO "post_images" ( "post_id", "image_url", "created_at") VALUES
(1, 'https://placehold.co/600x400?text=Post1', '2025-05-21 21:17:36.028905'),
(2, 'https://placehold.co/600x400?text=Post2', '2025-05-22 21:17:36.028905'),
(3, 'https://placehold.co/600x400?text=Post3', '2025-05-23 21:17:36.028905'),
(4, 'https://placehold.co/600x400?text=Post4', '2025-05-24 21:17:36.028905'),
(5, 'https://placehold.co/600x400?text=Post5', '2025-05-25 21:17:36.028905'),
(6, 'https://placehold.co/600x400?text=Post6', '2025-05-26 21:17:36.028905'),
(7, 'https://placehold.co/600x400?text=Post7', '2025-05-27 21:17:36.028905'),
(8, 'https://placehold.co/600x400?text=Post8', '2025-05-28 21:17:36.028905'),
(9, 'https://placehold.co/600x400?text=Post9', '2025-05-29 21:17:36.028905'),
( 10, 'https://placehold.co/600x400?text=Post10', '2025-05-30 21:17:36.028905');

-- INSERTS: tips
INSERT INTO "tips" ("sender_id", "receiver_id", "amount", "created_at") VALUES
(2, 1, 250, '2025-06-21 21:17:36.032979'),
(3, 2, 300, '2025-06-21 21:17:37.032979'),
(4, 3, 150, '2025-06-21 21:17:38.032979'),
(5, 4, 500, '2025-06-21 21:17:39.032979'),
(6, 5, 100, '2025-06-21 21:17:40.032979'),
(7, 6, 200, '2025-06-21 21:17:41.032979'),
(8, 7, 180, '2025-06-21 21:17:42.032979'),
(9, 8, 130, '2025-06-21 21:17:43.032979'),
(10, 9, 170, '2025-06-21 21:17:44.032979'),
(1, 10, 220, '2025-06-21 21:17:45.032979');

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('tags_id_seq', (SELECT MAX(id) FROM tags));
SELECT setval('posts_id_seq', (SELECT MAX(id) FROM posts));
SELECT setval('comments_id_seq', (SELECT MAX(id) FROM comments));
SELECT setval('post_images_id_seq', (SELECT MAX(id) FROM post_images));
SELECT setval('post_votes_id_seq', (SELECT MAX(id) FROM post_votes));
SELECT setval('comment_votes_id_seq', (SELECT MAX(id) FROM comment_votes));
SELECT setval('tips_id_seq', (SELECT MAX(id) FROM tips));
