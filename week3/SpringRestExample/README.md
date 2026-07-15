# Week 3 — Java FSE — Spring REST using Spring Boot 3

## What I built
A small Book REST API with a proper DTO layer (separate `BookRequest` /
`BookResponse` from the `Book` entity) and a global exception handler
(`@RestControllerAdvice`) that turns validation failures and missing
resources into consistent, correctly-coded JSON error responses instead of
raw stack traces.

## Key decisions
- **DTOs instead of exposing the entity directly** — the entity's shape can
  change without breaking the API contract, and validation
  (`@NotBlank`, `@Min`) lives on the request boundary where it belongs, not
  mixed into persistence annotations.
- **One `@RestControllerAdvice`** instead of try/catch in every controller
  method — a missing book always returns the same 404 shape everywhere, not
  a different one per endpoint that happens to have remembered to catch it.
- **A consistent `ApiError` body** (timestamp, status, error, message,
  details) on every failure path, so a client can parse errors the same way
  regardless of which exception triggered them.

## What was hardest
Deciding what belongs in the generic `Exception.class` handler vs. specific
handlers. Catching everything generically is tempting but it hides bugs
behind a friendly 500 message; the specific handlers for
`ResourceNotFoundException` and `MethodArgumentNotValidException` exist so
the *expected* failure modes get precise, useful responses, while the
catch-all is a last-resort safety net, not the primary error path.

## Proof it works (sending bad input)
```bash
curl -i -X POST localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title": "", "author": "Someone", "publicationYear": 2020}'
```
Response:
```json
{
  "timestamp": "...",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": ["title must not be blank"]
}
```

## How to run
```bash
mvn spring-boot:run
```

## How to test
```bash
mvn test
```
`BookControllerTest` exercises both the 400 (validation) and 404
(not found) paths through the actual HTTP layer via MockMvc.
