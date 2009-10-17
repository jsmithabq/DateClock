REM
REM $Id: clock.bat 244 2006-01-16 21:33:22Z jsmith $
REM
REM Executes the Clock application with default settings
REM

java -jar Time.jar

REM
REM Example of alternative configuration using command-line arguments:
REM
REM Executes the Clock application with optional width, height, delay, and
REM reads alternative time zones and their respective aliases from a file
REM in the current directory.  (You can provide any legitimate path
REM specification.)
REM Note that this invocation is commented out with a "REM"!
REM

REM java -jar Time.jar 200 140 2000 SampleTimeZones.txt
