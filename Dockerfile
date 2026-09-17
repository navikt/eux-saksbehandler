FROM gcr.io/distroless/java26
COPY target/eux-saksbehandler.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
