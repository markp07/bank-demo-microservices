FROM  p-nexus-3.development.nl.eu.abnamro.com:18445/openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7700

COPY target/ApiGatewayMicroservice-*.jar ApiGatewayMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ApiGatewayMicroservice.jar"]
