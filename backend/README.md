# Backend

## Environment Variables

### Database

**``SPRING_DATASOURCE_URL``**: JDBC url to a postgres database. E.g.: `jdbc:postgresql://HOST:PORT/DATABASE`

**``SPRING_DATASOURCE_USERNAME``**: Account username for the postgres database.

**``SPRING_DATASOURCE_PASSWORD``**: Account password for the postgres database.

### Other

**``JWT_KEY``**: Key to sign all jwts. **A change in production would invalidate all json web tokens!**

## Local Development

We recommend using JetBrains IDE `IntelliJ IDEA`. The backend is a `Spring Boot Application`.
For local development we've set up a docker-compose file and a local application config.

Run the local docker-compose file:
```bash
docker-compose -f docker-compose-local.yml up -d
```

And **then** start the application with the `local` profile.
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

**NOTE**: You can run the application in IntelliJ with a `Spring Boot` Run Configuration. There you have to set `Active Profiles` to `local`.

## Deployment

The application includes a `Dockerfile` and can be run everywhere. This project initially aimed to teach us the basics of
cloud work, so it will be automatically deployed to [Render](https://render.com).

**Please make sure to set all required environment variables before running the application.**