# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy only the pom.xml to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. Copy the source code and build the jar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# 3. Copy the built jar from the build stage
# Ensure the name matches your build artifact (usually search-service-0.0.1-SNAPSHOT.jar)
COPY --from=build /app/target/*.jar search-service.jar

# 4. Expose the port (matches your server.port=8084)
EXPOSE 8084

# 5. Run the application
ENTRYPOINT ["java", "-jar", "search-service.jar"]