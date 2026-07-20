# EMS (Employee Management System)

A Spring Boot–based Employee Management System exposing REST APIs to manage departments, employees, projects, and project assignments. Built with Spring MVC, Spring Data JPA, and PostgreSQL, with an OpenAPI/Swagger UI for API exploration.

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Running with Docker](#running-with-docker)
- [API Documentation](#api-documentation)
- [Example Requests](#example-requests)
- [Tests](#tests)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)
- [Maintainers](#maintainers)

## Project Overview

The application exposes four controllers:

| Controller | Responsibility |
|---|---|
| `DepartmentController` | Manage departments |
| `EmployeeController` | Manage employees |
| `ProjectController` | Manage projects |
| `ProjectAssignedController` | Assign employees to projects |

The codebase follows a layered architecture (controller → service → repository) with request/response DTOs, and persists data to PostgreSQL.

## Tech Stack

- Java 21
- Spring Boot 4.x
- Spring MVC, Spring Data JPA
- PostgreSQL
- springdoc-openapi (Swagger UI)
- Lombok
- Maven (wrapper included)

## Prerequisites

- JDK 21 with `JAVA_HOME` set
- PostgreSQL 14+ running locally, or Docker & Docker Compose
- Git (optional)

No local Maven installation is required — use the included wrapper.

## Quick Start

**Linux / macOS**

```bash
./mvnw clean package -DskipTests
java -jar target/EMS-0.0.1-SNAPSHOT.jar
```

**Windows (PowerShell)**

```powershell
.\mvnw.cmd clean package -DskipTests
java -jar target\EMS-0.0.1-SNAPSHOT.jar
```

The app starts on port `8080` by default. See `src/main/resources/application.properties` to change it.

## Running with Docker

```bash
docker compose up --build
```

This starts the application alongside a PostgreSQL container as defined in `docker-compose.yml`.

## API Documentation

Once the app is running:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

If these paths differ, check `OpenApiConfig` under `src/main/java/com/example/ems/config`.

> Exact endpoint paths may vary depending on controller mappings — Swagger UI is the source of truth.

## Example Requests

**Create a department**

```http
POST /api/departments
Content-Type: application/json

{
  "name": "Engineering",
  "location": "HQ",
  "budget": 500000
}
```

**Create an employee**

```http
POST /api/employees
Content-Type: application/json

{
  "name": "Alice Smith",
  "email": "alice.smith@example.com",
  "phoneNumber": "+1-555-1000",
  "hireDate": "2024-05-01",
  "salary": 85000,
  "departmentId": 1
}
```

**Create a project**

```http
POST /api/projects
Content-Type: application/json

{
  "name": "Project Phoenix",
  "description": "Replatforming initiative",
  "startDate": "2024-06-01",
  "endDate": "2024-12-31"
}
```

**Assign an employee to a project**

```http
POST /api/project-assigned
Content-Type: application/json

{
  "projectId": 1,
  "employeeId": 1,
  "role": "DEVELOPER"
}
```

## Tests

```bash
./mvnw test          # Linux / macOS
.\mvnw.cmd test      # Windows
```

## Configuration

Key properties in `src/main/resources/application.properties`:

| Property | Default | Description |
|---|---|---|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/employee_db` | JDBC connection URL |
| `spring.datasource.username` | — | Database user |
| `spring.datasource.password` | — | Database password |
| `spring.jpa.hibernate.ddl-auto` | `update` | Schema generation strategy |
| `spring.sql.init.mode` | `always` | Runs `data.sql` on startup |

All properties can be overridden with environment variables (e.g. `SPRING_DATASOURCE_URL`).

**Seed data:** `src/main/resources/data.sql` pre-populates departments, employees, projects, and assignments on startup while `spring.sql.init.mode=always`.

## Troubleshooting

**Database connection fails** — Confirm PostgreSQL is running and that the URL and credentials in `application.properties` are correct.

**Lombok annotations won't compile** — Install the Lombok plugin in your IDE and enable annotation processing.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests covering your changes
4. Open a pull request with a clear description

## License

No license file is currently included. Add a `LICENSE` before publishing or sharing.

## Maintainers

Maintained by the repository owner. For questions, open an issue.
