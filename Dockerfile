FROM gradle:8.10-jdk21 AS build
WORKDIR /app

# Cache Gradle dependencies (only invalidated when build.gradle changes)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN ./gradlew --no-daemon dependencies

# Build application
COPY . .
RUN ./gradlew --no-daemon bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/standoFit-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
