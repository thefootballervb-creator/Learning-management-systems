@echo off
title Initialize Courses
color 0A
cls
echo.
echo ===============================================
echo   INITIALIZING ALL DOMAIN COURSES
echo ===============================================
echo.
echo Waiting for backend to be ready...
echo.

:wait
timeout /t 5 /nobreak >nul 2>&1
powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -TimeoutSec 2; Write-Host 'Backend is ready!' } catch { Write-Host 'Backend not ready yet, waiting...'; exit 1 }" >nul 2>&1
if %errorlevel% neq 0 goto wait

echo.
echo Backend is ready! Initializing courses...
echo.

powershell -Command "try { $body = @{} | ConvertTo-Json; $r = Invoke-WebRequest -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -Body $body -ContentType 'application/json' -TimeoutSec 30; $data = $r.Content | ConvertFrom-Json; Write-Host ''; Write-Host '========================================'; Write-Host '   INITIALIZATION RESULT'; Write-Host '========================================'; Write-Host 'Success:' $data.success; Write-Host 'Message:' $data.message; Write-Host 'Courses Created:' $data.count; Write-Host ''; if ($data.success) { Write-Host 'Courses initialized successfully!' } else { Write-Host 'Note:' $data.message } } catch { Write-Host 'Error:' $_.Exception.Message }"

echo.
echo Verifying courses...
timeout /t 2 /nobreak >nul

powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/api/courses' -Method GET -TimeoutSec 5; $data = $r.Content | ConvertFrom-Json; Write-Host ''; Write-Host '========================================'; Write-Host '   VERIFICATION'; Write-Host '========================================'; Write-Host 'Total Courses:' $data.Count; if ($data.Count -gt 0) { Write-Host ''; Write-Host 'Sample Courses:'; $data[0..9] | ForEach-Object { Write-Host \"  - $($_.course_name)\" } } else { Write-Host 'No courses found' } } catch { Write-Host 'Error:' $_.Exception.Message }"

echo.
echo ===============================================
echo   DONE!
echo ===============================================
echo.
echo Next: Refresh your browser (Ctrl+F5)
echo URL: http://localhost:3000/courses
echo.
pause

