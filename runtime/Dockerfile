# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# Copy the whole repo into the build image
COPY . /workspace

# Build only the runtime module (and its dependencies) and produce the jar
RUN ./mvnw -q -pl runtime -am -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Non-root user
RUN useradd -r -u 10001 appuser
USER appuser

COPY --from=build /workspace/runtime/target/runtime-0.1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=local
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
