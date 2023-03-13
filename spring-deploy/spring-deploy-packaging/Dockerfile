# syntax=docker/dockerfile:1
FROM maven:3.9.0-amazoncorretto-17 as builder

WORKDIR /project

COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -T 4C

COPY src/ src

RUN --mount=type=cache,target=/root/.m2 \
    mvn package -T 2C

FROM amazoncorretto:17-alpine

WORKDIR /project

COPY --from=builder /project/target/spring-deploy-packaging*.jar app.jar

ENTRYPOINT [ "java", "-jar", "/project/app.jar" ]