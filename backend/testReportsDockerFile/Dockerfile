FROM gradle:8.5-jdk17 AS builder

WORKDIR /app
COPY . .
RUN ./gradlew test
RUN mkdir -p /output && cp -r build /output
