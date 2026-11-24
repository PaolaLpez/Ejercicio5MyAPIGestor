# Multi-stage build para Backend Ejercicio5MyAPIGestor
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
# Descargar dependencias en caché
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime ligero
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
LABEL maintainer="Paola Jaqueline López Mata <pjlm@utng.edu.mx>"
LABEL description="Backend REST API - Administrador de Proyectos"
COPY --from=build /app/target/Ejercicio5MyAPIGestor-0.0.1-SNAPSHOT.jar app.jar
ENV SERVER_PORT=8081
ENV SPRING_DATASOURCE_URL=jdbc:mariadb://mariadb:3306/dbgestor
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
