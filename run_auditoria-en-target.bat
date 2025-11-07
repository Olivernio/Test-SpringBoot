@echo off
echo ===========================================
echo  INICIANDO: MS-AUDITORIA (Puerto 8083)
echo ===========================================
cd ms_auditoria
cd target
call java -jar ms_auditoria-0.0.1-SNAPSHOT.jar
pause