# AI Code Review Assistant

AI Code Review Assistant is a Spring Boot backend service that accepts source code, sends it to OpenAI for automated review, stores the result in PostgreSQL, and exposes REST API endpoints for retrieving, filtering, and deleting review history.

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Hibernate
- OpenAI API
- Maven
- JUnit / Mockito

## Features

- Generate AI-powered code reviews
- Store review history in PostgreSQL
- Retrieve all reviews
- Retrieve review by ID
- Filter reviews by programming language
- Delete review by ID
- Delete all reviews
- Request validation
- Global exception handling
- Unit tests for service layer

## Architecture

```text
Client / Postman
    ↓
Controller
    ↓
Service
    ↓
OpenAI Service
    ↓
OpenAI API

Service
    ↓
Repository
    ↓
PostgreSQL
```

## Main Layers
```
controller — handles HTTP requests and responses
service — contains business logic
repository — handles database access through JPA
entity — represents database tables
dto — request and response objects
exception — custom exceptions and global error handling
```
## Environment Variables

This project requires an OpenAI API key.
```
export OPENAI_API_KEY="your_api_key_here"
```
The key is loaded in application.properties:
```
openai.api.key=${OPENAI_API_KEY}
```

## Database Setup

Create a PostgreSQL database:
```
CREATE DATABASE ai_reviews;
```
Example application.properties:
```
server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/ai_reviews
spring.datasource.username=username
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

openai.api.key=${OPENAI_API_KEY}
```

Run Locally
```./mvnw spring-boot:run```

The API will be available at:
```
http://localhost:8081
```

## API Endpoints
# Code Review API

## Create code review

```http
POST /api/v1/reviews
```

Request body:

```json
{
  "language": "python",
  "code": "def divide(a,b): return a/b",
  "prompt": "Focus on bugs, edge cases and readability"
}
```

## Get all reviews

```http
GET /api/v1/reviews
```

## Get review by ID

```http
GET /api/v1/reviews/{id}
```

## Filter reviews by language

```http
GET /api/v1/reviews?language=python
```

## Delete review by ID

```http
DELETE /api/v1/reviews/{id}
```

## Delete all reviews

```http
DELETE /api/v1/reviews
```

## Error Handling

Example error response:

```json
{
  "status": 404,
  "message": "Review with id 10 not found",
  "timestamp": "2026-05-10T15:20:00"
}
```

## Run Tests

Run all tests:

```bash
./mvnw test
```

Run only service tests:

```bash
./mvnw -Dtest=ReviewServiceTest test
```

## Live Deployment

The application is deployed on Render and can be tested using the live API URL below:

```text
https://ai-code-review-assistant-dhz3.onrender.com