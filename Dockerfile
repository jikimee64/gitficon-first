# 첫 번째 스테이지: JRE 생성
FROM amazoncorretto:17-alpine3.18 as builder-jre

RUN apk add --no-cache --repository=http://dl-cdn.alpinelinux.org/alpine/edge/main/ binutils=2.42-r0

RUN $JAVA_HOME/bin/jlink \
    --module-path "$JAVA_HOME/jmods" \
    --verbose \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre

# 두 번째 스테이지: 애플리케이션 빌드
FROM gradle:8.7-jdk17 as build

# 컨테이너내 작업 디렉토리 설정
WORKDIR /app

COPY . .

WORKDIR /app/core/core-api

RUN gradle clean build

# 세 번째 스테이지: 최종 이미지 생성
FROM alpine:3.18.4

ENV JAVA_HOME=/jre
ENV PATH="$JAVA_HOME/bin:$PATH"

COPY --from=builder-jre /jre $JAVA_HOME

COPY --from=build /app/core/core-api/build/libs/*.jar app.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "./app.jar"]