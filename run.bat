@echo off
echo Starting ALOHCMUTE application...
echo.

rem Set JAVA_HOME to Java 21
set JAVA_HOME=C:\Program Files\Java\jdk-21

rem Set PATH to use Java 21
set PATH=%JAVA_HOME%\bin;%PATH%

rem Display Java version
echo Using Java version:
java -version
echo.

echo Building project...
mvn clean package -DskipTests
echo.

echo Project built successfully!
echo You can now deploy the WAR file to any Tomcat server.
echo.

echo WAR file location: target\alohcmute-0.0.1-SNAPSHOT.war
echo.

echo To deploy manually:
echo 1. Install Apache Tomcat 9 or 10
echo 2. Copy the WAR file to Tomcat's webapps folder
echo 3. Start Tomcat server
echo 4. Access: http://localhost:8080/alohcmute-0.0.1-SNAPSHOT
echo.

pause