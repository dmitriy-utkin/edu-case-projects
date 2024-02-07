@echo off

cd ..

gradlew.bat wrapper
gradlew.bat clean
gradlew.bat build -x testClasses -x test

docker build -t crypto-bot -f docker\Dockerfile .

docker-compose -f docker\docker-compose.yml up
docker-compose -f docker\docker-compose.yml down --volumes --remove-orphans
