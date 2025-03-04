FROM gradle:8.1.1-jdk17

WORKDIR /opt/app

COPY --chown=gradle:gradle . .

RUN gradle build --no-daemon -x test

ENV PROFILE=docker

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${PROFILE} -jar build/libs/backend-0.0.1-SNAPSHOT.jar"]