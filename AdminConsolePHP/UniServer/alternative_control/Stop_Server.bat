@echo off
cls
COLOR B0
mode con:cols=65 lines=20
TITLE UNIFORM SERVER - Stop

rem ###################################################
rem # Name: Stop_Server.bat
rem # Created By: The Uniform Server Development Team
rem # Edited Last By: Mike Gleaves (ric)
rem # V 1.0 28-6-2009
rem ##################################################

rem ### working directory current folder 
pushd %~dp0

..\usr\local\php\php.exe -n  ..\unicon\main\stop_servers.php

rem ..\unicon\program\unidelay.exe 3
pause

rem ### restore original working directory
popd

