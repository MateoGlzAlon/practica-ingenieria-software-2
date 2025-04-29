# Directorios
BACKEND_DIR=backend
FRONTEND_DIR=frontend

# Detecta si estás en Windows (ComSpec está presente)
IS_WINDOWS := $(findstring Windows_NT, $(OS))

# Comandos
BACKEND_RUN = $(if $(IS_WINDOWS),gradlew.bat,./gradlew)
BACKEND_BUILD = $(if $(IS_WINDOWS),gradlew.bat clean build,./mvnw clean install)
BACKEND_CLEAN = $(if $(IS_WINDOWS),gradlew.bat clean,./mvnw clean)

# Comandos básicos
.PHONY: all backend frontend start stop build clean

all: start

start:
	@echo "🚀 Iniciando Backend y Frontend..."
	(cd $(BACKEND_DIR) && $(BACKEND_RUN) bootRun) & \
	(cd $(FRONTEND_DIR) && npm run dev)

backend:
	@echo "🛠 Iniciando solo el Backend..."
	cd $(BACKEND_DIR) && $(BACKEND_RUN) bootRun

frontend:
	@echo "🛠 Iniciando solo el Frontend en modo dev..."
	cd $(FRONTEND_DIR) && npm run dev

build:
	@echo "📦 Compilando ambos proyectos..."
	cd $(BACKEND_DIR) && $(BACKEND_BUILD)
	cd $(FRONTEND_DIR) && npm run build

clean:
	@echo "🧹 Limpiando proyecto..."
	cd $(BACKEND_DIR) && $(BACKEND_CLEAN)
	cd $(FRONTEND_DIR) && rm -rf .next

stop:
	@echo "🛑 Usa Ctrl+C para detener procesos o ejecuta kill manual."
