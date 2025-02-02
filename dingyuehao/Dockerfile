FROM openjdk:8-jdk-slim
LABEL maintainer=wanfeng
COPY target/dingyuehao-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=${PROFILES}"]
