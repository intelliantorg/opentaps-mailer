cd..
SET OFBIZ_HOME=%cd%
cd windows-service
javaservice.exe -install opentaps %JAVA_HOME%\jre\bin\server\jvm.dll -Xms256M -Xmx512M -Duser.language=en -Djava.class.path=%JAVA_HOME%\lib\tools.jar;%OFBIZ_HOME%\ofbiz.jar -start org.ofbiz.base.start.Start -out %OFBIZ_HOME%\runtime\logs\console.log -err %OFBIZ_HOME%\runtime\logs\serviceErr.txt -current %OFBIZ_HOME% -manual 
