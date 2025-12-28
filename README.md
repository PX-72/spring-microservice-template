# Spring Microservice Template

This repository is a starting point for building **production-ready Java microservices**.

The goal is not to show off frameworks, but to give you a clean, modern baseline that:

- builds reliably
- is easy to extend
- follows current enterprise conventions
- stays out of your way

I use this as a base for real projects.

---

## What this template gives you

### Core stack
- **Java 21 (LTS)**
- **Spring Boot 3.5**
- **Maven multi-module project**

### Architecture

spring-microservice-template
 - domain -> pure business logic (no Spring dependencies)
 - adapters -> persistence, external systems, implementations
 - runtime -> Spring Boot application (API, wiring, config)


This keeps business logic testable and prevents Spring from leaking everywhere.

### Runtime features
- REST API with validation
- Consistent error responses using **Problem Details**
- Actuator endpoints:
  - health / liveness / readiness
  - metrics
- Structured logging
  - readable logs locally
  - JSON logs via profile
- Trace/log correlation ready (OpenTelemetry + W3C)

### Testing
- **Unit tests** (fast, no DB required)
- Ready for integration tests (can be added later with Testcontainers)

### Build & packaging
- Maven Wrapper (`./mvnw`)
- Dockerfile for container builds
- `compose.yaml` for local Postgres when needed

---

## Why this exists

This is the setup I wish every new Java service started with:

- no magic
- no hidden coupling
- no accidental complexity
- no framework lock-in

It is intentionally boring in the right ways.

---

## Quick start

### Requirements
- Java 21
- Docker (optional, only needed if you enable DB)

### Build & run tests

```bash
./mvnw clean test
```

---

Build the runtime
```
./mvnw -pl runtime -am package
```

Run locally (no DB yet)
```
./mvnw -pl runtime spring-boot:run
```

Enable JSON logging
```
SPRING_PROFILES_ACTIVE=json ./mvnw -pl runtime spring-boot:run
```

---

Logging

Default: readable console logs
JSON logs: activate profile json

JSON logs already include:
 - timestamp
- log level
- logger
- message
- MDC
- traceId / spanId

This works well with any modern log platform.

---

Actuator

Once running:
```
/actuator/health
/actuator/health/liveness
/actuator/health/readiness
/actuator/metrics
/actuator/prometheus
```

How to extend this

Typical next steps for a real service:

1. Add your domain model in domain 
2. Add persistence or external clients in adapters 
3. Expose endpoints in runtime 
4. Introduce DB via profile (e.g. postgres)
5. Add integration tests with Testcontainers 
6. Add outbound HTTP client config (timeouts, retries, etc.)

Philosophy

This template prefers:
- explicit over clever
- boring over fragile
- small pieces over big frameworks
- things you can reason about at 2am
