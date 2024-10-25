FROM amazoncorretto:17

WORKDIR /app

COPY shortner/build/libs/shortner.jar .
COPY authn/build/libs/authn.jar .
COPY redirector/build/libs/redirector.jar .

ENV SPRING_PROFILES_ACTIVE=DBsqlite

RUN touch authn.log
RUN touch shortner.log
RUN touch redirector.log

COPY Docker/oneContainer/start.sh .

RUN sed -i 's/\r//' start.sh

ENTRYPOINT [ "bash","start.sh" ]


