@echo off
cd src

javac -encoding UTF-8 -d ..\bin -classpath ..\bin main\java\compositeNode\*.java
javac -encoding UTF-8 -d ..\bin -classpath ..\bin main\java\parser\*.java
javac -encoding UTF-8 -d ..\bin -classpath ..\bin main\java\factory\*.java
javac -encoding UTF-8 -d ..\bin -classpath ..\bin main\java\builder\*.java
javac -encoding UTF-8 -d ..\bin -classpath ..\bin main\java\client\*.java

cd ..
pause
@echo on
