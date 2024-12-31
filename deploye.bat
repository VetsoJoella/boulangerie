@echo off

setlocal

set "webapps=D:\wildfly-26.1.2.Final\standalone\deployments\"
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
del /s /q "%temporaire%\*.*" > nul 2>&1
rmdir /s /q "%temporaire%" > nul 2>&1

rem Création du dossier temporaire
mkdir "%temporaire%" > nul 2>&1

rem Création des dossiers nécessaires
mkdir "%temporaire%\WEB-INF\lib" > nul 2>&1
mkdir "%temporaire%\assets" > nul 2>&1
mkdir "%bin%" > nul 2>&1

rem Copie du dossier lib, web, et xml
echo "Copie des dossiers ..."
copy "%index_page%" "%temporaire%" /Y > nul 2>&1
xcopy "%assets%" "%temporaire%\assets" /E /I /Y > nul 2>&1
xcopy "%lib%" "%temporaire%\WEB-INF\lib" /E /I /Y > nul 2>&1
xcopy "%web%" "%temporaire%\WEB-INF\pages\" /E /I /Y > nul 2>&1
copy "%xml%\*" "%temporaire%\WEB-INF\" /Y > nul 2>&1

rem Compilation des fichiers Java
echo "Compilation des fichiers ..."
for /R "%src%" %%f in (*.java) do (
    copy "%%f" "%bin%" > nul 2>&1
)

rem Compilation des fichiers Java
javac -parameters -cp "%lib%\*" -d "%temporaire%\WEB-INF\classes" %bin%\*.java 

rem Vérification de l'erreur de compilation
if errorlevel 1 (
    echo Erreur de compilation ! Arrêt du script.
    exit /b 1
)

rem Suppression des fichiers temporaires si aucune erreur n'est survenue
del /s /q "%bin%\*.*" > nul 2>&1

rem Archivage des fichiers
jar -cvf "%webapps%%nomProjet%.war" -C "%temporaire%" . > nul 2>&1

endlocal
