# Week 2 — Java FSE — Spring Core, Spring Data JPA, Hibernate

## What I built
A small Library API (`Author` 1—N `Book`) using Spring Data JPA and an
in-memory H2 database, with 2 Spring beans wired through constructor
injection, and a repository query that demonstrates fixing the classic
Hibernate N+1 problem.

## Key decisions
- **Constructor injection over field injection** for `AuthorService`/`AppConfig`
  (see comments in `AppConfig.java`) — makes the dependency explicit, final,
  and trivial to unit test with a plain Mockito mock instead of a Spring
  context.
- **`FetchType.LAZY` on `Book.author`** (JPA's default for `@ManyToOne` is
  actually EAGER — this is set explicitly and lazily on purpose) combined
  with an explicit `JOIN FETCH` query (`BookRepository.findAllWithAuthor`)
  for the one endpoint that actually needs the author eagerly. This avoids
  paying a join on every book fetch while still avoiding N+1 queries when
  the author is genuinely needed.
- **H2 in-memory DB** instead of requiring a real Postgres/MySQL instance,
  so the project runs with zero external setup for review purposes.

## What was hardest
Deciding *where* to solve the N+1 problem — globally switching to `EAGER`
fetch is a one-line "fix" but adds cost to every unrelated query. Adding a
JOIN FETCH query only where it's needed keeps the default cheap and puts
the cost only on the endpoint that actually needs it.

## How to run
```bash
mvn spring-boot:run
```
Then:
```bash
curl -X POST localhost:8080/api/authors -H "Content-Type: application/json" -d '{"name":"Chinua Achebe"}'
curl localhost:8080/api/authors
```

## How to test
```bash
mvn test
```
