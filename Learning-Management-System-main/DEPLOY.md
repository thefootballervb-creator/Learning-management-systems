# Deploy to Render (Backend) and Netlify (Frontend)

## Overview
- Backend: Spring Boot (Maven) → Render Web Service
- Frontend: React (CRA + Tailwind) → Netlify Static Site
- Frontend API base URL is controlled by `REACT_APP_API_BASE_URL`.

---

## 1) Backend on Render

Prereqs:
- GitHub repo created and pushed
- JDK 21 compatible build (already set in `backend/pom.xml`)

Steps:
1. Commit the provided `render.yaml` to your repo (already added at repo root).
2. Go to Render dashboard → New → Blueprint → Connect your GitHub repo.
3. Render detects `render.yaml` and creates `lms-backend` web service.
4. Confirm settings:
   - Root directory: `backend`
   - Build: `./mvnw -DskipTests package`
   - Start: `java -jar target/Learning-Management-System-0.0.1-SNAPSHOT.jar`
   - Health check path: `/actuator/health` (optional)
   - Env var `JAVA_VERSION=21`
5. Deploy. After deploy, copy the Render URL: `https://<your-service>.onrender.com`.

Notes:
- If MySQL is remote, configure JDBC env vars on Render. If local only, switch to a managed DB for production.
- Ensure CORS is allowed for your Netlify domain.

---

## 2) Frontend on Netlify

Steps:
1. In Netlify, create a New site from Git → select the frontend folder if using monorepo.
   - Base directory: `frontend`
   - Build command: `npm run build`
   - Publish directory: `build`
2. Set Environment variables:
   - `REACT_APP_API_BASE_URL` = `https://<your-service>.onrender.com`
3. Deploy. After deploy, confirm SPA routes work (handled by `frontend/netlify.toml`).

---

## 3) Local overrides (optional)
You can set a global at runtime without rebuild:
```html
<script>
  window.__API_BASE_URL__ = "https://<your-service>.onrender.com";
  </script>
```
This is read by the frontend if `REACT_APP_API_BASE_URL` is not provided.

---

## 4) Where it’s wired
- `frontend/src/api/constant.js` now resolves API base:
  1) `process.env.REACT_APP_API_BASE_URL`
  2) `window.__API_BASE_URL__`
  3) fallback `http://localhost:8080`
- `frontend/netlify.toml` sets SPA redirects and documents env.
- `render.yaml` provisions the backend service.

---

## 5) Quick checklist
- [ ] Backend deployed on Render, URL copied
- [ ] Netlify env `REACT_APP_API_BASE_URL` set to Render URL
- [ ] Frontend redeployed
- [ ] API calls succeed from Netlify site

