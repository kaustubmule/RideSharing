FROM openjdk:17
LABEL authors="kaumu"
WORKDIR /app
COPY target/EurekaServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
    