#!/bin/bash

brew install mysql
brew install rabbitmq

mysql -h localhost -u gravitant -p gravitant123 -e "CREATE DATABASE gravitant_mysql_product;"