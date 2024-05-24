#!/usr/bin/env bash
docker-compose -f mock-server.yml up -d &&
echo "Mock server is up and running ..."