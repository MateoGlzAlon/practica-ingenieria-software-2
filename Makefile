# Directorios
BACKEND_DIR=backend
FRONTEND_DIR=frontend

# Comandos
BACKEND_RUN=./gradlew run
BACKEND_BUILD=./gradlew build
BACKEND_TEST=./gradlew test
E2E_TEST=npx cypress run

.PHONY: all start start.backend start.frontend start.frontend_dev build build.backend build.frontend test.backend test.e2e provision

all: start

start:
	@echo "== Iniciando Backend y Frontend =="
	(cd $(BACKEND_DIR) && $(BACKEND_RUN) spring-boot:run) & \
	(cd $(FRONTEND_DIR) && npm run dev)

start.backend:
	@echo "== Iniciando solo el Backend =="
	cd $(BACKEND_DIR) && $(BACKEND_RUN) spring-boot:run

start.frontend:
	@echo "== Iniciando solo el Frontend =="
	cd $(FRONTEND_DIR) && npm run start

start.frontend_dev:
	@echo "== Iniciando solo el Frontend (dev)=="
	cd $(FRONTEND_DIR) && npm run dev

build:
	@echo "== Buildeando backend =="
	cd $(BACKEND_DIR) && $(BACKEND_BUILD)
	cd $(FRONTEND_DIR) && npm run build

build.backend:
	@echo " == Buildeando backend =="
	cd $(BACKEND_DIR) && $(BACKEND_BUILD)

build.frontend:
	@echo " == Buildeando f proyectos =="
	cd $(FRONTEND_DIR) && npm run build	

test.backend:
	@echo "== Ejecutando pruebas del backend =="
	cd $(BACKEND_DIR) && $(BACKEND_TEST)

test.e2e:
	@echo "== Ejecutando pruebas E2E del frontend =="
	cd $(FRONTEND_DIR) && $(E2E_TEST)

provision:
	@echo "== Levantando el sistema (docker compose) =="
	docker compose up -d --build

