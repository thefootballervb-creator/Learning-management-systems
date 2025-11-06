# Fix ERR_CONNECTION_REFUSED Error

## Problem
You're seeing: "localhost refused to connect" or "ERR_CONNECTION_REFUSED"

## Reason
The backend server is NOT running on `http://localhost:8080`

## Solution - Start Backend Now

### Method 1: Use the Batch Script (Easiest)

1. **Navigate to this folder:**
   ```
   C:\Users\balav\Downloads\Learning-Management-System-main
   ```

2. **Double-click this file:**
   ```
   START_BACKEND_SIMPLE.bat
   ```

3. **A new window will open** - this is the backend server
4. **WAIT 30-60 seconds** for it to start
5. **Look for this message:**
   ```
   Started LearningManagementSystemApplication
   ```

6. **KEEP THE WINDOW OPEN** - don't close it!

7. **Test it:**
   - Open: `http://localhost:8080/actuator/health`
   - Should see: `{"status":"UP"}`

8. **Refresh your login page:**
   - Go to: `http://localhost:3000/instructor`
   - Press F5
   - Error should be gone!

### Method 2: Manual Start (If batch doesn't work)

1. **Open Command Prompt**

2. **Run these commands one by one:**
   ```cmd
   cd C:\Users\balav\Downloads\Learning-Management-System-main\Learning-Management-System-main\backend
   ```
   
   ```cmd
   set JAVA_HOME=C:\Program Files\Java\jdk-21
   ```
   
   ```cmd
   set PATH=%JAVA_HOME%\bin;%PATH%
   ```
   
   ```cmd
   mvn spring-boot:run
   ```

3. **Wait for:** "Started LearningManagementSystemApplication"
4. **Keep window open**
5. **Test:** `http://localhost:8080/actuator/health`

## Verification Steps

### Check 1: Is Backend Running?
```cmd
netstat -ano | findstr :8080
```
- **If you see output** → Backend is running ✅
- **If no output** → Backend is not running ❌

### Check 2: Health Endpoint
Open in browser: `http://localhost:8080/actuator/health`
- **If you see JSON** → Backend is working ✅
- **If ERR_CONNECTION_REFUSED** → Backend not running ❌

### Check 3: Backend Console Window
Look for:
- ✅ "Started LearningManagementSystemApplication" = Working
- ❌ Error messages = Problem
- ❌ Window closed = Backend stopped

## Common Issues

### Issue 1: "JAVA_HOME not found"
**Fix:**
- Make sure Java is installed at: `C:\Program Files\Java\jdk-21`
- Check: `java -version` should work
- If different path, update batch script

### Issue 2: "Port 8080 already in use"
**Fix:**
```cmd
netstat -ano | findstr :8080
taskkill /F /PID <process_id>
```
Then start backend again

### Issue 3: "Connection refused to database"
**Fix:**
- Make sure MySQL is running
- Check: `netstat -ano | findstr :3306`
- Start MySQL service if needed

### Issue 4: Backend starts but crashes
**Check backend window for errors:**
- Database connection errors → Check MySQL
- Port errors → Kill process using port 8080
- Java errors → Check JAVA_HOME

## Quick Checklist

Before starting backend:
- [ ] MySQL is running (port 3306)
- [ ] Java is installed (Java 21)
- [ ] JAVA_HOME is set (or use batch script)
- [ ] Port 8080 is free

After starting backend:
- [ ] Backend window is open
- [ ] "Started LearningManagementSystemApplication" message appears
- [ ] `http://localhost:8080/actuator/health` returns JSON
- [ ] Can refresh login page without error

## What Should Happen

1. **Start backend** → Window opens, shows logs
2. **Wait 30-60 seconds** → Backend compiles and starts
3. **See "Started" message** → Backend is ready
4. **Test health endpoint** → Should return JSON
5. **Refresh login page** → Error disappears

## Still Not Working?

1. **Check backend window** for error messages
2. **Verify MySQL** is running
3. **Check Java version**: `java -version`
4. **Try different port** in `application.yml`:
   ```yaml
   server:
     port: 8081
   ```
   Then update frontend API URL

## Important Notes

- **Backend must stay running** - don't close the window
- **First startup takes 30-60 seconds** - be patient
- **Frontend (port 3000)** can stay running
- **Backend (port 8080)** must be running for login to work

Once backend is running, the connection error will be resolved!

