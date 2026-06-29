@rem Gradle startup script for Windows
@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%\bin;%PATH%
set GRADLE_HOME=C:\Users\windows\Gradle\gradle-8.6
set CLASSPATH=C:\Users\windows\Gradle\gradle-8.6\lib\gradle-launcher-8.6.jar
"%JAVA_HOME%\bin\java.exe" -Xmx2048m -Xms512m -Dorg.gradle.appname=gradlew -classpath "%CLASSPATH%" org.gradle.launcher.GradleMain %*
