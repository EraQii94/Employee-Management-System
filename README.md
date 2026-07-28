# EMS — Employee Management System

A Spring Boot REST API for managing departments, employees, projects, and project assignments. Built with Spring MVC, Spring Data JPA and PostgreSQL, with OpenAPI/Swagger UI for exploring the API.

## Contents

- [Overview](#overview)
- [Tech stack](#tech-stack)
- [Data model](#data-model)
- [Prerequisites](#prerequisites)
- [Running the application](#running-the-application)
- [API reference](#api-reference)
- [Example requests](#example-requests)
- [Configuration](#configuration)
- [Validation rules](#validation-rules)
- [Error responses](#error-responses)
- [Tests](#tests)
- [Known limitations](#known-limitations)
- [Troubleshooting](#troubleshooting)

## Overview

The application follows a layered architecture — controller → service → repository — with request and response DTOs at the boundary so the JPA entities are never serialized directly.

| Controller | Base path | Responsibility |
|---|---|---|
| `DepartmentController` | `/api/v1/departments` | Manage departments |
| `EmployeeController` | `/api/v1/employee` | Manage employees |
| `ProjectController` | `/api/v1/project` | Manage projects |
| `ProjectAssignedController` | `/api/v1/projectassignment` | Assign employees to projects |

## Tech stack

- Java 21
- Spring Boot 4.1.0
- Spring MVC, Spring Data JPA (Hibernate)
- PostgreSQL 17
- springdoc-openapi (Swagger UI)
- Lombok
- Maven (wrapper included — no local Maven install needed)

## Data model

Four entities. The ERD is in the repository root.

**Department** — `id`, `name`, `location`, `budget`. Has many employees and many projects.

**Employee** — `id`, `name`, `email` (unique), `phoneNumber`, `hireDate`, `salary`. Belongs to at most one department.

**Project** — `id`, `name`, `description`, `startDate`, `endDate`. Belongs to at most one department.

**ProjectAssigned** — `id`, `role`. Join entity between `Employee` and `Project`.

The employee-to-project relationship is deliberately modelled as a **join entity rather than a plain `@ManyToMany`**. The requirement is to track the role each employee plays on each project, and that role is an attribute of the *relationship*, not of either side. Promoting the join table to a first-class entity gives that attribute a home and leaves room for others later — allocation percentage, assignment start and end dates.

`role` is one of `MANAGER`, `DEVELOPER`, `ANALYST`, persisted as a string via `@Enumerated(EnumType.STRING)` so that reordering the enum cannot corrupt existing rows.

## Prerequisites

- JDK 21, with `JAVA_HOME` set
- Docker and Docker Compose (recommended), or a local PostgreSQL 14+ instance

## Running the application

### 1. Start PostgreSQL

The Compose file provisions **the database only** — the application itself is run separately.

```bash
docker compose up -d
```

This starts PostgreSQL 17 on port `5432` with database `employee_db`, user `postgres`, password `postgres`.

If you prefer a local PostgreSQL install, create a database named `employee_db` and make sure the credentials match [Configuration](#configuration).

### 2. Start the application

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

The API is then available on `http://localhost:8080`.

### 3. Explore

- **Swagger UI** — http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON** — http://localhost:8080/v3/api-docs

The schema is recreated on every startup and seeded from `src/main/resources/data.sql` with two departments, three employees, two projects and three assignments. See [Configuration](#configuration) for why.

## API reference

### Departments — `/api/v1/departments`

| Method | Path | Description | Success |
|---|---|---|---|
| `POST` | `/` | Create a department | `201` |
| `GET` | `/` | List all departments | `200` |
| `GET` | `/{id}` | Get one department | `200` |
| `PUT` | `/{id}` | Update a department | `200` |
| `DELETE` | `/{id}` | Delete a department | `204` |

Deleting a department that still has employees assigned returns `409 Conflict`.

### Employees — `/api/v1/employee`

| Method | Path | Description | Success |
|---|---|---|---|
| `POST` | `/` | Create an employee | `201` |
| `GET` | `/{id}` | Get one employee | `200` |
| `GET` | `/?departmentId={id}` | List employees, optionally filtered by department | `200` |
| `PUT` | `/{id}` | Update an employee (partial — only non-null fields are applied) | `200` |
| `PUT` | `/assign/{employeeId}?departmentId={id}` | Assign an employee to a department | `200` |
| `DELETE` | `/?id={id}` | Delete an employee and their project assignments | `204` |

Omitting `departmentId` on the list endpoint returns all employees.

### Projects — `/api/v1/project`

| Method | Path | Description | Success |
|---|---|---|---|
| `POST` | `/` | Create a project | `201` |
| `GET` | `/{departmentId}` | List projects belonging to a department | `200` |
| `PUT` | `/{id}` | Update a project (empty response body) | `200` |
| `DELETE` | `/{id}` | Delete a project and its assignments | `200` |
| `PUT` | `/assign/{projectId}?departmentId={id}` | Assign a project to a department | `200` |

### Project assignments — `/api/v1/projectassignment`

| Method | Path | Description | Success |
|---|---|---|---|
| `POST` | `/assignments` | Assign one employee to a project with a role | `200` |
| `POST` | `/projects/{projectId}/assignments` | Assign several employees to one project | `200` |
| `GET` | `/projects/{projectId}/employees` | List everyone working on a project | `200` |
| `GET` | `/employees/{employeeId}/projects` | List every project an employee is on | `200` |

## Example requests

Every body below is valid against the current validation rules and can be pasted as-is.

**Create a department**

```http
POST /api/v1/departments
Content-Type: application/json

{
  "name": "Engineering",
  "location": "Cairo",
  "budget": 500000
}
```

**Create an employee**

```http
POST /api/v1/employee
Content-Type: application/json

{
  "name": "Alice Smith",
  "email": "alice.smith@example.com",
  "phoneNumber": "+201234567890",
  "hireDate": "2024-05-01",
  "salary": 85000,
  "departmentId": 1
}
```

> `phoneNumber` must match `^[+]?[0-9]{10,13}$` — an optional leading `+` followed by 10 to 13 digits, **no spaces, dashes or parentheses**.

**Create a project**

```http
POST /api/v1/project
Content-Type: application/json

{
  "name": "Project Phoenix",
  "description": "Replatforming initiative",
  "startDate": "2024-06-01",
  "endDate": "2024-12-31"
}
```

A project is not attached to a department at creation time — use the assign endpoint afterwards:

```http
PUT /api/v1/project/assign/1?departmentId=1
```

**Assign an employee to a project**

```http
POST /api/v1/projectassignment/assignments
Content-Type: application/json

{
  "projectId": 1,
  "employeeId": 1,
  "role": "DEVELOPER"
}
```

`role` must be one of `MANAGER`, `DEVELOPER`, `ANALYST`.

**Assign several employees at once**

```http
POST /api/v1/projectassignment/projects/1/assignments
Content-Type: application/json

[
  { "employeeId": 1, "role": "MANAGER" },
  { "employeeId": 2, "role": "DEVELOPER" }
]
```

The `projectId` in the path is applied to every entry, so it can be omitted from the bodies.

## Configuration

Set in `src/main/resources/application.properties`:

| Property | Value | Notes |
|---|---|---|
| `server.port` | `8080` | |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/employee_db` | |
| `spring.datasource.username` | `postgres` | |
| `spring.datasource.password` | `postgres` | |
| `spring.jpa.hibernate.ddl-auto` | `create-drop` | Schema is dropped and recreated on every startup |
| `spring.jpa.show-sql` | `true` | Generated SQL is logged |
| `spring.sql.init.mode` | `always` | Runs `data.sql` on startup |
| `spring.jpa.defer-datasource-initialization` | `true` | Ensures the schema exists before `data.sql` runs |

Any property can be overridden with an environment variable, e.g. `SPRING_DATASOURCE_URL`.

**On `create-drop`:** this is a development convenience — it guarantees a clean schema plus fresh seed data on every run. **All data is lost on shutdown.** For any real deployment Hibernate should not own the schema at all: `ddl-auto` would be `validate`, with schema changes managed as versioned migrations through Flyway or Liquibase.

## Validation rules

Currently enforced on `EmployeeRequest` and `EmployeeUpdateRequest` only:

| Field | Rule |
|---|---|
| `name` | Not blank |
| `email` | Not blank, valid email format, unique across employees |
| `phoneNumber` | Matches `^[+]?[0-9]{10,13}$` |
| `hireDate` | Not null, ISO format `yyyy-MM-dd` |
| `salary` | Not null, positive |

Constraint violations return `400` with a field-by-field breakdown.

## Error responses

Handled centrally by `GlobalExceptionHandler`. All errors share a common shape:

```json
{
  "timestamp": "2026-07-28T12:00:00Z",
  "status": 404,
  "error": "Employee not found with ID: 42"
}
```

Validation failures use `errors` instead of `error`, keyed by field name:

```json
{
  "timestamp": "2026-07-28T12:00:00Z",
  "status": 400,
  "errors": {
    "email": "must be a well-formed email address",
    "phoneNumber": "Phone number must be 10-13 digits"
  }
}
```

| Status | When |
|---|---|
| `400` | Bean validation failure, or a required request/ID was null |
| `404` | Referenced department, employee or project does not exist |
| `409` | Duplicate email, deleting a department with employees, or a database constraint violation |

## Tests

```bash
./mvnw test          # Linux / macOS
.\mvnw.cmd test      # Windows
```

Test coverage is currently limited to a Spring context load check (`EmsApplicationTests`). See [Known limitations](#known-limitations).

## Known limitations

Documented deliberately rather than left to be discovered:

- **No frontend.** The Angular single-page application is not implemented; this repository is the backend API only.
- **No automated test suite** beyond the context-load check. Service-layer unit tests with Mockito, `@DataJpaTest` for the derived queries and `@WebMvcTest` for the controllers are the intended next step.
- **`departmentName` is not populated** on employee responses. The department association is lazy and the service layer is not transactional, so resolving it requires an entity graph or a fetch join rather than a direct dereference.
- **No pagination** on list endpoints; they return the full table.
- **No CORS configuration**, so a browser client on a different origin (such as an Angular dev server on port 4200) cannot call this API as configured.
- **No authentication or authorisation.** Salary data is served to any caller.
- **`salary` and `budget` use `double`.** `BigDecimal` mapped to `NUMERIC` would be correct for currency — binary floating point cannot represent decimal fractions exactly.
- **Duplicate project assignments are permitted** — there is no unique constraint on the employee/project pair.

## Troubleshooting

**`no configuration file provided: not found`** — Docker Compose cannot see the Compose file. Confirm the filename is lowercase `docker-compose.yml`, or pass it explicitly with `-f`.

**Database connection refused** — check the container is up with `docker ps`, and that nothing else is already bound to port 5432.

**Data disappeared after a restart** — expected. `ddl-auto=create-drop` recreates the schema every time; the seed data in `data.sql` is reloaded on each startup.

**`400` on a request copied from this README** — check `phoneNumber`. The regex rejects spaces, dashes and parentheses.

**Lombok annotations won't compile** — install the Lombok plugin in your IDE and enable annotation processing.

## Author

Abdulrahman Eraky — abdulrahmanaleraky@gmail.com
