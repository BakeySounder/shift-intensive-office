FROM gradle:7.5.1-jdk17 as builder
WORKDIR /opt/app
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY ./src ./src
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/build/libs/shift-intensive-0.0.1.jar .

ENTRYPOINT ["java","-jar","/opt/app/shift-intensive-0.0.1.jar"]