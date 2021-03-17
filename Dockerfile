FROM openjdk:11-jre-slim

ENV LANG C.UTF-8

EXPOSE 8080

ADD build/libs/Varadehaldamine-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
