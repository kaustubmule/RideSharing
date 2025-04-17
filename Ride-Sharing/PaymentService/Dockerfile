FROM openjdk:17
LABEL authors="kaumu"
WORKDIR /app
COPY target/PaymentService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
