@echo off
REM News Project Application Manager for Windows

:menu
cls
echo.
echo ====================================
echo   NEWS AGGREGATOR - APPLICATION MENU
echo ====================================
echo.
echo 1. Clean and Build Project
echo 2. Run Application
echo 3. View Database
echo 4. Stop Application
echo 5. Clean Database
echo 6. Exit
echo.
set /p choice="Enter your choice (1-6): "

if "%choice%"=="1" goto build
if "%choice%"=="2" goto run
if "%choice%"=="3" goto database
if "%choice%"=="4" goto stop
if "%choice%"=="5" goto clean_db
if "%choice%"=="6" goto exit
goto menu

:build
echo.
echo [*] Building project...
call mvn clean install -q
echo [✓] Build completed!
pause
goto menu

:run
echo.
echo [*] Starting application...
echo [*] Access at: http://localhost:8080
echo [*] Press Ctrl+C to stop the application
call mvn spring-boot:run
pause
goto menu

:database
echo.
echo [*] Opening MySQL...
mysql -u root -p
pause
goto menu

:clean_db
echo.
echo [!] This will delete all news from the database
set /p confirm="Are you sure? (yes/no): "
if /i "%confirm%"=="yes" (
    mysql -u root -puvmysql@2006 -e "USE uvbase; TRUNCATE TABLE news;"
    echo [✓] Database cleaned!
) else (
    echo [*] Cancelled
)
pause
goto menu

:exit
exit /b 0
