#!/usr/bin/env bash
# Check if the Docker volume 'postgres_data' exists
if ! docker volume ls --format '{{.Name}}' | grep -q '^postgres_data$'; then
  # Pull the latest Postgres image
  echo "Pulling latest Postgres image..."
  docker pull postgres

  # Create the Docker volume 'postgres_data'
  echo "Creating Docker volume 'postgres_data'..."
  docker volume create postgres_data
else
  echo "Docker volume 'postgres_data' already exists. Skipping volume creation."
fi

# Run the Postgres container
echo "Running Postgres container..."
docker start postgres_container

# List the running containers
echo "Listing running containers..."
docker ps

echo "Postgres container setup complete."