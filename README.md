# 🎬 Movie Booking System

A RESTful backend for booking movie tickets, built with **Spring Boot 4**, **Spring Security** and **JWT** authentication, backed by **MySQL**.

Users can register, log in, browse movies, theatres and shows, and book seats. Admins manage movies, theatres and shows.

---

## ✨ Features

- 🔐 **Authentication and authorization**
  - Registration and login with BCrypt-hashed passwords
  - Stateless JWT authentication (sent as an `HttpOnly` cookie or a `Bearer` header)
  - Role-based access: `ROLE_USER` and `ROLE_ADMIN`
- 🎞️ **Movies, theatres and shows** managed through REST endpoints (admin-protected)
- 🎟️ **Seat booking**
  - Rejects seats already taken and duplicate seats in one request
  - Cancelled bookings free their seats
  - New bookings start as `PENDING` with a 2-hour payment deadline
  - ⏰ A scheduled job cancels expired `PENDING` bookings every minute
  - Bookings can be confirmed or cancelled; cancellation is blocked within 2 hours of showtime
- 🚨 **Global exception handling** with clean JSON errors (409 for duplicates, 401 for bad credentials)

---

## 🛠️ Tech Stack

| Layer     | Technology                              |
|-----------|-----------------------------------------|
| ☕ Language  | Java 21                                 |
| 🍃 Framework | Spring Boot 4.0.5 (Web MVC, Data JPA)   |
| 🔒 Security  | Spring Security 7, JWT (jjwt 0.13.0)    |
| 🗄️ Database  | MySQL 8                                 |
| 🔗 ORM       | Hibernate 7                             |
| 📦 Build     | Maven                                   |
| 🧰 Other     | Lombok                                  |

---

## 📁 Project Structure

```
src/main/java/Springproject/MovieBookingApp
├── Controller/     REST controllers (auth, admin, movie, theatre, show, booking)
├── Service/        Business logic (authentication, JWT, bookings, shows, ...)
├── Repository/     Spring Data JPA repositories
├── Entity/         JPA entities (User, Movie, Theatre, Show, Booking, ...)
├── Dto/            Request and response objects
├── SecurityConfig.java
├── JwtAuthenticationFilter.java
└── GlobalExceptionHandler.java
```

---

## 🚀 Getting Started

### 📋 Prerequisites

- JDK 21
- Maven 3.9+ (or use the included `mvnw`)
- MySQL 8 running on `localhost:3306`

### 1️⃣ Clone the repository

```bash
git clone https://github.com/selvam233/MovieBookingSystem.git
cd MovieBookingSystem/MovieBookingApp
```

### 2️⃣ Configure the application

`application.yml` is git-ignored because it holds secrets. Create
`src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: MovieBookingApp

  datasource:
    url: jdbc:mysql://localhost:3306/moviebookingdb?createDatabaseIfNotExist=true
    username: root
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update
    open-in-view: false
    show-sql: true

server:
  port: 8080

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000   # 24 hours in milliseconds
```

🔑 Generate a secret. It must be Base64 and decode to at least 32 bytes:

```bash
openssl rand -base64 32
```

Set the environment variables:

```bash
# macOS / Linux
export DB_PASSWORD=your_mysql_password
export JWT_SECRET=your_generated_secret

# Windows PowerShell
$env:DB_PASSWORD="your_mysql_password"
$env:JWT_SECRET="your_generated_secret"
```

### 3️⃣ Run

```bash
./mvnw spring-boot:run
```

On Windows: `mvnw.cmd spring-boot:run`. The API is available at `http://localhost:8080`.

Hibernate creates the tables on first start (`ddl-auto: update`).

---

## 👑 Creating the First Admin

Admin registration requires an existing admin, so promote the first one manually:

1. Register a normal user through `POST /api/auth/register`.
2. Promote it in MySQL:

```sql
UPDATE user_roles
SET roles = 'ROLE_ADMIN'
WHERE user_userid = (SELECT userid FROM users WHERE username = 'your_username');
```

3. Log in again so the new token carries the admin role.

---

## 📡 API Overview

### 🔓 Authentication (public)

| Method | Endpoint             | Description        |
|--------|----------------------|--------------------|
| POST   | `/api/auth/register` | Register a user    |
| POST   | `/api/auth/login`    | Log in, get a JWT  |

**Register**

```json
{
  "username": "selvam",
  "email": "selvam@example.com",
  "password": "Test@1234"
}
```

**Login**

```json
{
  "username": "selvam",
  "password": "Test@1234"
}
```

The login response returns the username and roles. The JWT is set in an `HttpOnly` cookie named `jwt`, and clients may also send it as `Authorization: Bearer <token>`.

### 🛡️ Admin (requires `ROLE_ADMIN`)

| Method | Endpoint              | Description         |
|--------|-----------------------|---------------------|
| POST   | `/api/admin/register` | Register an admin   |

### 🎥 Resources (authenticated)

Movies, theatres, shows and bookings are exposed through their controllers
(`Moviecontroller`, `TheatreController`, `ShowController`, `BookingController`).
Check each controller for the exact routes. The main operations are:

| Area     | Operations                                                            |
|----------|-----------------------------------------------------------------------|
| 🎞️ Movies   | Create, list, update, delete                                          |
| 🏛️ Theatres | Create, list, update, delete                                          |
| 🕒 Shows    | Create, list, search by movie or theatre name, update, delete         |
| 🎟️ Bookings | Create, list by user or show, filter by status, confirm, cancel       |

### 📜 Booking rules

- ✅ Seats must be free and cannot repeat within one request.
- ⏳ A new booking is `PENDING` for 2 hours, then auto-cancelled if not confirmed.
- 🚫 Cancellation is not allowed within 2 hours of the show or twice.
- 🔒 A show with existing bookings cannot be deleted.

---

## 🔄 Authentication Flow

1. `POST /api/auth/login` returns the `jwt` cookie.
2. The browser or Postman sends it automatically on later requests.
3. `JwtAuthenticationFilter` validates the token and sets the user's roles.
4. Protected endpoints return `403` without a valid token; `/api/admin/**` also needs `ROLE_ADMIN`.

---

## ⚠️ Error Responses

Errors are returned as JSON:

```json
{ "error": "Username already exists" }
```

| Status | Meaning                                           |
|--------|---------------------------------------------------|
| 400    | Invalid request or business-rule violation        |
| 401    | Invalid username or password                      |
| 403    | Missing or invalid token, or insufficient role    |
| 409    | Conflict, such as a duplicate username            |

---

## 🔐 Security Notes

- 🙈 Never commit `application.yml`, your DB password or your JWT secret.
- 🎲 Use a randomly generated JWT secret of at least 32 bytes.
- 🔒 Use `secure(true)` on the cookie and HTTPS in production.
- 🗃️ Set `ddl-auto` to `validate` or `none` in production and use migrations.

---

## 🗺️ Roadmap

- 💳 Payment integration for confirming bookings
- 💰 Server-side price calculation from the show's ticket price
- 🔐 Pessimistic locking to prevent double booking under high concurrency
- 🧱 Dedicated exception classes instead of `RuntimeException`
- 📖 Swagger / OpenAPI documentation
- 🧪 Unit and integration tests

---

## 👨‍💻 Author

**Selvam** ([@selvam233](https://github.com/selvam233))
