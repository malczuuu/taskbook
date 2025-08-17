# Taskbook

Taskbook - a full-stack, simple issue tracker app with Spring Boot and Angular. Developed by Damian Malczewski as a
project at [Cracow University of Technology](https://pk.edu.pl) in 2019.

Afterward, it serves the purpose of testing new features in Spring Boot, Angular and another development tools.

## Modules

- [**`taskbook-backend`**](./taskbook-backend). Backend application in Spring Boot.
- [**`taskbook-frontend`**](./taskbook-frontend). Frontend application in Angular.
- [**`taskbook-compose`**](./taskbook-compose). Full application setup in Docker Compose.
- [**`taskbook-localhost`**](./taskbook-compose). Environment setup in Docker Compose for local development.

## Setting Up

To set up and demonstrate the application, use the [`taskbook-compose`](./taskbook-compose) module.

## Development

To set up development environment, use the [`taskbook-localhost`](./taskbook-localhost) module.

## Workflows

Project uses GitHub Actions to automate builds, tests and release (to Docker Hub) for both `taskbook-backend` and
`taskbook-frontend` projects.

- [`taskbook-backend-build.yml`](.github/workflows/taskbook-backend-build.yml) – Build and test `taskbook-backend`
  Gradle project on `main` branch and pull requests.
- [`taskbook-backend-build-weekly.yml`](.github/workflows/taskbook-backend-build-weekly.yml) – Build and test
  `taskbook-backend` Gradle project weekly on `main` branch.
- [`taskbook-frontend-build.yml`](.github/workflows/taskbook-frontend-build.yml) – Build and test `taskbook-frontend`
  Node.js project on `main` branch and pull requests.
- [`taskbook-frontend-build-weekly.yml`](.github/workflows/taskbook-frontend-build-weekly.yml) – Build and test
  `taskbook-frontend` Node.js project weekly on `main` branch.
- [`docker-build-all.yml`](.github/workflows/docker-build-all.yml) – Build and push Docker images for both services to
  Docker Hub on tags.
