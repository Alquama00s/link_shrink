FROM nginx

# frontend

COPY linkshrink-frontend/dist/linkshrink-frontend/browser /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY Docker/nginx/default.conf /etc/nginx/conf.d/default.conf


# install  amazoncorretto:17

COPY Docker/oneContainer/corretto-keyring.gpg /usr/share/keyrings/corretto-keyring.gpg
RUN echo "deb [signed-by=/usr/share/keyrings/corretto-keyring.gpg] https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list
RUN apt-get update && apt-get upgrade -y
RUN apt-get install -y java-17-amazon-corretto-jdk

# backend

WORKDIR /app

COPY shortner/build/libs/shortner.jar .
COPY authn/build/libs/authn.jar .
COPY redirector/build/libs/redirector.jar .

ENV SPRING_PROFILES_ACTIVE=DBsqlite
ENV FRONTEND_URI=http://localhost
ENV LOGGING_LEVEL_ROOT=ERROR

# logs 

RUN touch authn.log
RUN touch shortner.log
RUN touch redirector.log
RUN touch nginx.log

# ports

EXPOSE 80
EXPOSE 8080
EXPOSE 8081
EXPOSE 8082

# entrypoint

COPY Docker/oneContainer/start.sh .

RUN sed -i 's/\r//' start.sh

ENTRYPOINT [ "bash","start.sh" ]


