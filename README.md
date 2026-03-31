# Capy API

A small but well-engineered Spring Boot backend for discovering curated capybara images.

Capy API serves image metadata through a clean REST interface with pagination, filtering, random retrieval, generated API docs, and simple caching. The goal of the project is not just to return cute capybara images, but to demonstrate solid backend engineering fundamentals in a compact, explainable system.

## Features

- Paginated image listing
- Lookup by image ID
- Random image endpoint
- Tag-based filtering
- MySQL persistence with Spring Data JPA
- OpenAPI / Swagger UI documentation
- In-memory caching for read-heavy endpoints
- Structured service-layer logging
- Seeded demo dataset for local development
- Unit and web-layer tests with JUnit 5 and MockMvc

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- MySQL
- H2 for tests
- Maven
- Springdoc OpenAPI
- JUnit 5
- MockMvc

## API Endpoints

### List images
`GET /api/images?page=0&size=12`

Optional filter:
`GET /api/images?page=0&size=12&tag=chill`

### Get image by ID
`GET /api/images/{id}`

Example:
`GET /api/images/1`

### Get random image
`GET /api/images/random`

## Example Response

```json
{
  "id": 16,
  "title": "Capybara chilling in hot spring",
  "imageUrl": "https://upload.wikimedia.org/...",
  "sourceUrl": "https://en.wikipedia.org/wiki/Capybara",
  "tags": ["chill", "water", "hot-spring"]
}
