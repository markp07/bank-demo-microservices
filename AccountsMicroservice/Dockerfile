FROM  openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7701

COPY target/AccountsMicroservice-*.jar AccountsMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","AccountsMicroservice.jar"]
