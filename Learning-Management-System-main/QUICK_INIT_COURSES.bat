@echo off
echo Initializing all courses...
echo.
echo Please wait for backend to be ready, then this will initialize 50+ courses.
echo.
timeout /t 5 /nobreak >nul 2>&1

powershell -Command "$maxAttempts = 20; $attempt = 0; while ($attempt -lt $maxAttempts) { try { Write-Host \"Attempt $($attempt + 1)/$maxAttempts - Checking backend...\"; $h = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -TimeoutSec 2; Write-Host 'Backend is ready!'; Write-Host 'Initializing courses (this may take 30-60 seconds)...'; $init = Invoke-RestMethod -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 90; Write-Host ''; Write-Host '========================================'; Write-Host 'SUCCESS!'; Write-Host '========================================'; Write-Host 'Courses initialized:' $init.count; Write-Host 'Message:' $init.message; Write-Host ''; Write-Host 'Verifying...'; Start-Sleep -Seconds 2; $courses = Invoke-RestMethod -Uri 'http://localhost:8080/api/courses' -Method GET; Write-Host 'Total courses now available:' $courses.Count; Write-Host ''; Write-Host 'Done! Please refresh your browser to see all courses.'; break } catch { $attempt++; if ($attempt -ge $maxAttempts) { Write-Host ''; Write-Host 'Backend not responding. Make sure it is running on port 8080.'; Write-Host 'You can start it by running: START_BACKEND_NOW.bat'; } else { Start-Sleep -Seconds 3 } } }"

echo.
pause

