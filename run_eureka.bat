@echo off
echo ===========================================
echo  INICIANDO: MS-EUREKA (Puerto 8761)
echo ===========================================
cd ms_eureka
call mvn spring-boot:run
pause