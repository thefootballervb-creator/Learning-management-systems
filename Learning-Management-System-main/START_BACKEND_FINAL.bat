@echo off
echo ========================================
echo Starting Backend Server
echo ========================================
echo.
echo JAVA_HOME is set correctly: %JAVA_HOME%
echo.
cd backend
set JAVA_HOME=C:\Program Files\Java\jdk-21
echo Starting Spring Boot backend...
echo.
start "Backend Server" cmd /k "mvn spring-boot:run"
echo.
echo Backend is starting in a new window.
echo Please wait 60 seconds for it to fully start.
echo.
echo Once you see "Started LearningManagementSystemApplication" in the Backend Server window,
echo refresh your browser at http://localhost:3000
echo.
pause

