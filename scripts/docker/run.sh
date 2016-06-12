#!/bin/bash

MACHINE_NAME=gravitant-product-vb-node

docker-machine create -d virtualbox --virtualbox-memory 4096 $MACHINE_NAME
eval $(docker-machine env $MACHINE_NAME)
mvn clean package -Dmaven.test.skip=true  -f ../../pom.xml
docker build -t gravitant/coreapi-product
docker login -e="$DOCKER_EMAIL" -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
docker-compose -p gravitant up -d