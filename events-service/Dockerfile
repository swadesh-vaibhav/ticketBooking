FROM eclipse-temurin:21
LABEL authors="swvaibhav"
ADD target/events-service-0.0.1-SNAPSHOT.jar events-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "events-service.jar"]