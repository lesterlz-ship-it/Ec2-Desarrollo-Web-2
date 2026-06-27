# ============================================================
# Etapa 1: Build con Maven + JDK 17
# ============================================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# ============================================================
# Etapa 2: Runtime con JRE 17 (imagen más ligera)
# ============================================================
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/order-service.jar app.jar

EXPOSE 8082

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget -qO- http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
