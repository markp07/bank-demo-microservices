FROM  openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7702

COPY /opt/atlassian/pipelines/agent/build/ClientsMicroservice/target/ClientsMicroservice-*.jar ClientsMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ClientsMicroservice.jar"]
