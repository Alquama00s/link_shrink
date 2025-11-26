# shortner

Shortner service: Spring Boot (Gradle) microservice responsible for creating and storing short URLs.

Default configuration (see `src/main/resources/application.yml`):

- Default service name: `shortner`
- Datasource: `jdbc:postgresql://localhost:5432/linkshrink` (username: `backend`, password: `pass_876`)
- Default port: 8080

Run locally for development:

```bash
cd shortner
./gradlew bootRun
```

Build a JAR to run or package into Docker:

```bash
./gradlew clean bootJar
java -jar build/libs/*-SNAPSHOT.jar

# build docker image
docker build -t linkshrink/shortner:local .
```

API notes

- The service exposes endpoints under `/api/urls` for create/lookup. Redirector expects to call shortner's `/api/urls` endpoints.

---

If you need help setting up the Postgres instance, check the `Docker/database` scripts or the `k8s/database` manifests.
