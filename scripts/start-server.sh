#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
docker stop ordersystem-server || true
docker rm ordersystem-server || true
docker pull 891376927503.dkr.ecr.ap-northeast-2.amazonaws.com/ordersystem-server:latest
docker run -d --name ordersystem-server -p 8080:8080 891376927503.dkr.ecr.ap-northeast-2.amazonaws.com/ordersystem-server:latest
echo "--------------- 서버 배포 끝 -----------------"