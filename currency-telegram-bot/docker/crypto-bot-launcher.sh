#!/bin/sh

cd ../
./gradlew wrapper
./gradlew clean
./gradlew build -x testClasses -x test

docker build -t crypto-bot -f docker/Dockerfile .

docker-compose -f docker/docker-compose.yml up
docker-compose -f docker/docker-compose.yml down --volumes --remove-orphans