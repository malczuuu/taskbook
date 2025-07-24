# Taskbook Backend

## Table of Contents

* [Building Docker Image](#building-docker-image)
* [Configuration](#configuration)
* [Running on local machine](#running-on-local-machine)
* [Project repositories](#project-repositories)

## Building Docker Image

Building Docker automatically builds production-ready jarfile in a multi-stage build.

```bash
docker build -t taskbook-backend:latest .
```

## Configuration

Configuration is possible via application properties.

| property                     | description                | default                                  |
|------------------------------|----------------------------|------------------------------------------|
| `spring.datasource.url`      | database connection string | `jdbc:mariadb://127.0.0.1:3306/taskbook` |
| `spring.datasource.username` | database username          | `taskbook`                               |
| `spring.datasource.password` | database password          | `taskbook`                               |
| `taskbook.jwt.secret`        | JWT secret                 | `0000000000`                             |
| `taskbook.jwt.lifetime`      | JWT lifetime (in seconds)  | `864000`                                 |

**Note** that within Docker container service will be launched with `production` profile, which
means that database schema won't be automatically created (`spring.jpa.hibernate.ddl-auto=none`).
It's required to create [database schema](../taskbook-compose/database.schema.sql) manually.

## Running on local machine

Running locally requires MariaDB database. See [`docker-compose.yaml`](../taskbook-localhost/docker-compose.yaml).

```shell
docker compose up -d
```

To run the application use `bootRun` Gradle task.

```shell
./gradlew bootRun
```

The application will be available on [`http://localhost:26160`](http://localhost:26160).

Run service using Gradle with `./gradlew bootRun` task.

**Note**, that as a Docker service, the application is available on `:80`.

Alternatively you can build service with `./gradlew build` task and run JARfile created in 
`build/libs/`. Running also works fine from most IDEs.

## Code Style

Code style is enforced by `./gradlew spotlessApply` task. It uses [Google Java Style][google-java-style].

[google-java-style]: https://google.github.io/styleguide/javaguide.html
