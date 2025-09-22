# Multi-stage build for Java Spring Boot application
# Stage 1: Build stage
FROM eclipse-temurin:24-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml first for better layer caching
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn/
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x mvnw && java -version && ./mvnw -v

# Resolve deps into cached ~/.m2
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -T 1C -DskipTests \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN \
    dependency:resolve

# Copy source code
COPY src ./src

# Build the application
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -T 1C -DskipTests \
    -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN \
    clean package

# Stage 2: Runtime stage
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app

# Install curl for health check
RUN apk add --no-cache curl

# Create a non-root user
RUN addgroup -g 1001 -S appuser && \
    adduser -S appuser -u 1001 -G appuser

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose the port the app runs on
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
