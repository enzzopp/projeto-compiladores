@echo off

cd ..



if not exist "Classes" (
    mkdir Classes
)


move *.class Classes\


echo Todos os arquivos .class foram movidos para a pasta "Classes".
pause
