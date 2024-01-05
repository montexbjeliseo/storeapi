FROM eclipse-temurin:17-jdk-jammy

LABEL authors="montexbjeliseo"

WORKDIR /app

COPY src ./src
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "target/storeapi-0.0.1-SNAPSHOT.jar"]