# authn

Authn service: Spring Boot (Gradle) microservice for authentication and user/token management.

Default configuration (see `src/main/resources/application.yml`):

- Application name: `authn`
- Data source example: `jdbc:postgresql://localhost:5433/linkshrink` (username: `backend`, password: `pass_876`)
- Server port: configurable in `application.yml` (often 8080 by default)

Run locally for development:

```bash
cd authn
./gradlew bootRun
```

Build & run:

```bash
./gradlew clean bootJar
java -jar build/libs/*-SNAPSHOT.jar

docker build -t linkshrink/authn:local .
```

Notes

- The service includes Actuator endpoints and Prometheus metrics configuration. Adjust DB settings to point to a running Postgres instance when running locally.
