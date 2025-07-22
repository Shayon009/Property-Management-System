@echo off
REM Copy FXML files to build directory for NetBeans compatibility
echo Copying FXML files to build directory...

if not exist "build\classes\propertyassetmanager" mkdir "build\classes\propertyassetmanager"
copy /Y "src\propertyassetmanager\*.fxml" "build\classes\propertyassetmanager\" >nul 2>&1

if %ERRORLEVEL% EQU 0 (
    echo ✓ FXML files copied successfully
) else (
    echo ✗ Failed to copy FXML files
    exit /b 1
)
