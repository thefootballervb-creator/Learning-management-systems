@echo off
echo ========================================
echo Starting Backend and Frontend
echo ========================================
echo.

REM Start Backend
echo Starting Backend Server...
cd Learning-Management-System-main\backend
set JAVA_HOME=C:\Program Files\Java\jdk-21
start "Backend Server" cmd /k "mvn spring-boot:run"
cd ..\..

REM Start Frontend
echo Starting Frontend Server...
cd Learning-Management-System-main\frontend
start "Frontend Server" cmd /k "npm start"
cd ..\..

echo.
echo Waiting 60 seconds for services to start...
timeout /t 60 /nobreak >nul 2>&1

echo.
echo Checking services and initializing courses...
powershell -Command "try { $b = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -TimeoutSec 3; Write-Host 'Backend: READY'; $c = Invoke-WebRequest -Uri 'http://localhost:8080/api/courses' -TimeoutSec 3; $d = $c.Content | ConvertFrom-Json; Write-Host 'Courses:' $d.Count; if ($d.Count -eq 0) { Write-Host 'Initializing courses...'; $i = Invoke-WebRequest -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 30; $id = $i.Content | ConvertFrom-Json; Write-Host 'Courses initialized:' $id.count } } catch { Write-Host 'Backend: Still starting...' }; try { $f = Invoke-WebRequest -Uri 'http://localhost:3000' -TimeoutSec 3; Write-Host 'Frontend: READY' } catch { Write-Host 'Frontend: Still starting...' }"

echo.
echo Opening browser...
timeout /t 3 /nobreak >nul 2>&1
start http://localhost:3000

echo.
echo ========================================
echo Done! Browser should open automatically
echo ========================================
echo.
pause

