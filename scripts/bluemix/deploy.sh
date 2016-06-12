#!/bin/bash

mvn clean package -Dmaven.test.skip=true -f ../../pom.xml

cf unbind-service catalog-api datastore-mysql-product
cf delete-service -f datastore-mysql-product
cf create-service mysql 100 datastore-mysql-product
cf push