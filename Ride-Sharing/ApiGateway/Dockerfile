FROM openjdk:17
LABEL authors="kaumu"
WORKDIR /app
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
