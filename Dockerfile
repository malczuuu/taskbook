FROM gradle:6.1.1-jdk11 as builder

USER root
COPY . .
RUN gradle build -i -x test && chmod +x ./docker-entrypoint.sh


FROM openjdk:11.0.5-jre-slim

WORKDIR /taskbook

EXPOSE 80

COPY --from=builder /home/gradle/build/libs/*.jar /taskbook/taskbook.jar
COPY --from=builder /home/gradle/docker-entrypoint.sh /taskbook/taskbook.sh

ENV MARIADB_URI jdbc:mariadb://mariadb:3306/taskbook
ENV MARIADB_USERNAME taskbook
ENV MARIADB_PASSWORD taskbook
ENV JWT_SECRET 0000000000
ENV JWT_LIFETIME 864000

ENTRYPOINT [ "/taskbook/taskbook.sh" ]
