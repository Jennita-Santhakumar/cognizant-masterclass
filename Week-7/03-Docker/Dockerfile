# Multi-stage Dockerfile for a Spring Boot service such as
# Week-4/UserMicroservice. Place this file at the root of that service
# (alongside pom.xml) to build it.

# ---- Stage 1: Build the JAR with Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy dependency descriptor first so Docker can cache the dependency
# download layer separately from source code changes.
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Stage 2: Run the JAR on a slim JRE-only image ----
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Run as a non-root user for better container security.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
