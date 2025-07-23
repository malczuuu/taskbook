# Taskbook Docker Compose Deployment

This directory contains an example Docker Compose setup for Taskbook, including the backend, frontend, database, and a
reverse proxy.

## Services

- **`mariadb`**: MariaDB 11.8.2 database. Initializes with a schema from `database.schema.sql`. Credentials and database
  name are set via environment variables.
- **`taskbook`**: Taskbook backend (Spring Boot). Connects to MariaDB using the provided credentials and JDBC URL. JWT
  secret and lifetime are set via environment variables.
- **`taskbook-frontend`**: Taskbook frontend (Angular). Serves the web application.
- **`gateway`**: `nginx` reverse proxy. Forwards API requests to the backend and all other requests to the frontend.
  Exposes port `26162` on localhost.

## Usage

1. Ensure Docker and Docker Compose are installed.
2. Run `docker compose pull` to download the latest images. This is optional, but keep it in mind as
   `docker-compose.yaml` uses `latest`.
3. Run `docker compose up` in this directory.
4. Access the application at [http://localhost:26162](http://localhost:26162).
   ```text
   username: admin@example.com
   password: admin
   ```

**Note:** The MariaDB service may take some time to initialize. The backend might keep restarting until the database is
ready.

## Ports

- Application: [http://localhost:26162](http://localhost:26162)
