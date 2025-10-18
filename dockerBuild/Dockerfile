# Use a lightweight OpenJDK runtime image
FROM eclipse-temurin:21-jre-alpine

# Set a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY dockerBuild/libs/opentelemetry-javaagent-0.16.1.jar /otel-javaagent.jar

# Create app directory
WORKDIR /app

# Copy your already-built JAR file into the container
COPY target/*.jar app.jar

# Download OpenTelemetry Java Agent JAR (replace with the latest version URL)
# RUN wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.23.0/opentelemetry-javaagent-1.23.0.jar -O /otel-javaagent.jar
# Download OpenTelemetry Java Agent JAR from Maven Central
# RUN wget https://repo1.maven.org/maven2/io/opentelemetry/javaagent/opentelemetry-javaagent/0.16.1/opentelemetry-javaagent-0.16.1.jar -O /otel-javaagent.jar
# Copy the OpenTelemetry Java agent JAR from the local folder
ENV OTEL_EXPORTER_OTLP_ENDPOINT="http://otel-collector.observability.svc.cluster.local:4318"
ENV OTEL_RESOURCE_ATTRIBUTES="service.name=spring-k8s-cache-webhook-coordinator"
ENV OTEL_TRACES_SAMPLER="always_on"

# Expose the port Spring Boot runs on
EXPOSE 8080

# JVM options: tune heap, enable container awareness
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"


# Health check (optional)
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the Spring Boot application with OpenTelemetry Java Agent
# -Dotel.java.global-autoconfigure.enabled=true
ENTRYPOINT ["sh", "-c", "java -javaagent:/otel-javaagent.jar $JAVA_OPTS  -jar app.jar"]
