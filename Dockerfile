# --- Etapa de construcción ---
FROM maven:3.9-eclipse-temurin-21 AS build
LABEL stage="build"
WORKDIR /app

# Copiamos solo los archivos necesarios para aprovechar la caché
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Luego copiamos el resto del código
COPY src ./src

# Compilamos el proyecto sin ejecutar tests
RUN mvn -q -DskipTests package

# --- Etapa de ejecución ---
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos solo el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto 8080 (por defecto de Spring Boot)
EXPOSE 8080

# Variables de entorno para personalización
ENV JAVA_OPTS=""

# Arranque del servicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
