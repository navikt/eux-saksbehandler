FROM gcr.io/distroless/java21
COPY target/eux-saksbehandler.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
