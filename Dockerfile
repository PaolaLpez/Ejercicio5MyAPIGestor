# ==============================================================================
# PROYECTO INTEGRADOR DEVOPS - BACKEND REST API (Ejercicio5MyAPIGestor)
# CRITERIO 1 & 2: DOCKER MULTI-STAGE BUILD (Reducción de tamaño y Seguridad)
# ==============================================================================

# ETAPA 1: Compilación y empaquetado (Build Stage con Maven y JDK 21)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Optimización de caché de capas Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Empaquetado del microservicio REST
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Runtime ligero (Seguridad: sin herramientas de compilación en producción)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia del archivo .jar optimizado
COPY --from=build /app/target/Ejercicio5MyAPIGestor-0.0.1-SNAPSHOT.jar app.jar

# Configuración del puerto para Render Cloud (puerto estándar backend: 8081)
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8081} -jar app.jar"]
