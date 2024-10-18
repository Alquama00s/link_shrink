FROM amazoncorretto:17

WORKDIR /app

COPY shortner/build/libs/shortner.jar .
COPY authn/build/libs/authn.jar .

ENV SPRING_PROFILES_ACTIVE=DBsqlite

RUN touch authn.log
RUN touch shortner.log

COPY Docker/oneContainer/start.sh .

ENTRYPOINT [ "bash","start.sh" ]


