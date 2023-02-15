@echo off

gradlew examples:dist && java -jar examples\dist\examples.jar