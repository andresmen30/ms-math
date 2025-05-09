# Etapa de build
FROM maven:3.8.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

# Etapa de runtime
FROM eclipse-temurin:21

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/ms-math-0.0.1-SNAPSHOT.jar ms-math-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "ms-math-0.0.1-SNAPSHOT.jar"]