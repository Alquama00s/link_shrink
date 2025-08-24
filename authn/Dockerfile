FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/authn.jar .

ENV SPRING_PROFILES_ACTIVE=DBh2
CMD ["echo","$SPRING_DATASOURCE_URL"]
ENTRYPOINT ["java", "-jar", "/app/authn.jar"]
