# Learning Management System 🎓

A full-stack web application built using **Spring Boot** (backend) and **React.js** (frontend) that allows administrators, teachers, and students to manage courses, assignments, assessments, and progress.

## 🚀 Features
- User authentication and roles (Admin, Teacher, Student)
- Course creation and enrollment
- Video lessons and learning progress
- Assessments with 20+ domain questions per course and certificates
- Assignment upload and grading
- Student and Instructor dashboards
- Secure REST API with Spring Boot (JWT)

## 🧩 Tech Stack
- **Frontend:** React.js, Tailwind CSS, JavaScript  
- **Backend:** Spring Boot (Java 21)  
- **Database:** MySQL  
- **Build Tools:** Maven (backend), npm (frontend)  
- **Other:** Axios, React Router, Ant Design (messages)

## ⚙️ Setup Instructions

### 1) Clone the repository
```bash
git clone https://github.com/venkat777164/learning--management-system.git
cd learning--management-system
```

> If your project directory name differs (e.g., nested `Learning-Management-System-main`), `cd` into that directory instead.

### 2) Backend (Spring Boot)
Requirements: JDK 21, Maven Wrapper included (`mvnw`/`mvnw.cmd`)

- Configure database in `backend/src/main/resources/application.yml` (default profile) or provide environment variables for your DB in production.
- Run locally:
  - Windows:
    ```bash
    cd backend
    ./mvnw.cmd spring-boot:run
    ```
  - macOS/Linux:
    ```bash
    cd backend
    ./mvnw spring-boot:run
    ```
- The backend starts by default on `http://localhost:8080`.

### 3) Frontend (React)
Requirements: Node.js LTS (18+ recommended)

```bash
cd frontend
npm install
# Point the frontend to your backend URL
# (optional in dev — defaults to http://localhost:8080)
# Windows (PowerShell)
$env:REACT_APP_API_BASE_URL="http://localhost:8080"; npm start
# macOS/Linux
REACT_APP_API_BASE_URL="http://localhost:8080" npm start
```

The app will open on `http://localhost:3000`.

### Environment configuration
The frontend reads the API base URL in this order:
1. `REACT_APP_API_BASE_URL` environment variable (build/runtime)
2. `window.__API_BASE_URL__` (you can set this in `public/index.html`)
3. Fallback: `http://localhost:8080`

Code location:
```text
frontend/src/api/constant.js
```

## 📦 Production Build
- Frontend build:
  ```bash
  cd frontend
  npm run build
  ```
- Backend build (jar):
  ```bash
  cd backend
  ./mvnw -DskipTests package
  java -jar target/Learning-Management-System-0.0.1-SNAPSHOT.jar
  ```

## ☁️ Deployment

### Render (Backend)
Two options:
1. Blueprint (recommended): commit `render.yaml` at repo root and create a Blueprint in Render. It builds `backend` using Maven and starts the jar.
2. Manual service: New → Web Service → select repo
   - Root directory: `backend`
   - Build command: `./mvnw -DskipTests package`
   - Start command: `java -jar target/Learning-Management-System-0.0.1-SNAPSHOT.jar`
   - Env var: `JAVA_VERSION=21`

After deploy, copy your backend URL (e.g., `https://<service>.onrender.com`).

### Netlify (Frontend)
- New site from Git
  - Base directory: `frontend`
  - Build command: `npm run build`
  - Publish directory: `build`
- Add environment variable:
  - `REACT_APP_API_BASE_URL = https://<service>.onrender.com`
- SPA routing is handled by `frontend/netlify.toml`.

## 🧪 Useful Scripts
- `backend/mvnw spring-boot:run` – run backend in dev
- `frontend/npm start` – run frontend in dev
- `frontend/npm run build` – production build

## 📁 Project Structure
```
backend/
  src/main/java/...        # Spring Boot application and modules
  src/main/resources/      # application.yml
frontend/
  src/                     # React app source
  public/                  # Static assets
```

## 🤝 Contributing
- Fork the repo, create a feature branch, and open a PR.
- Follow existing code style and keep changes focused.

## 🛡️ License
This project is for educational purposes. Add a specific license if you plan to distribute.

---
If you need help wiring your Render URL into Netlify or troubleshooting deploys, open an issue or ask for support.

