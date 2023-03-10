FROM amazoncorretto:17

WORKDIR /project

COPY target/spring-actuator-admin-client*.jar .
RUN ls -la .

ENTRYPOINT [ "java", "-jar", "spring-actuator-admin-client-1.0-SNAPSHOT.jar" ]