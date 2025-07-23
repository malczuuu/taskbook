# Taskbook Frontend

## Table of Contents

- [Building Docker image](#building-docker-image)
- [Running on local machine](#running-on-local-machine)
- [Project repositories](#project-repositories)

## Building Docker image

Building Docker automatically builds Angular distribution in a multi-stage build.

```bash
docker build -t taskbook-frontend:latest .
```

## Running on local machine

Running locally requires [`taskbook` backend application](../taskbook-backend) (along with its own
dependencies) to be up and running. Angular development server launches a reverse-proxy for `/api`
paths. Follow [backend instructions](../taskbook-backend/README.md#running-on-local-machine) to launch
it.

To run Angular development server use `start` npm task.

```shell
npm start
```

The application will be available on [`http://localhost:26161`](http://localhost:26161).

**Note**, that as a Docker service, the application works as a
[nginx](https://hub.docker.com/_/nginx) server, available on port `:80`.
