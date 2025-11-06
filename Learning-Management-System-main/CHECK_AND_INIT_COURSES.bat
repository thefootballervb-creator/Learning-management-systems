@echo off
echo Checking courses and initializing if needed...
echo.

powershell -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8080/api/courses' -Method GET -TimeoutSec 5; $data = $r.Content | ConvertFrom-Json; Write-Host 'Backend is UP! Status:' $r.StatusCode; Write-Host 'Courses Count:' $data.Count; if ($data.Count -eq 0) { Write-Host ''; Write-Host 'Database is EMPTY - initializing courses...'; $initR = Invoke-WebRequest -Uri 'http://localhost:8080/api/admin/reinit-courses' -Method POST -ContentType 'application/json' -TimeoutSec 30; $initData = $initR.Content | ConvertFrom-Json; Write-Host 'Init Result:' $initData.success; Write-Host 'Courses initialized:' $initData.count } else { Write-Host ''; Write-Host 'Courses already exist in database!' } } catch { Write-Host 'ERROR: Backend not responding or not ready yet.'; Write-Host 'Please wait for backend to start, then run this script again.' }"

echo.
pause
