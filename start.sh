#!/usr/bin/env bash
docker-compose build --no-cache
docker-compose up -d --remove-orphans --build