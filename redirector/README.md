# redirector

Redirector service: Spring Boot (Gradle) microservice that looks up short URLs and performs the actual redirect.

Default configuration (see `src/main/resources/application.yml`):

- Application name: `redirector`
- Default server port: 8081
- Feign clients configured to call `shortner` and `authn` services

Run locally for development:

```bash
cd redirector
./gradlew bootRun
```

Build / run as JAR:

```bash
./gradlew clean bootJar
java -jar build/libs/*-SNAPSHOT.jar

# build docker image
docker build -t linkshrink/redirector:local .
```

Integration notes

- Redirector expects to reach the Shortner API to resolve short tokens and the Authn service for auth checks — check `application.yml` for the configured service URLs.
