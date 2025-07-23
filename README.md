# Taskbook

A simple issue-tracker application. Developed by Damian Malczewski as a project at
[Cracow University of Technology][pk.edu.pl] in 2019.

## Table of Contents

* [Summary](#summary)
* [Technologies](#technologies)
* [Running full-stack solution on local machine](#running-full-stack-solution-on-local-machine)

## Summary

The summary is written in Polish and was made for the purpose of documenting the project at college.
It's available [here](./Taskbook-summary-PL.pdf).

## Technologies

- [Spring Boot][spring-boot]
- [Angular][angular]
- [Docker][docker]
- [MariaDB][mariadb]

## Running full-stack solution on local machine

Running relies on [Docker][docker] and [Docker Compose][docker-compose].

1. Build proper Docker images, according to the instructions in `README.md` files.

    * [`taskbook-backend`][taskbook-backend]
    * [`taskbook-frontend`][taskbook-frontend]

2. Launch pre-configured environment from [`taskbook-compose/`][taskbook-compose] directory.

   ```shell
   $ docker compose up -d
   ```

   Verify services being up and running.

   ```shell
   $ docker compose ps
   ```

3. Browse gateway at [`http://localhost:26162`](http://localhost:26162).

4. Log in with default user credentials.

   ```text
   username: admin@example.com
   password: admin
   ```

[pk.edu.pl]: https://pk.edu.pl

[spring-boot]: https://docs.spring.io/spring-boot/docs/2.5.5/reference/html/

[angular]: https://angular.io/docs

[docker]: https://docs.docker.com/

[mariadb]: https://mariadb.com/kb/en/documentation/

[docker-compose]: https://docs.docker.com/compose/

[taskbook]: https://github.com/malczuuu/taskbook

[taskbook-backend]: https://github.com/malczuuu/taskbook/tree/main/taskbook-backend

[taskbook-frontend]: https://github.com/malczuuu/taskbook/tree/main/taskbook-frontend

[taskbook-compose]: https://github.com/malczuuu/taskbook/tree/main/taskbook-compose/deployment
