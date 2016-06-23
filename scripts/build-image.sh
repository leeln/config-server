#!/bin/bash
set -e

cd $(dirname $0)

cd ../

./scripts/build.sh

TAG=${TAG:-$(awk '/ENV VERSION/{print $3}' Dockerfile)}
NAME=${NAME:-$(awk '/ENV SERVER_NAME/{print $3}' Dockerfile)}

IMAGE=registry.gitlab.com/micoa/${NAME}:${TAG}

docker build -t ${IMAGE} .

echo Done building ${IMAGE}
