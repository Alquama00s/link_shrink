# LinkShrink — URL shortening microservices

<!-- ![LinkShrink](linkshrink-logo.png) -->

LinkShrink is an open-source, modular URL shortening platform. The repository contains multiple Java Spring Boot microservices (shortner, redirector, authn, gateway), an Angular web client and orchestration assets (Docker + Kubernetes manifests) so you can run LinkShrink locally or in a cluster.

## Contents

- Overview
- Components (services and roles)
- Quick start — run locally with Docker
- Development — run services locally (Gradle / Maven / Angular)
- Containers & building images
- Kubernetes — deploy to a cluster
- Testing and troubleshooting
- Contributing and links

For details on the project's roadmap and contribution guidelines see the project's wiki: https://github.com/Alquama00s/link_shrink/wiki

---

## Architecture / components

High level components in this workspace:

- linkshrink-frontend — Angular web UI (dev server: 4200, built site served by NGINX in Docker)
- shortner — Spring Boot (Gradle) service that creates short URLs and stores them in Postgres
- redirector — Spring Boot (Gradle) service that looks up a short URL and performs the redirect
- authn — Spring Boot (Gradle) auth service (user management / token endpoints)
- gateway — Spring Boot (Gradle) simple API gateway / edge
- backend — (Maven) a supporting backend project in /backend
- Docker/ & k8s/ — pre-configured Dockerfiles and Kubernetes manifests

Default service ports seen in the repo (local defaults):

- shortner (API) — 8080
- authn — 8080 (configurable)
- redirector — 8081
- frontend dev server — 4200 (production UI served on port 80 inside the container image)

Many of the repo's container images expose 8080–8082 and an example single-container image maps ports like:

docker run -p 80:80 -p 8080-8082:8080-8082 alquama00s/linkshrink

---

## Quick start — run with Docker (single container)

This repository includes Docker artifacts to produce an integrated image. To run the prebuilt container (if available on Docker Hub):

```bash
# run the prebuilt 'linkshrink' container and map the default ports
docker run -p 80:80 -p 8080-8082:8080-8082 alquama00s/linkshrink

# Frontend UI (NGINX) -> http://localhost
# APIs:
#  - shortner /api (default) -> http://localhost:8080
#  - redirector (redirect service) -> http://localhost:8081
#  - authn -> http://localhost:8082 (where provided)
```

Visit http://localhost to open the web UI.

API example using curl (create a shortened URL via the shortner service):

```bash
curl -X POST http://localhost:8080/api/urls/create \
  -H "Content-Type: application/json" \
  -d '{"longUrl":"https://example.com/very/long/url"}'
```

NOTE: Port assignments vary between Docker/Kubernetes configs — check the specific Dockerfile or k8s manifest for the exact mapping.

---

## Development — run services locally

Prerequisites

- Java 17+ (Gradle projects use the provided Gradle wrapper)
- Maven 3.x (for projects under `backend` which use Maven)
- Node.js + npm/yarn (for `linkshrink-frontend` development)
- Docker / kubectl if you're planning container or cluster testing

Recommended local runs:

- Frontend (development server):
  - cd linkshrink-frontend
  - npm ci
  - npm start (or `ng serve`) — opens at http://localhost:4200

- Shortner / Redirector / Authn / Gateway (Spring Boot, Gradle)
  - ./gradlew :shortner:bootRun (or cd shortner && ./gradlew bootRun)
  - ./gradlew :redirector:bootRun
  - ./gradlew :authn:bootRun
  - Use `./gradlew bootJar` to build a JAR and run via `java -jar` if preferred

- Backend (Maven project in /backend):
  - cd backend
  - ./mvnw spring-boot:run

These services read their configuration from application.yml — adjust database connection strings and ports while developing.

---

## Build & Docker images

A set of helper scripts live in `build-scripts/`.

To build service artifacts and Docker images (one possible flow):

```bash
# build JARs (gradle or maven wrappers run the builds per-service)
./gradlew build         # top-level will build Gradle modules (shortner/redirector/authn/gateway)
cd backend && ./mvnw package

# build Docker images (example for one service)
cd shortner && docker build -t linkshrink/shortner:local .
```

There is also a `Docker/oneContainer/` setup that builds an integrated image exposing ports 80, 8080, 8081 and 8082 which is convenient for a single-host demo.

---

## Kubernetes

Manifest files to run LinkShrink on Kubernetes are under `k8s/`. Basic steps to deploy on a cluster (e.g., Minikube or a cloud provider):

```bash
# Ensure you have images available (push to a registry or build images inside the cluster)
kubectl apply -f k8s/backend
kubectl apply -f k8s/frontend
# Add DB and secrets from k8s/database if required
```

The `k8s` folder contains per-service deployment YAMLs including port and environment configuration. Review the files to adapt to your environment.

---

## Testing & monitoring

- Unit tests: run Gradle `./gradlew test` in a microservice or `./mvnw test` for the `backend` module
- Prometheus metrics / Actuator are included in the Spring Boot services — endpoints are exposed when Actuator is configured (check `application.yml`)

---

## Contributing

Thanks for checking out LinkShrink! If you'd like to help:

1. Look at issues or open a new issue in the project GitHub
2. Check the wiki and roadmap pages for desired work: https://github.com/Alquama00s/link_shrink/wiki
3. Open a pull request — follow the project's code style and include unit tests where appropriate

---

## License

This project is licensed under the license file in the repository. See `LICENSE` for details.

---

If you'd like I can also improve or add per-service READMEs (frontend, shortner, redirector, authn, backend, and k8s) so each folder contains concise run/build instructions — tell me which folders you'd like prioritized.