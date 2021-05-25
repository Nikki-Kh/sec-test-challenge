FROM openjdk:13-jdk-alpine
WORKDIR /home/app
COPY /target/*.jar product-app.jar
ENTRYPOINT ["java","-jar","product-app.jar"]