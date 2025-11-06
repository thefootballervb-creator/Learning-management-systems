# Troubleshooting Network Error

## Problem
The instructor login page (and other pages) show "Network error. Please try again."

## Root Cause
The backend server is not running on `http://localhost:8080`.

## Solution

### Option 1: Start Backend Using Batch Script (Easiest)
1. Double-click `START_BACKEND.bat` in the project root
2. Wait for the message "Started LearningManagementSystemApplication"
3. The backend will be ready in 30-60 seconds
4. Refresh the login page

### Option 2: Manual Start
1. Open Command Prompt
2. Navigate to the backend folder:
   ```
   cd Learning-Management-System-main\Learning-Management-System-main\backend
   ```
3. Set JAVA_HOME:
   ```
   set JAVA_HOME=C:\Program Files\Java\jdk-21
   ```
4. Start the backend:
   ```
   mvn spring-boot:run
   ```
5. Wait for "Started LearningManagementSystemApplication"
6. Refresh the login page

### Option 3: Check if Backend is Already Running
1. Open browser and go to: `http://localhost:8080/actuator/health`
2. If you see JSON response, backend is running
3. If you get connection error, backend is not running

## Verification Steps

1. **Check Backend Status:**
   - Open: `http://localhost:8080/actuator/health`
   - Should return: `{"status":"UP"}`

2. **Check Frontend Connection:**
   - Frontend is running on: `http://localhost:3000`
   - Frontend tries to connect to: `http://localhost:8080`
   - Both should be running

3. **Check MySQL:**
   - MySQL should be running on port 3306
   - Default credentials: `root` / `123456789`

## Common Issues

### Issue 1: Port 8080 Already in Use
**Solution:**
- Find process using port 8080: `netstat -ano | findstr :8080`
- Kill the process or change backend port in `application.yml`

### Issue 2: MySQL Not Running
**Solution:**
- Start MySQL service
- Verify connection in `application.yml`

### Issue 3: JAVA_HOME Not Set
**Solution:**
- Set JAVA_HOME: `set JAVA_HOME=C:\Program Files\Java\jdk-21`
- Or update system environment variables

### Issue 4: Backend Takes Too Long
**Solution:**
- First startup takes 30-60 seconds (compiling dependencies)
- Check console for errors
- Verify MySQL is running

## Quick Test

After starting backend, test the connection:
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/actuator/health"
```

If successful, you'll see the health status. Then refresh your login page.

## Still Not Working?

1. Check backend console for error messages
2. Verify MySQL is running: `netstat -ano | findstr :3306`
3. Check Java version: `java -version`
4. Verify application.yml database settings
5. Check firewall settings

