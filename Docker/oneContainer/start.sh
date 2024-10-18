#!/bin/bash

java -jar /app/authn.jar > /app/authn.log 2>&1  &
java -jar /app/shortner.jar > /app/shortner.log 2>&1 &

tail -f /app/*.log