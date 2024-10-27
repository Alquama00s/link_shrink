FROM nginx

COPY linkshrink-frontend/dist/linkshrink-frontend/browser /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY Docker/nginx/default.conf /etc/nginx/conf.d/default.conf
