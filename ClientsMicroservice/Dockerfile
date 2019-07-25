FROM  p-nexus-3.development.nl.eu.abnamro.com:18445/openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7702

COPY target/ClientsMicroservice-*.jar ClientsMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ClientsMicroservice.jar"]
