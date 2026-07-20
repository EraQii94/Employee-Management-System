# EMS (Employee Management System) ✅ v2.0.0

A **production-ready** Spring Boot REST API for managing employees, departments, projects, and project assignments. Built with Spring MVC, Spring Data JPA, Hibernate, and PostgreSQL with comprehensive error handling and validation.

**Status:** Production Ready ✅ | **Last Updated:** July 18, 2026

## 📋 Quick Links for Developers

> **👋 New to this project?** Start here:
> 1. Read [`QUICK_START.md`](QUICK_START.md) (5 minutes to running)
> 2. Read [`HANDOVER.md`](HANDOVER.md) (complete system guide)
> 3. Read [`CHANGELOG.md`](CHANGELOG.md) (what changed and why)
> 4. Check [`TROUBLESHOOTING.md`](TROUBLESHOOTING.md) (common issues)

## Table of Contents

- [Features](#features)
- [Recent Fixes (v2.0)](#recent-fixes-v20)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Running with Docker](#running-with-docker)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Exception Handling](#exception-handling)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Troubleshooting](#troubleshooting)
- [Documentation Files](#documentation-files)
- [Contributing](#contributing)

## Features

✅ **CRUD Operations**
- Manage employees, departments, projects, and assignments

✅ **Validation**
- Email uniqueness and format validation
- Phone number format validation (10-13 digits)
- Required field validation with meaningful error messages

✅ **Cascading Deletes**
- Deleting a project cascades delete to project assignments
- Deleting an employee cascades delete to project assignments
- Department deletion prevents deletion if employees are assigned

✅ **Partial Updates**
- Update only the fields you need (no need to send all fields)

✅ **Error Handling**
- Centralized exception handling with structured JSON responses
- Proper HTTP status codes (400, 404, 409, 500)

✅ **Documentation**
- Swagger/OpenAPI UI for API exploration
- Complete developer guides
- Troubleshooting documentation

## Recent Fixes (v2.0)

**🐛 Critical Fixes:**
- ✅ Project deletion FK constraint violation (cascading deletes)
- ✅ Employee deletion FK constraint violation (cascading deletes)
- ✅ Department deletion returns 500 instead of 409 Conflict
- ✅ Employee update returns 500 instead of 400 Bad Request

**✨ Improvements:**
- ✅ Global exception handler with structured JSON responses
- ✅ Support for partial employee updates
- ✅ Transactional cascading deletes
- ✅ Improved validation error messages

**Details:** See [`CHANGELOG.md`](CHANGELOG.md)

## Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Language |
| **Spring Boot** | 3.x | Framework |
| **Spring Data JPA** | Included | ORM |
| **Hibernate** | Latest | JPA Implementation |
| **PostgreSQL** | 12+ | Database |
| **Jakarta Validation** | Latest | Bean validation |
| **Lombok** | Latest | Boilerplate reduction |
| **Maven** | 3.8+ | Build tool |
| **SpringDoc OpenAPI** | Latest | Swagger UI |

## Prerequisites

- **Java:** JDK 17 or higher
- **Database:** PostgreSQL 12+ (or use Docker)
- **Build Tool:** Maven (included as wrapper)
- **Optional:** Git, Docker & Docker Compose

## Quick Start

### ⚡ Get Running in 5 Minutes

```powershell
# Step 1: Navigate to project
cd D:\EMS

# Step 2: Start database (Docker recommended)
docker-compose up -d

# Step 3: Run application
.\mvnw.cmd spring-boot:run
```

✅ Application starts on **http://localhost:8080**

**For detailed setup:** See [`QUICK_START.md`](QUICK_START.md)

## Running with Docker

```bash
docker-compose up -d
```

This starts both the application and PostgreSQL in containers.

## API Documentation

Once running, access API docs at:

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## Example Requests

**Create Employee**
```bash
curl -X POST "http://localhost:8080/api/v1/employee" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phoneNumber": "+12345678901",
    "hireDate": "2024-01-15",
    "salary": 50000
  }'
```

**Update Employee (Partial)**
```bash
curl -X PUT "http://localhost:8080/api/v1/employee/1" \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","salary":60000}'
```

**Get All Employees**
```bash
curl http://localhost:8080/api/v1/employee
```

For more examples: See [`HANDOVER.md`](HANDOVER.md) → API Endpoints section

## Configuration

**File:** `src/main/resources/application.properties`

| Property | Purpose |
|----------|---------|
| `spring.datasource.url` | Database connection URL |
| `spring.datasource.username` | Database user |
| `spring.datasource.password` | Database password |
| `spring.jpa.hibernate.ddl-auto` | Schema generation: `create`, `update`, `validate` |
| `server.port` | Application port (default: 8080) |

## Exception Handling

The application uses a centralized exception handler that returns proper HTTP status codes with JSON responses:

| Scenario | Status | Example |
|----------|--------|---------|
| Validation error | 400 | Invalid email or phone format |
| Resource not found | 404 | Employee with ID 999 doesn't exist |
| Email already exists | 409 | Email already in use |
| FK constraint violation | 409 | Deleting project with assignments |
| Business rule violation | 409 | Deleting department with employees |

**Response Format:**
```json
{
  "timestamp": "2026-07-18T08:22:17.824Z",
  "status": 409,
  "error": "Cannot delete department with assigned employees"
}
```

## Project Structure

```
D:\EMS/
├── src/main/java/com/example/ems/
│   ├── controller/          # REST endpoints
│   ├── service/             # Business logic
│   ├── repository/          # Data access (JPA)
│   ├── entity/              # Database entities
│   ├── dto/                 # Request/Response DTOs
│   ├── mapper/              # Entity-DTO mappers
│   ├── exception/           # Custom exceptions & handlers
│   └── config/              # Configuration
├── src/main/resources/
│   ├── application.properties
│   └── data.sql
├── HANDOVER.md              # Complete developer guide
├── CHANGELOG.md             # What changed and why
├── TROUBLESHOOTING.md       # Common issues and solutions
├── QUICK_START.md           # 5-minute setup guide
├── pom.xml                  # Maven dependencies
└── Docker-compose.yml       # Docker setup
```

## Testing

```bash
# Run all tests
.\mvnw.cmd test          # Windows
./mvnw test              # Linux/macOS

# Run specific test class
.\mvnw.cmd test -Dtest=EmployeeServiceTest
```

## Troubleshooting

**Common Issues:**

1. **"Connection refused"** → Database not running → Use `docker-compose up -d`
2. **"Tables do not exist"** → Set `spring.jpa.hibernate.ddl-auto=create` in properties
3. **"Port 8080 in use"** → Change `server.port` in properties
4. **"Invalid email" (400)** → Email must be in format: `user@domain.com`
5. **"Phone invalid" (400)** → Phone must be 10-13 digits, optionally starting with +

**Full guide:** See [`TROUBLESHOOTING.md`](TROUBLESHOOTING.md)

## Documentation Files

| File | Purpose | Read Time |
|------|---------|-----------|
| [`QUICK_START.md`](QUICK_START.md) | Get running in 5 minutes | 5 min |
| [`HANDOVER.md`](HANDOVER.md) | Complete system guide | 20 min |
| [`CHANGELOG.md`](CHANGELOG.md) | What changed and why | 15 min |
| [`TROUBLESHOOTING.md`](TROUBLESHOOTING.md) | Common issues & solutions | As needed |

## Contributing

1. Read the code structure in [`HANDOVER.md`](HANDOVER.md)
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make changes and test locally: `.\mvnw.cmd test`
4. Commit with clear messages: `git commit -m "Add feature description"`
5. Push and create a pull request

## Support

- 📖 Read [`HANDOVER.md`](HANDOVER.md) for complete documentation
- 🔧 Check [`TROUBLESHOOTING.md`](TROUBLESHOOTING.md) for common issues
- 📝 See [`CHANGELOG.md`](CHANGELOG.md) for recent changes
- 🚀 Get started with [`QUICK_START.md`](QUICK_START.md)

## Version Info

- **Current Version:** 2.0.0 ✅
- **Released:** July 18, 2026
- **Status:** Production Ready
- **Java:** 17+ required
- **Spring Boot:** 3.x

---

**Version:** 2.0.0  
**Status:** Production Ready ✅  
**Last Updated:** July 18, 2026

