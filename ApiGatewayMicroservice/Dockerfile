FROM  openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7700

COPY /opt/atlassian/pipelines/agent/build/ApiGatewayMicroservice/target/ApiGatewayMicroservice-*.jar ApiGatewayMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ApiGatewayMicroservice.jar"]
