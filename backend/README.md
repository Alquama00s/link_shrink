# backend

This folder contains a Java backend project (Maven). Use this README to run and test the backend component.

Quick start

```bash
cd backend
./mvnw spring-boot:run
```

Build & package

```bash
./mvnw clean package
java -jar target/*.jar
```

Testing

```bash
./mvnw test
```

If you're integrating this module with other services in the repository, review the service-specific `application.yml` configuration files and docker/k8s manifests.
