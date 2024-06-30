#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Build the Maven project, skipping tests
echo "Building the Maven project..."
mvn clean install -T 100 -U -ntp -ff -DskipTests

# Check if the build was successful
if [ $? -eq 0 ]; then
  echo "Maven build succeeded, starting Docker Compose..."
else
  echo "Maven build failed, exiting..."
  exit 1
fi

# Build and start the Docker containers
docker-compose up --build

# Check if Docker Compose command succeeded
if [ $? -eq 0 ]; then
  echo "Docker Compose started successfully."
else
  echo "Docker Compose failed to start, exiting..."
  exit 1
fi
