FROM eclipse-temurin:21-jdk-alpine

WORKDIR /carrefour-mvp

COPY target/carrefour-mvp.jar /carrefour-mvp/carrefour-mvp.jar

EXPOSE 9988

ENTRYPOINT ["java", "-jar", "/carrefour-mvp/carrefour-mvp.jar"]