FROM node:24.4.1-slim AS builder

WORKDIR /builder

ARG APP_VERSION

COPY . ./
RUN npm install && \
    if [ -n "$APP_VERSION" ]; then \
        npm version "$APP_VERSION" --no-git-tag-version; \
    fi && \
    npm run build

FROM nginx:1.29.0-alpine

WORKDIR /usr/share/nginx/html

COPY docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /builder/dist/taskbook-frontend/* ./
