#
# $Id: clock.bat 244 2006-01-16 21:33:22Z jsmith $
#
# Executes the Clock application with default settings
#

java -jar Time.jar

#
# Example of alternative configuration using command-line arguments:
#
# Executes the Clock application with optional width, height, delay, and
# reads alternative time zones and their respective aliases from a file
# in the current directory.  (You can provide any legitimate path
# specification.)
# Note that this invocation is commented out with a "#"!
#

# java -jar Time.jar 200 140 2000 SampleTimeZones.txt
