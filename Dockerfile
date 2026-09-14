FROM eclipse-temurin:17-jre

COPY target/Cinenew-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]