@echo off
title Learning Management System - Backend Server
color 0A
echo.
echo ============================================
echo   LEARNING MANAGEMENT SYSTEM - BACKEND
echo ============================================
echo.
echo Starting backend server...
echo This will take 30-60 seconds on first run
echo.
echo Press Ctrl+C to stop the server
echo ============================================
echo.

cd /d "%~dp0Learning-Management-System-main\backend"

REM Set JAVA_HOME before any other commands
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo JAVA_HOME=%JAVA_HOME%
echo.
echo Java Version:
"%JAVA_HOME%\bin\java" -version
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java not found at %JAVA_HOME%
    echo Please check your Java installation.
    pause
    exit /b 1
)
echo.

echo Starting Spring Boot...
echo.
mvn spring-boot:run

echo.
echo Backend stopped.
pause

