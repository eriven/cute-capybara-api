# Capy API Task List

## Current Phase
Phase 3: Engineering Quality (Docs, Caching, Logging)

## In Progress
(none)

## Todo
(none — Phase 3 complete)

## Done

### Phase 3
- [x] Add Swagger/OpenAPI via springdoc-openapi-starter-webmvc-ui 2.5.0
- [x] Add OpenApiConfig bean (title, description, version)
- [x] Add @Operation, @Parameter, @ApiResponse annotations to all three endpoints
- [x] Add spring-boot-starter-cache dependency
- [x] Add CacheConfig (@EnableCaching, ConcurrentMapCache via Spring Boot auto-config)
- [x] Cache getImages (key: page-size-tag) with @Cacheable
- [x] Cache getImageById (key: id) with @Cacheable
- [x] Leave getRandomImage uncached (caching random defeats its purpose)
- [x] Add SLF4J structured logging to ImageService (fetch events, result counts, random id)

### Phase 2
- [x] Add GET /api/images/random
- [x] Add filtering support to GET /api/images via `tag`
- [x] Add tags to Image entity using @ElementCollection(fetch=EAGER)
- [x] Update DataSeeder to populate initial tags
- [x] Update tests for new queries and endpoints
- [x] Fix: route ordering — /random before /{id} to prevent path variable collision
- [x] Fix: @ElementCollection(fetch=EAGER) to avoid LazyInitializationException with open-in-view=false
- [x] Fix: DataSeeder detects stale Phase 1 data (no tags) and reseeds
- [x] Fix: DISTINCT in findByTag query to prevent duplicate results from JOIN

### Phase 1
- [x] Define initial project scope
- [x] Create Spring Boot project skeleton (pom.xml, CapyApiApplication, Maven wrapper)
- [x] Configure MySQL in application.yml (+ H2 test profile)
- [x] Create standard package structure: controller, service, repository, model, dto, config, exception
- [x] Create Image entity
- [x] Create ImageRepository
- [x] Create ImageService (paginated listing, single lookup, DTO mapping)
- [x] Create ImageController (thin, validated)
- [x] Add GET /api/images (paginated, sorted by newest)
- [x] Add GET /api/images/{id}
- [x] Add DTOs (ImageDto record, ImagePageResponse record)
- [x] Add global exception handling (404, 400, type mismatch)
- [x] Add seed data (15 capybara images via DataSeeder)
- [x] Add tests (16 tests passing: 9 controller + 7 service)

## Decisions
- Use Java 17 (source/target) on JDK 21 runtime
- Use Spring Boot 3.2.5
- Use Maven (via wrapper)
- Use MySQL (H2 for tests)
- Package name: com.rizzler.capyapi
- Java records for DTOs
- No Lombok (keep it simple)
- Caching: ConcurrentMapCache (in-memory, auto-configured by Spring Boot)
- getRandomImage intentionally not cached

## Next Up (Phase 4 candidates)
- Add CORS configuration for frontend consumption
- Add POST /api/images endpoint
- Add frontend (React/Next.js capybara feed)
- Add Docker Compose for MySQL + app portability
