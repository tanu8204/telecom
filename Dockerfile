# # Use OpenJDK 17 as the base image for the build stage
# FROM openjdk:17-jdk-slim AS build

# # Set the working directory in the container
# WORKDIR /app

# # Copy the project files into the container
# COPY . /app

# # Make the gradlew script executable (if using Gradle)
# RUN chmod +x gradlew

# # If you're using Maven, replace the Gradle build command with Maven build
# # RUN ./gradlew build  # Uncomment if using Gradle
# RUN mvn clean install -DskipTests  # Uncomment if using Maven

# # Use OpenJDK 17 slim image for the runtime environment
# FROM openjdk:17-jdk-slim

# # Expose the port that the application will run on
# EXPOSE 8081  # Change to your app's port if different (default is 8080)

# # Create a directory in the container for the application
# RUN mkdir /app

# # Copy the built JAR file from the build stage to the runtime stage
# COPY --from=build /app/target/Telecomm-0.0.1-SNAPSHOT.jar /app/application.jar

# # Set the entry point to run the Spring Boot application
# ENTRYPOINT ["java", "-jar", "/app/application.jar"]

# Use JDK 17 slim image for the runtime environment
FROM openjdk:17-jdk-slim

# Expose the application port
EXPOSE 8081

# Copy the built JAR file to the container
COPY Telecomm-0.0.1-SNAPSHOT.jar /app/application.jar

# Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/application.jar"]

