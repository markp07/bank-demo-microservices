ARG DOCKER_PREFIX
FROM  ${DOCKER_PREFIX}openjdk:11-jdk

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 7788

COPY target/SpringBootAdminMicroservice-*.jar SpringBootAdminMicroservice.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","SpringBootAdminMicroservice.jar"]
