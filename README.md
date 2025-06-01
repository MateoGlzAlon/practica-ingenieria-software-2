# Stoa  
## IT Support Platform

This project is a web application developed as a practical assignment for the Software Engineering 2 course. Its goal is to provide a space where users can receive help with computer-related problems through a community, experts, and automated tools.

It includes:

- Backend with **Java + Spring Boot**  
- Frontend with **React + Next.js**  
- Unit testing with **JUnit and Mockito**  
- Integration testing with **JUnit and H2**  
- End-to-end testing with **Cypress**  
- Orchestration with **Docker Compose**  
- Task automation with **Makefile**

---

## Prerequisites

- [Docker and Docker Compose](https://docs.docker.com/)  
- [Node.js and npm](https://nodejs.org/) (only if not using Docker)  
- [Java 17+](https://adoptium.net/) (only if not using Docker)  
- [Make](https://www.gnu.org/software/make/)  
  > On Windows, you can use `make` via WSL or install it with tools like [GnuWin32](http://gnuwin32.sourceforge.net/packages/make.htm)

---

## Quick Installation and Run

Clone the repository and start the entire system:

```bash
git clone https://github.com/MateoGlzAlon/practica-ingenieria-software-2.git
cd practica-ingenieria-software-2
make provision
```

> **Important:** Before running the system, make sure to correctly configure the **required environment variables** in the `docker-compose.yml` file.  
> These variables are needed by both the `backend` and the `frontend`, and define key aspects such as database access, authentication services, cloud storage, and external APIs.

See the environment variables table below for more details.

---

## Running the Application Without `make`

If you prefer not to use `make`, you can also run the project manually using Docker:

```bash
docker compose up --build
```

---

## Available Commands (`Makefile`)

| Command                   | Description                                              |
|---------------------------|----------------------------------------------------------|
| `make start`              | Starts backend and frontend in development mode          |
| `make start.backend`      | Starts the backend only                                  |
| `make start.frontend`     | Starts the frontend in production mode                   |
| `make start.frontend_dev` | Starts the frontend in development mode                  |
| `make build`              | Builds both backend and frontend                         |
| `make build.backend`      | Builds the backend only                                  |
| `make build.frontend`     | Builds the frontend only                                 |
| `make test.backend`       | Runs backend unit tests (JUnit + Gradle)                 |
| `make test.e2e`           | Runs frontend end-to-end tests (Cypress)                 |
| `make provision`          | Builds and launches the full system with Docker Compose  |

---

## Environment Variables

| Variable                  | Service           | Description                                                                 |
|---------------------------|-------------------|-----------------------------------------------------------------------------|
| `NEXT_PUBLIC_API_URL`     | frontend           | Base URL of the API used by the frontend.                                   |
| `GOOGLE_CLIENT_ID`        | frontend, backend  | Google client ID for OAuth2 authentication.                                 |
| `AWS_BUCKET_NAME`         | frontend           | Name of the AWS S3 bucket used for file storage.                            |
| `AWS_BUCKET_REGION`       | frontend           | Region where the AWS bucket is located.                                     |
| `AWS_ACCESS_KEY`          | frontend           | Access key for authenticating with AWS services.                            |
| `AWS_SECRET_ACCESS_KEY`   | frontend           | Secret key associated with `AWS_ACCESS_KEY`.                                |
| `OPENAI_API_KEY`          | frontend           | API key to access OpenAI services (ChatGPT).                                |
| `POSTGRES_HOST`           | backend            | Host address of the PostgreSQL database.                                    |
| `POSTGRES_DATABASE`       | backend            | Name of the database to use.                                                |
| `POSTGRES_USERNAME`       | backend            | User with access permissions to the database.                               |
| `POSTGRES_PASSWORD`       | backend            | Password of the database user.                                              |
| `GOOGLE_CLIENT_SECRET`    | backend            | Client secret for OAuth2 authentication with Google.                        |
