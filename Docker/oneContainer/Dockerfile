FROM nginx:1.27.2-alpine-slim

# set up openjdk 17

RUN apk update && apk add openjdk17-jdk


# frontend

COPY linkshrink-frontend/dist/linkshrink-frontend/browser /usr/share/nginx/html
COPY Docker/nginx/default.conf /etc/nginx/conf.d/default.conf

# backend

WORKDIR /app

COPY shortner/build/libs/shortner.jar authn/build/libs/authn.jar redirector/build/libs/redirector.jar Docker/oneContainer/start.sh /app/

ENV SPRING_PROFILES_ACTIVE=DBsqlite
ENV FRONTEND_URI=http://localhost
ENV LOGGING_LEVEL_ROOT=ERROR

# ports
EXPOSE 80 8080 8081 8082

# entrypoint

COPY Docker/oneContainer/start.sh .

ENTRYPOINT [ "/bin/sh","start.sh" ]


