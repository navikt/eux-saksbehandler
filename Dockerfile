FROM gcr.io/distroless/java21
COPY neessi-api-webapp/target/neessi-api.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
