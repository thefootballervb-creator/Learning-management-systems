@echo off
title Learning Management System - Starting All Services
color 0A
cls
echo.
echo ===============================================
echo   LEARNING MANAGEMENT SYSTEM
echo   Starting Frontend and Backend
echo ===============================================
echo.

REM Check if frontend is running
netstat -ano | findstr ":3000" >nul
if %errorlevel% equ 0 (
    echo Frontend: Already running on port 3000
) else (
    echo Frontend: Starting...
    start "Frontend Server" cmd /k "cd /d %~dp0Learning-Management-System-main\frontend && npm start"
    echo Frontend started in new window
)

timeout /t 2 /nobreak >nul

REM Check if backend is running
netstat -ano | findstr ":8080" >nul
if %errorlevel% equ 0 (
    echo Backend: Already running on port 8080
) else (
    echo Backend: Starting...
    start "Backend Server" cmd /k "cd /d %~dp0Learning-Management-System-main\backend && mvn spring-boot:run"
    echo Backend started in new window
)

echo.
echo ===============================================
echo   SERVICES STARTING
echo ===============================================
echo.
echo Frontend: http://localhost:3000
echo Backend:  http://localhost:8080
echo.
echo IMPORTANT:
echo - Two new windows will open
echo - Keep BOTH windows open
echo - Wait 30-60 seconds for services to start
echo - Frontend takes ~10 seconds
echo - Backend takes ~30-60 seconds
echo.
echo Once started, open:
echo   http://localhost:3000
echo.
echo ===============================================
echo.
pause

