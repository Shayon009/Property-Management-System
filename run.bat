@echo off
echo ===============================================
echo    PROPERTY ASSET MANAGER - STARTING...
echo ===============================================
echo.

REM Create build directory if it doesn't exist
if not exist "build\classes\propertyassetmanager" mkdir "build\classes\propertyassetmanager"

REM Copy FXML files to build directory
echo [1/4] Copying FXML files...
copy /Y "src\propertyassetmanager\*.fxml" "build\classes\propertyassetmanager\" >nul 2>&1

REM Compile all Java files with JavaFX libraries
echo [2/4] Compiling Java files...
javac --module-path "lib\javafx" --add-modules javafx.controls,javafx.fxml -cp "lib\sqlite-jdbc-3.50.2.0.jar;lib\mysql-connector-j-8.4.0.jar" -d build\classes src\propertyassetmanager\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Compilation failed!
    echo Please check for syntax errors in Java files.
    pause
    exit /b 1
)

echo [3/4] Compilation successful!
echo [4/4] Starting Property Asset Manager...
echo.
echo ===============================================
echo    APPLICATION READY - Login Window Opening
echo ===============================================
echo.
echo LOGIN CREDENTIALS:
echo   Admin: admin / admin123 (Full Access)
echo   User:  user  / user123  (Read-Only)
echo.

REM Run the application with JavaFX libraries
java --module-path "lib\javafx" --add-modules javafx.controls,javafx.fxml -cp "build\classes;lib\sqlite-jdbc-3.50.2.0.jar;lib\mysql-connector-j-8.4.0.jar" propertyassetmanager.Main

echo.
echo Application closed.
pause
