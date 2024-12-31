@echo off 

setlocal

set "src=src" 
set "lib=lib" 
set "bin=bin" 
set "mainClass=com.main.Test"

mkdir "%bin%"

rem Compilation des fichiers Java
for /R "%src%" %%f in (*.java) do (
    copy "%%f" "%bin%"
    @REM echo "%%f"
)

rem Compilation des fichiers Java
javac -cp "%lib%\*" -parameters -d %bin% %bin%\*.java

if errorlevel 1 (
    echo Erreur de compilation ! Arrêt du script.
    exit /b 1
)
rem Suppression des fichiers temporaires
del /s /q "%bin%\*.java" 


rem Exécution de la classe principale
@REM if exist "%mainClass:.=\%.class" (
    java -cp "%bin%;%lib%\*" %mainClass%
@REM ) else (
@REM     echo Classe principale non trouvée !
@REM     exit /b 1
@REM )
del /s /q "%bin%\*.class" 
endlocal

