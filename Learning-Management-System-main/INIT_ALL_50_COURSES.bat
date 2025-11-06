@echo off
echo ========================================
echo Initializing All 50+ Domain Courses
echo ========================================
echo.
echo Waiting for backend to be ready...
echo.

timeout /t 5 /nobreak >nul 2>&1

powershell -Command "$maxAttempts = 20; $attempt = 0; while ($attempt -lt $maxAttempts) { try { Write-Host \"Attempt $($attempt + 1)/$maxAttempts - Checking backend...\"; $h = Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -TimeoutSec 3; Write-Host '[OK] Backend is ready!'; Write-Host ''; Write-Host 'Initializing all 50+ domain courses...'; Write-Host 'This will delete existing courses and create 50+ new ones.'; Write-Host 'This may take 30-60 seconds...'; Write-Host ''; $init = Invoke-RestMethod -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 90; Write-Host ''; Write-Host '========================================'; Write-Host '[SUCCESS]'; Write-Host '========================================'; Write-Host 'Courses initialized:' $init.count; Write-Host 'Message:' $init.message; Write-Host ''; Write-Host 'Verifying courses...'; Start-Sleep -Seconds 2; $courses = Invoke-RestMethod -Uri 'http://localhost:8080/api/courses' -Method GET; Write-Host 'Total courses now available:' $courses.Count; Write-Host ''; Write-Host 'Sample courses:'; $courses | Select-Object -First 10 course_name, instructor, price | Format-Table -AutoSize; Write-Host '========================================'; Write-Host ''; Write-Host 'Done! Refresh your browser at http://localhost:3000/courses'; Write-Host 'to see all' $courses.Count 'courses!'; break } catch { $attempt++; if ($attempt -ge $maxAttempts) { Write-Host ''; Write-Host '[ERROR] Backend did not start in time.'; Write-Host 'Please make sure the backend is running on port 8080.'; Write-Host 'Wait a bit longer and run this script again.' } else { Start-Sleep -Seconds 4 } } }"

echo.
pause

