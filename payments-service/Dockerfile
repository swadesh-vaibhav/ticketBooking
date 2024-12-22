FROM eclipse-temurin:21
LABEL authors="swvaibhav"
ADD target/payments-service-0.0.1-SNAPSHOT.jar payments-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "payments-service.jar"]