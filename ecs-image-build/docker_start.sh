#!/bin/bash
#
# Start script for presenter-account-api
PORT=3000

exec java -jar -Dserver.port="${PORT}" -XX:MaxRAMPercentage=80 "presenter-account-api.jar"