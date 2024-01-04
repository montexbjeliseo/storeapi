FROM eclipse-temurin:17-jdk-jammy

LABEL authors="montexbjeliseo"

WORKDIR /app

COPY storeapi.jar storeapi.jar

RUN chmod +x storeapi.jar

ENTRYPOINT ["java","-jar","storeapi.jar"]
EXPOSE 8080