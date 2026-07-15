# SonarQube — Before/After (Week 2/3 code)

Ran `mvn sonar:sonar` against a local SonarQube server pointed at the
Week 2 (`SpringCoreJPAExample`) and Week 3 (`SpringRestExample`) modules.
Three real issues it flagged, and the fix for each:

## 1. Field injection flagged as a code smell (Week 2, before AppConfig existed)
- **Before:** `AuthorService` originally had
  `@Autowired private AuthorRepository authorRepository;` — Sonar rule
  `java:S6813` ("Field injection should be avoided"), because it can't be
  tested without a Spring context and hides a null-dependency risk.
- **After:** switched to constructor injection (see `AppConfig.java`,
  `AuthorService` constructor). Issue closed; `AuthorServiceTest` now
  constructs the class directly with a mock, no Spring context needed.

## 2. Generic exception swallowed silently
- **Before:** an early version of `BookController.findById` had a
  `try { ... } catch (Exception e) { return null; }` around the lookup —
  Sonar rule `java:S1166` ("Exception handlers should preserve the
  original exception") plus a null-return that pushes a NullPointerException
  onto the caller instead of a meaningful error.
- **After:** removed the try/catch; the repository's `orElseThrow` now
  raises `ResourceNotFoundException`, caught once by
  `GlobalExceptionHandler` and turned into a proper 404. Issue closed.

## 3. Duplicated validation logic across controller methods
- **Before:** `BookController.create` and an early `update` method both had
  manual `if (title == null || title.isBlank()) throw ...` checks inline —
  Sonar flagged this as duplicated logic (`java:S1871`,
  "duplicated code blocks").
- **After:** replaced with `@Valid @RequestBody BookRequest` and
  `@NotBlank`/`@Min` annotations on the DTO fields, validated once by
  `GlobalExceptionHandler.handleValidation`. Issue closed; no other
  controller method needs its own manual check.

## How to reproduce
```bash
# requires a local SonarQube server: docker run -d -p 9000:9000 sonarqube:community
cd week2/SpringCoreJPAExample
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<token>

cd ../../week3/SpringRestExample
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<token>
```
