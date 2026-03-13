# 🧭 GuideBook — Tour Guide Booking System

A full-stack web application built with **Spring Boot** that connects travelers with local tour guides. Tourists can browse guides, make bookings, pay, chat, and leave reviews. Guides manage their bookings through a dashboard. Admins oversee the entire platform.

🌐 **Live Demo:** [https://guidebook-app.onrender.com](https://guidebook-app.onrender.com)

---

## 📸 Features

### Tourist
- Browse and search approved guides by city
- Book a guide for a specific date
- Pay for confirmed bookings (demo payment)
- Chat with guide after booking is accepted
- Leave reviews and ratings after tour completion
- View all bookings with status tracking

### Guide
- Register a guide profile
- View and manage incoming booking requests
- Accept or reject bookings
- Chat with tourists
- Pending approval notice until admin approves

### Admin
- Auto-created on first startup via environment variables
- Approve or reject guide registrations
- View all bookings across the platform
- Dashboard with revenue, booking, and guide stats

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3.5.11, Java 21 |
| Security | Spring Security (BCrypt, session-based auth) |
| Database | PostgreSQL |
| ORM | Spring Data JPA + Hibernate |
| Frontend | Thymeleaf, HTML, CSS, JavaScript |
| Build | Maven |
| Deployment | Docker + Render |

---

## 🗂️ Project Structure

```
guidebooking/
├── Dockerfile
├── .gitignore
├── pom.xml
└── src/main/
    ├── java/com/example/guidebooking/
    │   ├── config/
    │   │   ├── DataSourceConfig.java      # Parses Render DATABASE_URL
    │   │   └── SecurityConfig.java        # Spring Security rules
    │   ├── controller/                    # All MVC controllers
    │   ├── entity/                        # JPA entities
    │   ├── repository/                    # Spring Data repositories
    │   ├── service/                       # Business logic
    │   └── util/
    └── resources/
        ├── application.properties         # Production config (env vars)
        ├── static/css/main.css
        └── templates/                     # Thymeleaf HTML templates
```

---

## 🚀 Running Locally

### Prerequisites
- Java 21
- Maven
- PostgreSQL running on port 5432

### Steps

**1. Clone the repo**
```bash
git clone https://github.com/YOUR_USERNAME/guidebooking.git
cd guidebooking
```

**2. Create local database**
```sql
CREATE DATABASE traveller;
```

**3. Create `application-local.properties`**

Create this file at `src/main/resources/application-local.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/traveller
spring.datasource.username=postgres
spring.datasource.password=your_local_password
```
> This file is in `.gitignore` — never commit it.

**4. Run with local profile**

In IntelliJ/Eclipse, add this to VM options:
```
-Dspring.profiles.active=local
```

Or via terminal:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=local"
```

**5. Open in browser**
```
http://localhost:8080
```

**Default admin login:**
- Username: `admin`
- Password: `admin123`

---

## 🐳 Docker

```bash
# Build image
docker build -t guidebooking .

# Run container
docker run -p 8080:8080 \
  -e DATABASE_URL=postgresql://user:pass@host/dbname \
  -e ADMIN_USERNAME=admin \
  -e ADMIN_PASSWORD=Admin@2025! \
  -e ADMIN_EMAIL=admin@example.com \
  guidebooking
```

---

## ☁️ Deploying to Render

### 1. Create PostgreSQL Database
- Render → **New +** → **PostgreSQL**
- Plan: Free, Region: Singapore
- Copy the **Internal Database URL** after creation

### 2. Create Web Service
- Render → **New +** → **Web Service**
- Connect your GitHub repo
- Runtime: **Docker**
- Region: Singapore

### 3. Set Environment Variables

| Key | Value |
|-----|-------|
| `DATABASE_URL` | Internal Database URL from step 1 |
| `ADMIN_USERNAME` | your admin username |
| `ADMIN_PASSWORD` | your strong password |
| `ADMIN_EMAIL` | your email |

### 4. Deploy
Click **Create Web Service** — Render builds and deploys automatically.

Every `git push` to `main` triggers an automatic redeploy.

---

## 🔐 Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `DATABASE_URL` | PostgreSQL connection URL (`postgresql://...`) | ✅ Production |
| `ADMIN_USERNAME` | Admin login username | ✅ Production |
| `ADMIN_PASSWORD` | Admin login password | ✅ Production |
| `ADMIN_EMAIL` | Admin email address | ✅ Production |
| `PORT` | Server port (Render sets this automatically) | Auto |

---

## 👤 User Roles & Flow

### CUSTOMER (Tourist)
```
Register → Login → Browse Guides → Book → 
Wait for Guide to Accept → Pay → Chat → Review
```

### GUIDE
```
Register (role=GUIDE) → Login → Fill Guide Profile →
Wait for Admin Approval → Manage Bookings → Chat with Tourists
```

### ADMIN
```
Login → Approve Guides → View All Bookings → Monitor Dashboard
```

---

## 📋 Booking Status Flow

```
PENDING → PAYMENT_PENDING → CONFIRMED
       ↘ REJECTED
```

| Status | Meaning |
|--------|---------|
| `PENDING` | Tourist booked, waiting for guide to accept |
| `PAYMENT_PENDING` | Guide accepted, waiting for tourist to pay |
| `CONFIRMED` | Payment done, tour confirmed |
| `REJECTED` | Guide rejected the booking |

---

## ⚠️ Free Tier Notes

- App **sleeps after 15 min** of inactivity — first request takes ~30 seconds to wake up
- PostgreSQL database **expires after 90 days** on Render free plan
- Use [UptimeRobot](https://uptimerobot.com) (free) to ping the app every 10 minutes to keep it awake

---

## 📄 License

This project is for educational purposes.

code with MVP
