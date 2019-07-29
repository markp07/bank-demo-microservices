FROM  openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7799

COPY /opt/atlassian/pipelines/agent/build/TransactionsMicroservice/target/EurekaMicroservice-*.jar EurekaMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","EurekaMicroservice.jar"]
