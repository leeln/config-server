#!/bin/bash
set -e

cd $(dirname $0)

cd ../

TAG=${TAG:-$(awk '/ENV VERSION/{print $3}' Dockerfile)}
NAME=${NAME:-$(awk '/ENV SERVER_NAME/{print $3}' Dockerfile)}
PORT=${PORT:-$(awk '/ENV PORT/{print $3}' Dockerfile)}

IMAGE=registry.gitlab.com/micoa/${NAME}:${TAG}

docker stop ${NAME}

docker rm ${NAME}

docker run -d --name ${NAME} -p ${PORT}:${PORT} ${IMAGE} .

echo Done Deploying ${IMAGE}
