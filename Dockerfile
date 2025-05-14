FROM eclipse-temurin:21-jdk-alpine AS build
COPY . /
RUN chmod +x ./gradlew
RUN ./gradlew clean build --no-daemon

FROM eclipse-temurin:21-jre-alpine AS run
COPY --from=build /build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]

