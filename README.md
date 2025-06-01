# practica-inso2
# Plataforma de Ayuda Informática

Este proyecto es una aplicación web desarrollada como práctica de la asignatura de Ingeniería del Software 2. Su objetivo es proporcionar un espacio donde los usuarios pueden recibir asistencia sobre problemas informáticos mediante una comunidad, expertos y herramientas automatizadas.

Incluye:

- Backend en **Java + Spring Boot**
- Frontend en **React + Next.js** 
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
```

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
