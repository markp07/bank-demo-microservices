FROM  openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7703

COPY target/TransactionsMicroservice-*.jar TransactionsMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","TransactionsMicroservice.jar"]
