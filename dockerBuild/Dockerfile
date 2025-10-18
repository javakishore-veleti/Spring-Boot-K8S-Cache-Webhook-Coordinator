# Use a lightweight OpenJDK runtime image
FROM eclipse-temurin:21-jre-alpine

# Set a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Create app directory
WORKDIR /app

# Copy your already-built JAR file into the container
# (replace target/*.jar with your actual path)
COPY target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# JVM options: tune heap, enable container awareness
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

# Health check (optional)
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
