FROM openjdk:13-jdk-alpine

WORKDIR /home/app
COPY /target/*.jar review-app.jar
ENTRYPOINT ["java","-jar","review-app.jar"]