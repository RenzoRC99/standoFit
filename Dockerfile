FROM gradle:8.10-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/standoFit-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
