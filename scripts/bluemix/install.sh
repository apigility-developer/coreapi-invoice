#!/bin/bash

cf service datastore-mysql-product | grep 'not found' &> /dev/null
if [ $? == 0 ]; then
  cf create-service mysql 100 datastore-mysql-product
else
  echo "Service datastore-mysql-product already exists"
fi

cf service sharedservice-eventbroker | grep 'not found' &> /dev/null
if [ $? == 0 ]; then
  cf create-service cloudamqp lemur sharedservice-eventbroker
else
  echo "Service sharedservice-eventbroker already exists"
fi