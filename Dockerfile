 # Base image with Java 17
FROM eclipse-temurin:17-jdk-alpine

# Maintainer info
LABEL maintainer="Chetashree"

# Create working directory
WORKDIR /app

# Copy the jar file from Jenkins build output (mounted in /mnt/jars)
COPY target/*.jar app.jar

# Expose the port your Spring Boot app listens on
EXPOSE 8080

# Run the Spring Boot JAR
ENTRYPOINT ["java", "-jar", "app.jar"]


