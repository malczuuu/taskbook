FROM gradle:8.14-jdk21 AS builder

ARG APP_VERSION

USER root
COPY . .

RUN if [ -n "$APP_VERSION" ]; then \
        gradle build -i -x test -Pversion="$APP_VERSION"; \
    else \
        gradle build -i -x test; \
    fi

FROM eclipse-temurin:21-alpine

WORKDIR /taskbook

EXPOSE 80

COPY --from=builder /home/gradle/build/libs/*.jar /taskbook/taskbook.jar

ENV JAVA_OPTS_DEFAULT="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+ExitOnOutOfMemoryError \
-Dfile.encoding=UTF-8 \
-Duser.timezone=UTC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/tmp"

ENV EXTRA_JAVA_OPTS=""

ENV SPRING_PROFILES_ACTIVE="production"

ENV APP_ARGS=""

ENV JAVA_OPTS="$JAVA_OPTS_DEFAULT $EXTRA_JAVA_OPTS"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /taskbook/taskbook.jar $APP_ARGS"]
