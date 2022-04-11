echo off
cls
echo MercuryTrade build starting
echo mvn clean package
call mvn clean package
echo Copying MercuryTrade.jar from app/target to release_files
cd app/target
copy MercuryTrade.jar "../../release_files"
cd ../..
echo Launching launch4j.exe to generate MercuryTrade.exe from .jar file
cd launch4j
launch4jc.exe ../release_files/release_config.xml
cd ..
echo build_mercury_bat completed