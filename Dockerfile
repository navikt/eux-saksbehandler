FROM gcr.io/distroless/java25
COPY target/eux-saksbehandler.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
