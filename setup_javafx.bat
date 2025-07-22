@echo off
echo Downloading JavaFX SDK 21...

REM Create temp directory
if not exist "temp" mkdir temp

echo Downloading JavaFX SDK...
curl -L -o "temp\openjfx-21_windows-x64_bin-sdk.zip" "https://download2.gluonhq.com/openjfx/21/openjfx-21_windows-x64_bin-sdk.zip"

if exist "temp\openjfx-21_windows-x64_bin-sdk.zip" (
    echo Extracting JavaFX SDK...
    powershell -command "Expand-Archive -Path 'temp\openjfx-21_windows-x64_bin-sdk.zip' -DestinationPath 'temp' -Force"
    
    echo Copying JavaFX libraries...
    if not exist "lib\javafx" mkdir lib\javafx
    xcopy "temp\javafx-sdk-21\lib\*.jar" "lib\javafx\" /Y
    
    echo Cleaning up...
    rmdir /s /q temp
    
    echo JavaFX SDK setup complete!
) else (
    echo Failed to download JavaFX SDK
)

pause
