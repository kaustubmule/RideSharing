FROM openjdk:17
LABEL authors="kaumu"
WORKDIR /app
COPY target/ConfigServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
