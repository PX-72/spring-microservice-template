# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY .. /workspace
RUN ./mvnw -q -pl app -am -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Non-root user
RUN useradd -r -u 10001 appuser
USER appuser

COPY --from=build /workspace/app/target/app-0.1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=local
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
