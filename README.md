# spring-spotify-explorer

## Project Architecture
This project is following hexagonal architecture. 
Check details information here:
https://reflectoring.io/spring-hexagonal/

## Project overview
Create a songs playlist by requesting songs through Spotify API (https://developer.spotify.com/documentation/web-api/reference/#category-search) passing the song naming.

## Endpoints
POST: /track
201 (Created in our DB without returning anything at this moment)
404 Not found

## Endpoint used from Spotify
AUTHENTICATION:
https://developer.spotify.com/documentation/general/guides/authorization-guide/#client-credentials-flow