### Zoom API

## Configuration
Set client_id, client_secret, and port (default 4001) in the bot.ini file in src/main/resources for OAuth.
Browser path is not required, since the application opens the default browser for authentication.

## Building the project

Project requires Java JDK 11.
To build the project run:
```
./gradlew build
```

## Running
```
ngrok start --none
./gradlew run
```
