FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/authn.jar .

ENV SPRING_PROFILES_ACTIVE=DBh2

ENTRYPOINT ["java", "-jar", "/app/authn.jar"]
