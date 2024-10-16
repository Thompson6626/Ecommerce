# Use the official Gradle image for building the app. 
# We're picking a lightweight Alpine 
FROM gradle:8.7.0-jdk-lts-and-current-alpine AS build

# Set the working directory to /app inside the container.
WORKDIR /app

# Copy the essential Gradle build files (build.gradle and settings.gradle) 
# so that Gradle knows how to build the project.
COPY build.gradle settings.gradle /app/

# Copy over the entire source code of the project.
COPY src /app/src

# Run Gradle to clean up any previous builds and package the app into a .jar file. 
# We're skipping tests here to make the build faster.
RUN gradle clean build -x test

# Now we switch to a much smaller runtime image. 
# Amazon Corretto is a lightweight version of OpenJDK and a great fit for running Java apps.
FROM amazoncorretto:21.0.4-alpine3.20

# Set the working directory where the app will run.
WORKDIR /app

# Copy the final built .jar file from the previous build stage into this runtime container.
# The '--from=build' tells Docker to grab the .jar from the first stage.
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8088 so that our Spring Boot app can accept traffic.
EXPOSE 8088

# Finally, run the Spring Boot app by executing the JAR file.
ENTRYPOINT ["java", "-jar", "app.jar"]
