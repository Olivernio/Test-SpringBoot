@echo off
echo ===========================================
echo  INICIANDO: MS-CLINICO (Puerto 8081)
echo ===========================================
cd ms_clinico
call mvn spring-boot:run
pause