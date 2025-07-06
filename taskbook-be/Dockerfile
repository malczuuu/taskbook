FROM gradle:8.14-jdk21 AS builder

USER root
COPY .. .
RUN gradle build -i -x test

FROM eclipse-temurin:21-alpine

WORKDIR /taskbook

EXPOSE 80

COPY --from=builder /home/gradle/build/libs/*.jar /taskbook/taskbook.jar

ENV JAVA_OPTS="\
-XX:+UseContainerSupport \
-XX:MaxRAMPercentage=75.0 \
-XX:+ExitOnOutOfMemoryError \
-Dfile.encoding=UTF-8 \
-Duser.timezone=UTC \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=/tmp"

ENV SPRING_PROFILES_ACTIVE="production"

ENV EXTRA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /taskbook/taskbook.jar $EXTRA_OPTS"]
