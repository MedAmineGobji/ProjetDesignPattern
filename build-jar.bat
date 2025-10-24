@echo off
echo ===================================
echo   Build Space Invaders JAR
echo ===================================
echo.

echo [1/3] Nettoyage du projet...
call mvn clean

echo.
echo [2/3] Compilation et packaging...
call mvn package

echo.
echo [3/3] JAR cree dans le dossier target/
echo Fichier: space-invaders-patterns-1.0.0-shaded.jar
echo.

echo Pour lancer le JAR:
echo java -jar target/space-invaders-patterns-1.0.0-shaded.jar
echo.

pause