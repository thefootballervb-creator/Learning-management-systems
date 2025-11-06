@echo off
echo ========================================
echo Initializing Courses
echo ========================================
echo.
echo Waiting for backend to be ready...
timeout /t 60 /nobreak >nul 2>&1

echo.
echo Checking backend and initializing courses...
powershell -Command "try { $h = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -TimeoutSec 5; Write-Host '[OK] Backend is running'; Write-Host ''; $c = Invoke-WebRequest -Uri 'http://localhost:8080/api/courses' -Method GET -TimeoutSec 5; $d = $c.Content | ConvertFrom-Json; Write-Host '[INFO] Current courses in database:' $d.Count; if ($d.Count -eq 0) { Write-Host ''; Write-Host 'Database is empty - Initializing courses...'; $i = Invoke-WebRequest -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 60; $id = $i.Content | ConvertFrom-Json; Write-Host ''; Write-Host '[SUCCESS] Courses initialized:' $id.count; Write-Host '[SUCCESS] Status:' $id.success; Write-Host ''; Write-Host 'Please refresh your browser to see the courses!' } else { Write-Host ''; Write-Host '[OK] Courses already exist in database!'; Write-Host 'If you still see "No courses found", please refresh your browser.' } } catch { Write-Host '[ERROR] Backend not responding yet.'; Write-Host 'Please wait a bit longer and run this script again, or check the backend window.' }"

echo.
echo ========================================
pause

