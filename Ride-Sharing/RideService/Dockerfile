FROM openjdk:17
LABEL authors="kaumu"
WORKDIR /app
COPY target/RideService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
