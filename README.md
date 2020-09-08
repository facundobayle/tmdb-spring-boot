# TMDB Spring Boot

Using Spring, the idea is to create an API that allows obtaining information from movies. 
For this we are going to use the API of [The Movie Database](https://developers.themoviedb.org/3/getting-started/introduction) to obtain the necessary data. 

The application has to:

* Allow search for movies
* Allow to obtain information about a film, with details of the main actors, director and writers, reviews, similar films, etc.
* Allow to build thematic lists.

## Run Server
To run with maven, just run the following command:

`$ mvn spring-boot:run`

To test it:

`$ curl localhost:8080`

If you are using Intellij IDEA, the Spring Boot plugin allows you to easily launch the application with a specific Run / Debug configuration.

## API Key
To use the TMDB API it is necessary to create an api_key. To create it go to https://developers.themoviedb.org/3/getting-started/authentication.

Based on [das-spring-boot](https://github.com/gamestoy/das-spring-boot) project.