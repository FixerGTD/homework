### Start of builder image
# ------------------------
# Builder stage to prepare application for final image
# Secured and Log4Shell CVE not detected
FROM openjdk:18-alpine3.15 as builder
WORKDIR temp

# Could be set to different jar file location
ARG JAR_FILE=target/*.jar

# Copy fat jar file to current image builder
COPY ${JAR_FILE} application.jar

# Extract the jar file layers
RUN java -Djarmode=layertools -jar --enable-preview application.jar extract

