#!/bin/bash

# Function to check if Docker is installed
check_docker_installed() {
  if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Installing Docker..."
    install_docker
  else
    echo "Docker is already installed."
  fi
}

# Function to install Docker
install_docker() {
  # Update package list and install prerequisites
  sudo apt-get update
  sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common

  # Add Docker's official GPG key
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

  # Add Docker's APT repository
  sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

  # Update package list again
  sudo apt-get update

  # Install Docker
  sudo apt-get install -y docker-ce

  # Start Docker service
  sudo systemctl start docker
  sudo systemctl enable docker

  echo "Docker installed successfully."
}

# Check if Docker is installed and install if necessary
check_docker_installed

# Check if the Docker volume 'postgres_data' exists, create it if not, and run the Postgres container
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
