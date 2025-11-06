@echo off
title Backend Server - DO NOT CLOSE THIS WINDOW
color 0B
cls
echo.
echo ===============================================
echo   LEARNING MANAGEMENT SYSTEM - BACKEND
echo ===============================================
echo.
echo IMPORTANT: Keep this window open!
echo.
echo Starting backend server...
echo This will take 30-60 seconds...
echo.
echo ===============================================
echo.

REM Set JAVA_HOME explicitly
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Verify Java
echo Checking Java...
"%JAVA_HOME%\bin\java" -version
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java not found!
    echo Please install Java 21 at: C:\Program Files\Java\jdk-21
    pause
    exit /b 1
)
echo.

REM Navigate to backend directory
cd /d "%~dp0Learning-Management-System-main\backend"
echo Current directory: %CD%
echo.

REM Start Spring Boot
echo ===============================================
echo Starting Spring Boot Application...
echo ===============================================
echo.
echo Wait for: "Started LearningManagementSystemApplication"
echo.
echo Once you see that message, the backend is ready!
echo.
echo ===============================================
echo.

call mvn spring-boot:run

echo.
echo ===============================================
echo Backend has stopped.
echo ===============================================
pause

