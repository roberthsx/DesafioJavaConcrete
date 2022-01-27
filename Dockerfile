FROM openjdk:11-jdk
MAINTAINER Robert Xavier
WORKDIR /app
ARG JAR_FILE=DesafioJava-0.0.1-SNAPSHOT.jar
COPY /target/${JAR_FILE} /app/DesafioJavaConcrete.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "DesafioJavaConcrete.jar"]