@echo off
echo ========================================
echo Starting Learning Management System Backend
echo ========================================
echo.

set JAVA_HOME=C:\Program Files\Java\jdk-21
echo JAVA_HOME set to: %JAVA_HOME%
echo.

cd /d "%~dp0Learning-Management-System-main\backend"
echo Current directory: %CD%
echo.

echo Checking if MySQL is running...
netstat -ano | findstr ":3306" >nul
if %errorlevel% equ 0 (
    echo MySQL is running ✓
) else (
    echo WARNING: MySQL might not be running!
    echo Please start MySQL first.
    pause
)

echo.
echo Starting Spring Boot application...
echo This may take 30-60 seconds...
echo.
echo Press Ctrl+C to stop the server
echo.

mvn spring-boot:run

pause

