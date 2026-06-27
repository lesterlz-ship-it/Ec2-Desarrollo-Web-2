# ============================================================
# Etapa 1: Build con Maven + JDK 17
# ============================================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias (cache de capas)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# ============================================================
# Etapa 2: Runtime con JRE 17 (imagen más ligera)
# ============================================================
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/target/user-service.jar app.jar

# Exponer el puerto
EXPOSE 8081

# Healthcheck
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget -qO- http://localhost:8081/actuator/health || exit 1

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
