# Quick Start Guide - Fix Network Error

## Current Status
- ✅ Frontend: Running on `http://localhost:3000`
- ❌ Backend: NOT running on `http://localhost:8080`
- ✅ MySQL: Running on port 3306

## Solution: Start Backend

### Method 1: Use Batch Script (Recommended)
1. Navigate to: `Learning-Management-System-main` folder
2. Double-click: `START_BACKEND.bat`
3. Wait for message: **"Started LearningManagementSystemApplication"**
4. This takes 30-60 seconds
5. Refresh your login page

### Method 2: Manual Start
1. Open **Command Prompt** (Run as Administrator recommended)
2. Navigate to backend:
   ```
   cd C:\Users\balav\Downloads\Learning-Management-System-main\Learning-Management-System-main\backend
   ```
3. Set Java:
   ```
   set JAVA_HOME=C:\Program Files\Java\jdk-21
   ```
4. Start backend:
   ```
   mvn spring-boot:run
   ```
5. Wait for: **"Started LearningManagementSystemApplication"**
6. Keep this window open (don't close it)
7. Refresh login page

## Verify Backend is Running

1. Open browser: `http://localhost:8080/actuator/health`
2. Should see: `{"status":"UP"}`
3. If you see JSON, backend is working!

## Test Login

After backend is running:
1. Go to: `http://localhost:3000/instructor`
2. Login with:
   - Email: `instructor@gmail.com`
   - Password: `instructor2468`
3. Network error should be GONE!

## Troubleshooting

### If Backend Won't Start:
1. Check MySQL is running: `netstat -ano | findstr :3306`
2. Check Java: `java -version` (should show Java 21)
3. Check database credentials in `application.yml`
4. Look for error messages in console

### If Port 8080 is Busy:
1. Find process: `netstat -ano | findstr :8080`
2. Kill process: `taskkill /F /PID <process_id>`
3. Or change port in `application.yml`:
   ```yaml
   server:
     port: 8081
   ```

### Common Errors:
- **"JAVA_HOME not found"**: Set JAVA_HOME environment variable
- **"Connection refused to database"**: Start MySQL service
- **"Port already in use"**: Kill process using port 8080

## Important Notes

- Keep backend console open while using the app
- Backend takes 30-60 seconds to start first time
- Frontend must stay running on port 3000
- MySQL must be running on port 3306

## Test Credentials

### Admin
- Email: `admin@gmail.com`
- Password: `admin2468`
- URL: `http://localhost:3000/admin`

### Instructor
- Email: `instructor@gmail.com`
- Password: `instructor2468`
- URL: `http://localhost:3000/instructor`

### Student
- Register at: `http://localhost:3000/register`
- Select "Student" role
- Complete registration

