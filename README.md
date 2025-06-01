# Stoa
# Plataforma de Ayuda Informática

Este proyecto es una aplicación web desarrollada como práctica de la asignatura de Ingeniería del Software 2. Su objetivo es proporcionar un espacio donde los usuarios pueden recibir asistencia sobre problemas informáticos mediante una comunidad, expertos y herramientas automatizadas.

Incluye:

- Backend en **Java + Spring Boot**
- Frontend en **React + Next.js** 
- Puebas unitarias con **JUnit y Mockito**
- Pruebas de integración con **JUnit y H2**
- Pruebas E2E con **Cypress**
- Orquestación con **Docker Compose**
- Automatización de tareas con **Makefile**

---

## Requisitos previos

- [Docker y Docker Compose](https://docs.docker.com/)
- [Node.js y npm](https://nodejs.org/) (solo si no usas Docker)
- [Java 17+](https://adoptium.net/) (solo si no usas Docker)
- [Make](https://www.gnu.org/software/make/)  
  > En Windows, puedes usar `make` desde WSL o instalarlo con herramientas como [GnuWin32](http://gnuwin32.sourceforge.net/packages/make.htm)

---

## Instalación y ejecución rápida

Clona el repositorio y levanta todo el sistema:

```bash
git clone https://github.com/MateoGlzAlon/practica-ingenieria-software-2.git
cd practica-ingenieria-software-2
make provision
````

> ⚠️ **Importante:** Antes de ejecutar el sistema, asegúrate de configurar correctamente las **variables de entorno** necesarias en el archivo `docker-compose.yml`.
> Estas variables son requeridas tanto para el `backend` como para el `frontend` y definen aspectos clave como el acceso a la base de datos, servicios de autenticación, almacenamiento en la nube y APIs externas.

Consulta la tabla de variables de entorno más abajo para más detalles.


---

## Ejecutar la aplicación sin utilizar `make`

Si no deseas usar `make`, también puedes ejecutar el proyecto manualmente utilizando Docker:

```bash
docker compose up --build
```

---

## Comandos disponibles (`Makefile`)

| Comando                  | Descripción                                              |
|--------------------------|----------------------------------------------------------|
| `make start`             | Inicia backend y frontend en modo desarrollo             |
| `make start.backend`     | Inicia solo el backend                                   |
| `make start.frontend`    | Inicia solo el frontend (modo producción)                |
| `make start.frontend_dev`| Inicia el frontend en modo desarrollo                    |
| `make build`             | Compila backend y frontend                               |
| `make build.backend`     | Compila solo el backend                                  |
| `make build.frontend`    | Compila solo el frontend                                 |
| `make test.backend`      | Ejecuta pruebas unitarias del backend (JUnit + Gradle)   |
| `make test.e2e`          | Ejecuta pruebas end-to-end del frontend (Cypress)        |
| `make provision`         | Construye y levanta todo el sistema con Docker Compose   |


## 📋 Variables de Entorno

| Variable                | Servicio          | Descripción                                                                 |
| ----------------------- | ----------------- | --------------------------------------------------------------------------- |
| `NEXT_PUBLIC_API_URL`   | frontend          | URL base de la API utilizada por el frontend.                               |
| `GOOGLE_CLIENT_ID`      | frontend, backend | ID del cliente de Google para autenticación OAuth2.                         |
| `AWS_BUCKET_NAME`       | frontend          | Nombre del bucket de AWS S3 utilizado para almacenar archivos.              |
| `AWS_BUCKET_REGION`     | frontend          | Región donde se encuentra el bucket de AWS.                                 |
| `AWS_ACCESS_KEY`        | frontend          | Clave de acceso para autenticarse en los servicios de AWS.                  |
| `AWS_SECRET_ACCESS_KEY` | frontend          | Clave secreta vinculada a `AWS_ACCESS_KEY`.                                 |
| `OPENAI_API_KEY`        | frontend          | Clave de API para acceder a los servicios de OpenAI (ChatGPT).              |
| `POSTGRES_HOST`         | backend           | Dirección del host de la base de datos PostgreSQL.                          |
| `POSTGRES_DATABASE`     | backend           | Nombre de la base de datos a utilizar.                                      |
| `POSTGRES_USERNAME`     | backend           | Usuario con permisos para acceder a la base de datos.                       |
| `POSTGRES_PASSWORD`     | backend           | Contraseña del usuario de la base de datos.                                 |
| `GOOGLE_CLIENT_SECRET`  | backend           | Secreto de cliente para autenticación OAuth2 con Google.                    |
