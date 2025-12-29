# Spring Microservice Template

This repository is a starting point for building **production-ready Java microservices**.

The goal is not to show off frameworks, but to give you a clean, modern baseline that:

- builds reliably
- is easy to extend
- follows current enterprise conventions
- stays out of your way

I use this as a base for real projects.

---

### Architecture style: Ports and Adapters (Hexagonal)

This template follows the **Ports and Adapters** (also called **Hexagonal**) architecture.

- The **core** of the system (domain + use cases) knows nothing about frameworks, databases, messaging systems, or transports.
- Everything that touches the outside world is an **adapter**.
- The runtime module wires the whole system together.

This makes the service:
- easier to test
- easier to change infrastructure
- safer to extend with new transports (REST, Kafka, gRPC, MCP, WebSockets, etc.)

#### How it maps to this project

domain
- core model + business rules
- inbound ports (use cases)
- outbound ports (interfaces)

adapters/in/
- REST controllers
- Kafka consumers
- gRPC services
- WebSocket handlers
- MCP handlers

adapters/out/
- persistence (JPA, JDBC, etc.)
- messaging producers
- external service clients
- caches

runtime
- Spring Boot entrypoint
- wiring & configuration
- selecting which adapters are active

Inbound adapters translate external input into calls on **inbound ports**.  
Outbound adapters implement **outbound ports** defined in the domain.

The core never depends on adapters.  
Adapters depend on the core.  
The runtime composes everything.

---

## What this template gives you

### Core stack
- **Java 21 (LTS)**
- **Spring Boot 3.5**
- **Maven multi-module project**

---

### Runtime features
- REST API with validation
- Consistent error responses using **Problem Details**
- Database migrations with **Flyway**
- Actuator endpoints:
  - health / liveness / readiness
  - metrics
- Structured logging
  - readable logs locally
  - JSON logs via profile
- Trace/log correlation ready (OpenTelemetry + W3C)

### Testing
- **Unit tests** (fast, no DB required)
- **Integration tests** with Testcontainers (real PostgreSQL)

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
- Docker (for integration tests and local database)

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

---

Database Migrations

This template uses **Flyway** for database schema management.

Migrations live in:
```
runtime/src/main/resources/db/migration/
```

Naming convention:
```
V1__create_greetings_table.sql
V2__add_created_at_column.sql
V3__add_index_on_message.sql
```

On startup, Flyway automatically:
1. Checks which migrations have already run
2. Applies any new migrations in order
3. Fails fast if something is wrong

To add a schema change, create a new versioned SQL file. Never edit existing migrations that have been applied.

---

Integration Tests

This template includes integration tests using **Testcontainers**.

Run unit tests only:
```bash
./mvnw test
```

Run all tests including integration tests:
```bash
./mvnw verify
```

Integration tests:
- Spin up a real PostgreSQL container
- Run Flyway migrations
- Test the full request flow
- Are skipped automatically if Docker is unavailable

Integration test files end with `*IT.java` and live in:
```
runtime/src/test/java/
```

---

How to extend this

Typical next steps for a real service:

1. Add your domain model in domain
2. Add persistence or external clients in adapters
3. Expose endpoints in adapters/in/
4. Add database migrations in runtime/src/main/resources/db/migration/
5. Add more integration tests as needed
6. Add outbound HTTP client config (timeouts, retries, etc.)

Philosophy

This template prefers:
- explicit over clever
- boring over fragile
- small pieces over big frameworks
- things you can reason about at 2am
