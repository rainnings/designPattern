@echo off
cd src
javadoc -encoding UTF-8 -private -author -version -d ..\doc -classpath ..\bin main\java\compositeNode\*.java
javadoc -encoding UTF-8 -private -author -version -d ..\doc -classpath ..\bin main\java\parser\*.java
javadoc -encoding UTF-8 -private -author -version -d ..\doc -classpath ..\bin main\java\factory\*.java
javadoc -encoding UTF-8 -private -author -version -d ..\doc -classpath ..\bin main\java\builder\*.java
javadoc -encoding UTF-8 -private -author -version -d ..\doc -classpath ..\bin main\java\client\*.java

cd ..
pause
@echo on