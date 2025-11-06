# How to Verify Backend is Running

## Quick Check

Open this URL in your browser:
```
http://localhost:8080/actuator/health
```

### If Working:
You'll see: `{"status":"UP"}`
- ✅ Backend is running!
- Refresh your login page
- Network error should be gone

### If NOT Working:
You'll see: "ERR_CONNECTION_REFUSED" or "This site can't be reached"
- ❌ Backend is not running
- Follow steps below to start it

## Steps to Start Backend

### Step 1: Start Backend
1. Go to: `Learning-Management-System-main` folder
2. Double-click: `START_BACKEND_SIMPLE.bat`
3. A new window will open
4. **KEEP THIS WINDOW OPEN** (don't close it)

### Step 2: Wait for Startup
Look for this message in the window:
```
Started LearningManagementSystemApplication in X.XXX seconds
```

This takes 30-60 seconds on first run.

### Step 3: Verify
1. Open: `http://localhost:8080/actuator/health`
2. Should see JSON response
3. If yes, backend is working!

### Step 4: Test Login
1. Go to: `http://localhost:3000/instructor`
2. Refresh page (F5)
3. Login should work now!

## Troubleshooting

### Problem: "JAVA_HOME not found"
**Solution:**
- The batch script should set it automatically
- If not, check Java is installed at: `C:\Program Files\Java\jdk-21`

### Problem: "Port 8080 already in use"
**Solution:**
1. Find process: `netstat -ano | findstr :8080`
2. Kill it: `taskkill /F /PID <process_id>`
3. Try starting again

### Problem: Backend starts but crashes
**Check:**
- MySQL is running: `netstat -ano | findstr :3306`
- Database credentials in `application.yml`
- Console window for error messages

### Problem: Still getting ERR_CONNECTION_REFUSED
**Try:**
1. Make sure backend window is still open
2. Check for error messages in backend window
3. Verify port 8080: `netstat -ano | findstr :8080`
4. Wait 60 seconds and try health check again

## What You Should See

### Backend Console Window:
```
============================================
  LEARNING MANAGEMENT SYSTEM - BACKEND
============================================

Starting backend server...
JAVA_HOME=C:\Program Files\Java\jdk-21

Java Version:
java version "21.0.9" ...

Starting Spring Boot...

... (lots of Spring Boot startup logs) ...

Started LearningManagementSystemApplication in 45.123 seconds
```

### Browser Health Check:
```
http://localhost:8080/actuator/health
```
Response: `{"status":"UP"}`

## Important Notes

- **Keep backend window open** while using the app
- Backend must be running for frontend to work
- First startup takes longer (30-60 seconds)
- If you close the backend window, restart it

## Quick Test Commands

Check if backend is running:
```cmd
netstat -ano | findstr :8080
```
If you see output, port 8080 is in use (backend might be running)

Test backend response:
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/actuator/health"
```
If successful, backend is working!

