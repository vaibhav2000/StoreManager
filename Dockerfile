FROM gradle:8.14.2-jdk17 as build
COPY . .
RUN gradle clean

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/TSM-0.0.1-SNAPSHOT.jar tsm.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar", "tsm.jar"]