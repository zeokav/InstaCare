set DIR=CommunicationService
if "%1"=="user" set DIR=UserService

set IMG=communication-service
if "%1"=="user" set IMG=user-service

echo "Building jar"
cd "..\%DIR%"
@Call .\gradlew.bat clean build -xtest

echo "Updating image"
cd ..\Deployment
@Call docker build -f Dockerfile.service "..\%DIR%" -t %IMG%