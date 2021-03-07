FROM openjdk:8-jdk-alpine
MAINTAINER experto.com
VOLUME /tmp
EXPOSE 8080
ADD build/libs/Varadehaldamine-0.0.1-SNAPSHOT.jar Varadehaldamine-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} springbootdocker.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springbootpostgresqldocker.jar"]