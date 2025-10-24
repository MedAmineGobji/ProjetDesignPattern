@echo off
echo ===================================
echo   Space Invaders - Design Patterns
echo ===================================
echo.

echo [1/4] Nettoyage du projet...
call mvn clean

echo.
echo [2/4] Compilation du projet...
call mvn compile

echo.
echo [3/4] Execution des tests...
call mvn test

echo.
echo [4/4] Lancement du jeu...
call mvn javafx:run

echo.
echo Projet termine!
pause