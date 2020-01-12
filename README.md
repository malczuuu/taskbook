# Taskbook

[![Build Status](https://travis-ci.org/malczuuu/taskbook.svg?branch=master)](https://travis-ci.org/malczuuu/taskbook)

Backend service for Taskbook application. Checkout also
[frontend application](https://github.com/malczuuu/taskbook-frontend).

## Configuration

Configuration is possible via JVM properties.

| property                       | description                | default                                  |
|--------------------------------|----------------------------|------------------------------------------|
| `-Dspring.datasource.url`      | database connection string | `jdbc:mariadb://127.0.0.1:3306/taskbook` |
| `-Dspring.datasource.username` | database username          | `taskbook`                               |
| `-Dspring.datasource.password` | database password          | `taskbook`                               |
| `-Dtaskbook.jwt.secret`        | JWT secret                 | `0000000000`                             |
| `-Dtaskbook.jwt.lifetime`      | JWT lifetime (in seconds)  | `864000`                                 |

## Development

* Prepare [Docker](https://docs.docker.com/install/) environment with `docker-compose up -d`.
* Run service in development environment with `./gradlew bootRun` task.

**Note** that running service requires Java version at least 8.

## Docker

Docker image can be configured using environment variables which represents JVM properties listed
above.

| environment        | property                       | default value                          |
|--------------------|--------------------------------|----------------------------------------|
| `MARIADB_URI`      | `-Dspring.datasource.url`      | `jdbc:mariadb://mariadb:3306/taskbook` |
| `MARIADB_USERNAME` | `-Dspring.datasource.username` | `taskbook`                             |
| `MARIADB_PASSWORD` | `-Dspring.datasource.password` | `taskbook`                             |
| `JWT_SECRET`       | `-Dtaskbook.jwt.secret`        | `0000000000`                           |
| `JWT_LIFETIME`     | `-Dtaskbook.jwt.lifetime`      | `864000`                               |

Note that within Docker container service will be launched with `production` profile, which means
that database schema won't be automatically created (`spring.jpa.hibernate.ddl-auto=none`). It's
required to create [database schema](/ops/database.schema.sql) manually.
