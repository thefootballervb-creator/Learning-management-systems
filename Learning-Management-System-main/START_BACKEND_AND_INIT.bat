@echo off
echo ========================================
echo Starting Backend and Initializing Courses
echo ========================================
echo.

cd backend
set JAVA_HOME=C:\Program Files\Java\jdk-21
echo Starting backend server...
start "Backend Server" cmd /k "mvn spring-boot:run"

echo.
echo Waiting 50 seconds for backend to start...
timeout /t 50 /nobreak >nul 2>&1

echo.
echo Checking backend status and initializing courses...
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -Method GET -TimeoutSec 5; Write-Host 'Backend is UP! Status:' $r.StatusCode; Write-Host ''; Write-Host 'Checking courses...'; $courses = Invoke-WebRequest -Uri 'http://localhost:8080/api/courses' -Method GET -TimeoutSec 5; $data = $courses.Content | ConvertFrom-Json; Write-Host 'Courses Count:' $data.Count; if ($data.Count -eq 0) { Write-Host ''; Write-Host 'Database is EMPTY - initializing courses...'; $init = Invoke-WebRequest -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 30; $initData = $init.Content | ConvertFrom-Json; Write-Host 'Courses initialized:' $initData.count; Write-Host 'Success:' $initData.success } else { Write-Host ''; Write-Host 'Courses already exist in database!' } } catch { Write-Host 'ERROR: Backend not responding yet.'; Write-Host 'Please wait a bit longer and run CHECK_AND_INIT_COURSES.bat' }"

echo.
echo ========================================
echo Done! Backend should be running now.
echo Open http://localhost:3000 in your browser
echo ========================================
echo.
pause

