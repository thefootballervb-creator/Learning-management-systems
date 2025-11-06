@echo off
echo ========================================
echo Initializing All Domain Courses
echo ========================================
echo.

powershell -Command "try { Write-Host 'Calling reinit-courses endpoint...'; $response = Invoke-RestMethod -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 60; Write-Host ''; Write-Host 'SUCCESS!'; Write-Host 'Courses initialized:' $response.count; Write-Host 'Message:' $response.message; Write-Host ''; Write-Host 'Verifying courses...'; Start-Sleep -Seconds 2; $courses = Invoke-RestMethod -Uri 'http://localhost:8080/api/courses' -Method GET; Write-Host 'Total courses now available:' $courses.Count; Write-Host ''; Write-Host 'Done! Refresh your browser to see all courses.' } catch { Write-Host 'ERROR:' $_.Exception.Message; Write-Host ''; Write-Host 'Make sure the backend is running on port 8080' }"

echo.
pause

