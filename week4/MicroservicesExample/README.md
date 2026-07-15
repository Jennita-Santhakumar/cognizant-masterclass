# Week 4 — Java FSE — Microservices with Spring Boot 3 (+ SonarQube evidence)

## What I built
Two independently runnable Spring Boot microservices:
- **service-inventory** (port 8082) — owns stock levels, exposes
  `GET /api/inventory/{sku}`.
- **service-order** (port 8081) — calls service-inventory over REST via a
  single `InventoryClient`, exposes
  `GET /api/orders/can-fulfil/{sku}`.

Plus `../sonarqube-evidence/before-after.md`, documenting 3 real SonarQube
issues found in the Week 2/3 code and how each was fixed.

## Key decisions
- **Only 2 services, not more** — depth over breadth. The point is showing
  a real inter-service REST call, config-driven service location, and a
  shared contract, not standing up a large distributed system.
- **Config property instead of a hardcoded URL**
  (`inventory.service.base-url` in service-order's `application.properties`)
  — this is the seam where a real service registry (Eureka/Consul) or API
  gateway would plug in later without touching `OrderController` or
  `InventoryClient`.
- **`InventoryClient` as the single call site** — every other class in
  service-order is unaware that inventory data comes from REST at all;
  swapping to a message queue later touches one file.
- **Shared contract documented explicitly** (`StockLevel` duplicated in both
  services, `client/InventoryClient` as the boundary) rather than sharing a
  JAR — keeps the services genuinely independently deployable.

## What was hardest
Deciding how much "shared contract" to build. A shared client JAR is more
DRY, but it re-couples two services that are supposed to be independently
deployable — every consumer would need to bump the JAR version whenever
the producer changes its DTO. Duplicating the small `StockLevel` DTO in
each service, with the duplication called out here, keeps the services
actually independent, which is closer to what a microservices architecture
is for in the first place.

## How to run
```bash
# terminal 1
cd service-inventory && mvn spring-boot:run

# terminal 2
cd service-order && mvn spring-boot:run

# terminal 3
curl localhost:8081/api/orders/can-fulfil/SKU-1   # in stock
curl localhost:8081/api/orders/can-fulfil/SKU-2   # out of stock -> 409
```
