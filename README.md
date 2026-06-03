# Zimchat — Real-Time Chat Application

A real-time chat web application built with **Java**, **Spring Boot**, and **WebSockets (STOMP)**. Users can send messages instantly, see timestamps, and share file attachments (images and documents) in a shared room.

**Live demo:** [https://real-time-chat-web-app-bqsb.onrender.com](https://real-time-chat-web-app-bqsb.onrender.com)  
*(Free tier may take ~30–60 seconds to wake up after idle time.)*

![Chat UI](https://github.com/user-attachments/assets/5c50ab6d-1058-4307-8718-34843785b1a0)
![Chat with attachments](https://github.com/user-attachments/assets/704ef802-b70a-493d-9474-fca8db93fc94)

---

## Features

- Real-time messaging via **STOMP over WebSockets** (SockJS fallback)
- Server-rendered UI with **Thymeleaf** and Bootstrap
- Message timestamps (ISO format, displayed in local time)
- File attachments (base64, up to 5 MB) — images preview inline, other files as downloads
- **Docker** containerization for consistent local and cloud runs
- **CI** with GitHub Actions (automated tests on push)
- **CD** via Render — auto-deploy on push to `main`

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Java 17, Spring Boot 3.4 |
| Real-time | Spring WebSocket, STOMP, SockJS |
| Frontend | Thymeleaf, Bootstrap 5, Stomp.js |
| Build | Maven |
| Container | Docker (multi-stage Dockerfile) |
| CI | GitHub Actions |
| Hosting | [Render](https://render.com) (Docker, free tier) |

---

## How It Works

1. User opens `/chat` and connects via SockJS to the STOMP endpoint `/chat`.
2. Messages are sent to `/app/sendMessages` and broadcast to all subscribers on `/topic/messages`.
3. The in-memory message broker keeps the setup simple for demos (no database required).

```
Browser  →  HTTP/WebSocket  →  Spring Boot  →  In-memory STOMP broker  →  All connected clients
```

---

## Prerequisites

- **Java 17+** (for local Maven runs)
- **Docker Desktop** (optional, for containerized runs)
- **Git**

---

## Run Locally

### Option 1 — Maven

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Open [http://localhost:8080/chat](http://localhost:8080/chat)

### Option 2 — Docker

```bash
docker build -t zimchat .
docker run -p 8080:8080 --name zimchat-app zimchat
```

### Option 3 — Docker Compose

```bash
docker compose up --build
```

Health check: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

## API & Endpoints

| Method / Type | Path | Description |
|---------------|------|-------------|
| GET | `/` | Redirects to `/chat` |
| GET | `/chat` | Chat UI |
| WebSocket (STOMP) | `/chat` | SockJS/STOMP connection |
| SEND | `/app/sendMessages` | Publish a message |
| SUBSCRIBE | `/topic/messages` | Receive broadcasts |
| GET | `/actuator/health` | Health status (for monitoring) |

---

## Deployment

The app is deployed on **Render** as a **Docker** web service:

1. Push to `main` on GitHub.
2. **GitHub Actions** runs `./mvnw test`.
3. **Render** builds the image from `Dockerfile` and deploys automatically.

### Environment (Render)

| Variable | Value |
|----------|--------|
| `SPRING_PROFILES_ACTIVE` | `prod` |

Production config uses `PORT` from Render (see `application-prod.properties`).

---

## Project Structure

```
src/main/java/com/cloudmathew/zimchat/
├── ZimchatApplication.java      # Entry point
├── Config/WebSocketConfig.java  # STOMP & SockJS setup
├── Controller/ChatController.java # HTTP + message handling
└── Models/ChatMessage.java      # Message payload

src/main/resources/
├── application.properties       # Default config
├── application-prod.properties  # Cloud / Docker config
└── templates/chat.html          # Chat UI

Dockerfile, docker-compose.yml   # Container setup
.github/workflows/ci.yml         # CI pipeline
```

---

## CI/CD

| Stage | Tool | What it does |
|-------|------|----------------|
| **CI** | GitHub Actions | Runs Maven tests on push/PR to `main` |
| **CD** | Render | Builds Docker image and deploys on push to `main` |

Workflow file: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

---

## Author

**Mathew Shereni** — [GitHub](https://github.com/MATHEW-SHERENI)

Repository: [Real-time-chat-web-app](https://github.com/MATHEW-SHERENI/Real-time-chat-web-app)

---

## License

This project was developed as part of academic coursework (PSD). Use and modify as needed for learning and portfolio purposes.
