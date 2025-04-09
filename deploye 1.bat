@echo off

setlocal

set "webapps=path\to\your\deployments\folder"
set "nomProjet=boulangerie"

set "assets=assets"
set "temporaire=temp"
set "src=src"
set "lib=lib"
set "web=pages"
set "xml=conf"
set "bin=bin"
set "index_page=test.html"

rem Suppression et Création du dossier temporaire
del /s /q "%temporaire%\*.*"
rmdir /s /q "%temporaire%" 

rem Création du dossier temporaire
mkdir "%temporaire%" 

rem Création des dossiers nécessaires
mkdir "%temporaire%\WEB-INF\lib"
mkdir "%temporaire%\assets"
mkdir "%bin%"

rem Copie du dossier lib, web, et xml
echo "Copie des dossiers ..."
copy "%index_page%" "%temporaire%" /Y 
xcopy "%assets%" "%temporaire%\assets" /E /I /Y 
xcopy "%lib%" "%temporaire%\WEB-INF\lib" /E /I /Y 
xcopy "%web%" "%temporaire%\WEB-INF\pages\" /E /I /Y 
copy "%xml%\*" "%temporaire%\WEB-INF\" /Y 

rem Compilation des fichiers Java
for /R "%src%" %%f in (*.java) do (
    copy "%%f" "%bin%"  /Y 
)
echo "Compilation des fichiers ..."
rem Compilation des fichiers Java
javac -parameters -cp "%lib%\*" -d "%temporaire%\WEB-INF\classes" %bin%\*.java 

rem Vérification de l'erreur de compilation
if errorlevel 1 (
    echo Erreur de compilation ! Arrêt du script.
    exit /b 1
)

rem Suppression des fichiers temporaires si aucune erreur n'est survenue
del /s /q "%bin%\*.*"  /E /Y /Q
echo "Archivage des fichiers ..."
rem Archivage des fichiers
jar -cvf "%webapps%%nomProjet%.war" -C "%temporaire%" . 
echo "Finalisation"
@REM endlocal
