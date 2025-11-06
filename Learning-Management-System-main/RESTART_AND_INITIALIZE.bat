@echo off
title Learning Management System - Restart & Initialize
color 0B
cls
echo.
echo ===============================================
echo   LEARNING MANAGEMENT SYSTEM
echo   Restart Backend and Initialize Courses
echo ===============================================
echo.

echo Step 1: Stopping existing backend processes...
taskkill /F /IM java.exe 2>nul || echo "No Java processes to stop"
timeout /t 2 /nobreak >nul

echo.
echo Step 2: Starting backend...
cd /d %~dp0Learning-Management-System-main\backend
start "Backend Server" cmd /k "echo Starting Backend... && echo. && mvn spring-boot:run"

echo.
echo Step 3: Waiting for backend to start (60 seconds)...
timeout /t 60 /nobreak >nul

echo.
echo Step 4: Initializing courses...
powershell -Command "try { $body = @{} | ConvertTo-Json; $r = Invoke-WebRequest -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -Body $body -ContentType 'application/json' -TimeoutSec 10; $data = $r.Content | ConvertFrom-Json; Write-Host ''; Write-Host '========================================'; Write-Host '   COURSE INITIALIZATION RESULT'; Write-Host '========================================'; Write-Host 'Success:' $data.success; Write-Host 'Message:' $data.message; Write-Host 'Courses Count:' $data.count; Write-Host ''; Write-Host 'Courses initialized successfully!' } catch { Write-Host 'Error:' $_.Exception.Message; Write-Host 'Backend might still be starting...' }"

echo.
echo Step 5: Verifying courses...
timeout /t 3 /nobreak >nul
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/api/courses' -Method GET -TimeoutSec 3; $data = $r.Content | ConvertFrom-Json; Write-Host ''; Write-Host '========================================'; Write-Host '   VERIFICATION'; Write-Host '========================================'; Write-Host 'Total Courses:' $data.Count; if ($data.Count -gt 0) { Write-Host ''; Write-Host 'Sample Courses:'; $data[0..4] | ForEach-Object { Write-Host \"  - $($_.course_name)\" } } else { Write-Host 'No courses found' } } catch { Write-Host 'Error:' $_.Exception.Message }"

echo.
echo ===============================================
echo   SETUP COMPLETE
echo ===============================================
echo.
echo Next steps:
echo 1. Refresh your browser (Ctrl+F5)
echo 2. Go to: http://localhost:3000/courses
echo 3. You should see all domain courses!
echo.
pause

