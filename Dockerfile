# ---- Stage 1: Build the app using Maven ----
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the JAR, skip tests for faster build
RUN mvn clean package -DskipTests

# ---- Stage 2: Run the app using lightweight JRE ----
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
