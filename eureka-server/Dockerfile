# FAZA 1: Bildovanje (Kompajliranje koda unutar Dockera)
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# FAZA 2: Pokretanje aplikacije (Lagana slika)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
# Kopiramo samo gotov .jar fajl iz FAZE 1
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]