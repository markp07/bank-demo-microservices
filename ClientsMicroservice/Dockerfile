ARG DOCKER_PREFIX
FROM  ${DOCKER_PREFIX}openjdk:11-jdk

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7702

COPY target/ClientsMicroservice-*.jar ClientsMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","ClientsMicroservice.jar"]
