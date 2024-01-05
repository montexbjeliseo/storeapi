FROM eclipse-temurin:17-jdk-jammy

LABEL authors="montexbjeliseo"

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x storeapi.jar
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]