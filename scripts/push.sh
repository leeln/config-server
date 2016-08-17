#!/bin/bash
set -e

cd $(dirname $0)

cd ../

TAG=${TAG:-$(awk '/ENV VERSION/{print $3}' Dockerfile)}
NAME=${NAME:-$(awk '/ENV SERVER_NAME/{print $3}' Dockerfile)}

IMAGE=docker.repo.waimai.sankuai.info/micoa/${NAME}:${TAG}

docker build -t ${IMAGE} .

docker push ${IMAGE}

echo Done pushing ${IMAGE}
