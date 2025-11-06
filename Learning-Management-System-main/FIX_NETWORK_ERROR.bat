@echo off
echo ========================================
echo Fixing Network Error - Starting Backend
echo ========================================
echo.

cd backend
set JAVA_HOME=C:\Program Files\Java\jdk-21
echo Starting backend server...
start "Backend Server" cmd /k "mvn spring-boot:run"

echo.
echo Waiting 60 seconds for backend to start...
timeout /t 60 /nobreak >nul 2>&1

echo.
echo Checking backend and initializing courses...
powershell -Command "$maxAttempts = 15; $attempt = 0; while ($attempt -lt $maxAttempts) { try { Write-Host \"Attempt $($attempt + 1)/$maxAttempts - Checking...\"; $h = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -TimeoutSec 3; Write-Host ''; Write-Host '[SUCCESS] Backend is RUNNING!'; Write-Host ''; Write-Host 'Checking courses...'; $courses = Invoke-RestMethod -Uri 'http://localhost:8080/api/courses' -Method GET -TimeoutSec 5; Write-Host 'Current courses:' $courses.Count; if ($courses.Count -lt 10) { Write-Host ''; Write-Host 'Initializing all domain courses (50+)...'; Write-Host 'This may take 30-60 seconds...'; $init = Invoke-RestMethod -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 90; Write-Host ''; Write-Host '[SUCCESS] Courses initialized:' $init.count; Start-Sleep -Seconds 2; $courses = Invoke-RestMethod -Uri 'http://localhost:8080/api/courses' -Method GET; Write-Host 'Total courses now available:' $courses.Count } else { Write-Host ''; Write-Host 'Courses already initialized!' } Write-Host ''; Write-Host '========================================'; Write-Host 'Backend is ready!'; Write-Host 'Refresh your browser now at:'; Write-Host 'http://localhost:3000'; Write-Host '========================================'; break } catch { $attempt++; if ($attempt -lt $maxAttempts) { Start-Sleep -Seconds 4 } } } if ($attempt -ge $maxAttempts) { Write-Host ''; Write-Host '[ERROR] Backend did not start in time.'; Write-Host 'Please check the Backend Server window for errors.'; Write-Host 'Wait a bit longer and refresh your browser.' }"

echo.
pause

