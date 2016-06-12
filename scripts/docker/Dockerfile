FROM java:8
MAINTAINER Gravitant CTO Team

VOLUME /tmp
ADD ../../target/coreapi-product-1.0.0-SNAPSHOT.jar coreapi-product.jar
RUN bash -c 'touch /coreapi-product.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/coreapi-product.jar"]