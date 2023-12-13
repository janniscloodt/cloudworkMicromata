# Cloudwork Micromata

## What is this?

This repository is a playground to experiment with the cloud. This project started with simple GitHub Actions to automatically 
test, build and deploy the application to the cloud.
The goal now is it to build a small feed (like Instagram) with login, registration and ChatGPT integration.

## How to get startet?

### View the application

The latest version of the application is available [here](https://cloudwork-frontend.onrender.com).

### Local Setup

#### Backend

The backend includes a lokal docker compose file to get a database up and running. Use the profile ``local`` when starting the
spring application.

```bash
cd backend
docker-compose -f docker-compose-local.yml up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

#### Frontend

The frontend includes an example environment file.

```bash
cd frontend
cp .env.example .env
npm ci
npm run dev
```