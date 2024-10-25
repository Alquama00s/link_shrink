#!/bin/bash

echo "starting backend server...."

java -jar /app/authn.jar > /app/authn.log 2>&1  &
sleep 10
java -jar /app/shortner.jar > /app/shortner.log 2>&1 &
java -jar /app/redirector.jar > /app/redirector.log 2>&1 &

tail -f /app/*.log