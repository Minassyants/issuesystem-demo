FROM eclipse-temurin:22_36-jre-alpine
COPY target/issuesystem-0.0.1-SNAPSHOT.jar issuesystem.jar
ENTRYPOINT [ "java", "-jar", "/issuesystem.jar", "--spring.profiles.active=prod" ]