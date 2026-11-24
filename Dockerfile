# Multi-stage build para Backend Ejercicio5MyAPIGestor
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime ligero
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
LABEL maintainer="Paola Jaqueline López Mata <pjlm@utng.edu.mx>"
LABEL description="Backend REST API - Administrador de Proyectos"
COPY --from=build /app/target/Ejercicio5MyAPIGestor-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8081} -jar app.jar"]
