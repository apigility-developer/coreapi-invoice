#!/bin/bash

mvn clean package -Dmaven.test.skip=true  -f ../../pom.xml
java -jar ../../target/coreapi-product-1.0.0-SNAPSHOT.jar \
--spring.jpa.hibernate.ddl-auto=create \
--spring.datasource.driver-class-name=org.hsqldb.jdbc.JDBCDriver \
--eureka.client.enabled=false