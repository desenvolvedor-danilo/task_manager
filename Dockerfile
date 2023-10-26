FROM ubuntu/mysql:latest as build
RUN apt update
RUN apt-get install openjdk-17-jdk -y
RUN apt install maven -y
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-slim

EXPOSE 8080

COPY --from=build /target/todolist-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java","-jar","app.jar" ]