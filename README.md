# Taskbook

Backend for Taskbook application. Developed by Damian Malczewski as a project at
[Cracow University of Technology][pk.edu.pl] in 2019.

Afterwards it served the purpose of testing Spring Boot updates.

## Table of Contents

* [Building Docker image](#building-docker-image)
* [Configuration](#configuration)
* [Running on local machine](#running-on-local-machine)
* [Project repositories](#project-repositories)

## Building Docker image

Building Docker automatically builds production-ready jarfile in a multi-stage build.

```bash
$ docker build -t taskbook-backend:latest .
```

## Configuration

Configuration is possible via JVM properties.

| property                       | description                | default                                  |
|--------------------------------|----------------------------|------------------------------------------|
| `-Dspring.datasource.url`      | database connection string | `jdbc:mariadb://127.0.0.1:3306/taskbook` |
| `-Dspring.datasource.username` | database username          | `taskbook`                               |
| `-Dspring.datasource.password` | database password          | `taskbook`                               |
| `-Dtaskbook.jwt.secret`        | JWT secret                 | `0000000000`                             |
| `-Dtaskbook.jwt.lifetime`      | JWT lifetime (in seconds)  | `864000`                                 |

### Configuration of Docker container

Docker image can be configured using environment variables which represents JVM properties listed
above.

| environment        | description                | default value                          |
|--------------------|----------------------------|----------------------------------------|
| `MARIADB_URI`      | database connection string | `jdbc:mariadb://mariadb:3306/taskbook` |
| `MARIADB_USERNAME` | database username          | `taskbook`                             |
| `MARIADB_PASSWORD` | database password          | `taskbook`                             |
| `JWT_SECRET`       | JWT secret                 | `0000000000`                           |
| `JWT_LIFETIME`     | JWT lifetime (in seconds)  | `864000`                               |

**Note** that within Docker container service will be launched with `production` profile, which
means that database schema won't be automatically created (`spring.jpa.hibernate.ddl-auto=none`).
It's required to create [database schema](./operations/deployment/database.schema.sql) manually.

## Running on local machine

Running locally requires MariaDB database. See [`docker-compose.yml`](./docker-compose.yml).

```shell
$ docker-compose up -d
```

To run the application use `bootRun` Gradle task.

```shell
$ ./gradlew bootRun
```

The application will be available on [`http://localhost:26160`](http://localhost:26160).

Run service using Gradle with `./gradlew bootRun` task.

**Note**, that as a Docker service, the application is available on `:80`.

Alternatively you can build service with `./gradlew build` task and run JARfile created
in `build/libs/`. Running also works fine from most IDEs.

## Project repositories

* [`taskbook`][taskbook], which holds backend application.
* [`taskbook-frontend`][taskbook-frontend], which holds frontend application.

[pk.edu.pl]: https://pk.edu.pl

[taskbook]: https://github.com/malczuuu/taskbook

[taskbook-frontend]: https://github.com/malczuuu/taskbook-frontend
