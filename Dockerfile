FROM gcr.io/distroless/java21
COPY eux-saksbehandler-webapp/target/eux-saksbehandler.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
