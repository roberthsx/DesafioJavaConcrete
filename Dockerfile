FROM openjdk:11-jdk
MAINTAINER Robert Xavier
WORKDIR /app
COPY out/artifacts/DesafioJavaConcrete_jar/DesafioJavaConcrete.jar /app/DesafioJavaConcrete.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "DesafioJavaConcrete.jar"]