@echo off
echo ===========================================
echo  INICIANDO: MS-AUDITORIA (Puerto 8083)
echo ===========================================
cd ms_auditoria
call mvn spring-boot:run
echo ===========================================
echo  SI DA ERROR, EJECUTE "run_auditoria-en-target.bat"
echo ===========================================
pause