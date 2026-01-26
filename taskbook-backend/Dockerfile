FROM gradle:9.3.0-jdk21 AS builder

ARG APP_VERSION

USER root
COPY . .

ENV GRADLE_OPTS="\
-Dorg.gradle.configuration-cache=false \
-Dorg.gradle.daemon=false \
-Dorg.gradle.parallel=false \
-Dorg.gradle.workers.max=1"

RUN if [ -n "$APP_VERSION" ]; then \
        gradle build -i -x test -Pversion="$APP_VERSION"; \
    else \
        gradle build -i -x test; \
    fi

FROM eclipse-temurin:21-alpine

WORKDIR /taskbook

EXPOSE 80

COPY --from=builder /home/gradle/build/libs/*.jar /taskbook/taskbook.jar

ENV SPRING_PROFILES_ACTIVE="production"

ENV JAVA_OPTS_DEFAULT="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+ExitOnOutOfMemoryError \
-Dfile.encoding=UTF-8 \
-Duser.timezone=UTC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/tmp"

ENV JAVA_OPTS_EXTRA=""

ENV APP_ARGS=""

CMD ["/bin/sh", "-c", "exec java $JAVA_OPTS_DEFAULT $JAVA_OPTS_EXTRA -jar /taskbook/taskbook.jar $APP_ARGS"]
