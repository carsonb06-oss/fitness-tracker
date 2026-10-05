# Fitness Tracker API

A REST API for logging workouts, tracking sets and reps, and computing training stats like personal records — built to practice backend fundamentals: relational data modeling, authentication, and API design.

## Features

- **User accounts** — registration and login with hashed passwords (BCrypt) and JWT-based authentication
- **Workout logging** — log a full session (date, exercises, sets) in a single request, with nested data saved via cascading relationships
- **Exercise catalog** — a shared, reusable list of exercises (name + muscle group)
- **Computed stats** — personal record lookup (highest weight ever lifted for a given exercise) and total training volume per workout
- **Protected routes** — workout data requires a valid JWT; exercise catalog and auth routes are public

## Tech stack

- **Java 21**, **Spring Boot 4**
- **Spring Data JPA** / Hibernate — ORM and persistence
- **PostgreSQL** — relational database
- **Spring Security** + **JJWT** — authentication and JWT issuing/validation
- **Maven** — build and dependency management

## Data model

```
User
Exercise ──< WorkoutExercise >── Workout
                │
                └──< SetEntry
```

- A `Workout` belongs to a session date and contains many `WorkoutExercise` entries
- Each `WorkoutExercise` links to one `Exercise` from the catalog and contains many `SetEntry` records (reps + weight)
- `Exercise` is a shared, reusable catalog entry — the same "Bench Press" can appear across many workouts

## Getting started

### Prerequisites
- Java 21
- Maven
- PostgreSQL (or Docker, to run it in a container)

### Setup

1. Clone the repo:
   ```
   git clone https://github.com/YOUR_USERNAME/fitness-tracker.git
   cd fitness-tracker
   ```

2. Start PostgreSQL and create a database:
   ```
   docker run --name fitness-db -e POSTGRES_PASSWORD=yourpassword -p 5432:5432 -d postgres
   docker exec -it fitness-db psql -U postgres -c "CREATE DATABASE fitness_tracker;"
   ```

3. Configure `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/fitness_tracker
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

4. Run it:
   ```
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`.

## API endpoints

### Auth
| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|----------------|
| POST | `/auth/register` | Create a new user | No |
| POST | `/auth/login` | Log in, returns a JWT | No |

### Exercises
| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|----------------|
| GET | `/exercises` | List all exercises | No |
| GET | `/exercises/{id}` | Get one exercise | No |
| POST | `/exercises` | Create an exercise | No |
| DELETE | `/exercises/{id}` | Delete an exercise | No |
| GET | `/exercises/personal-record/{name}` | Highest weight ever logged for an exercise | No |

### Workouts
| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|----------------|
| GET | `/workouts` | List all workouts | Yes |
| GET | `/workouts/{id}` | Get one workout | Yes |
| POST | `/workouts` | Log a workout (with nested exercises/sets) | Yes |
| DELETE | `/workouts/{id}` | Delete a workout | Yes |

For protected routes, include the token from `/auth/login` as a header:
```
Authorization: Bearer <token>
```

## Example: logging a workout

```
POST /workouts
Authorization: Bearer <token>
Content-Type: application/json

{
    "date": "2026-09-28",
    "exercises": [
        {
            "exercise": { "id": 1 },
            "sets": [
                { "reps": 8, "weight": 135 },
                { "reps": 6, "weight": 155 }
            ]
        }
    ]
}
```

## Known simplifications

A few deliberate shortcuts, worth noting as "next steps" rather than oversights:

- **Workouts aren't yet scoped to individual users** — any logged-in user can currently see all workout data. The `Workout` entity would need a `User` reference, filtered by the authenticated user on each request.
- **Schema changes rely on `ddl-auto=update`** rather than versioned migrations (e.g. Flyway/Liquibase), which is fine for development but not how a production system would manage schema changes.
- **The JWT signing key is hardcoded** rather than pulled from an environment variable/secrets manager.

## Possible extensions

- Scope workouts per-user (see above)
- Weekly/monthly training volume summaries
- A simple frontend (React or a JavaFX desktop client) to interact with the API visually
- Deploy to Render/Railway with a hosted Postgres instance
