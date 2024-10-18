FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/redirector.jar .

ENTRYPOINT ["java", "-jar", "/app/redirector.jar"]
