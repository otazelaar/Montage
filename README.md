# www.linkedin.com/in/owentazelaar
# Movie Database Application

- Search movies by film title

- Learn information such as:
  - Director, release date, runtime
  - IMDB Rating
  - Metacritic score
  - Plot overview
  - Reviews from TMDB API users (***SPOILER ALERT***)

- Use search presets to see lists of popular, upcoming, top rated movies

<img src="https://user-images.githubusercontent.com/98372611/221631977-e1e4fda2-9aad-4736-a5b3-2ae35a835e68.png" width="250" height="550"><img src="https://user-images.githubusercontent.com/98372611/221631989-bf245f37-a128-40f3-b1f8-86415b809ee0.png" width="250" height="550">

# Features
- Light & dark Mode
  - Mode matches your system preferences

# Architecture
- MVVM
- Use cases

# APIs
- Two different APIs were integrated using Retrofit

- TMDB API https://www.themoviedb.org/
  - Used to acquire database of movies for the MovieListScreen
  - Associated IMDB movie ID was used with the OMDB database to aquire other desired data

- OMDB API https://www.omdbapi.com/
  - Allowed access to IMDB and Metacritic ratings, plot overview, and other useful data displayed on the MovieDetailScreen

# Dependencies
- Kotlin Coroutines
- Jetpack Compose
- Dagger
- Hilt
- Retrofit
- Okhttp
- Glide
- Junit
- Navigation Component

# Attribution
<img src="https://www.themoviedb.org/assets/2/v4/logos/v2/blue_long_1-8ba2ac31f354005783fab473602c34c3f4fd207150182061e425d366e4f34596.svg" width="550" height="100">

- This product uses the TMDB API but is not endorsed or certified by TMDB
- This product is not endorsed by or affiliated by IMDb.com
- This product is not endorsed by or affiliated by OMDb API
- Great thanks to CodingWithMitch.com for his tutorial on a movie database application which has inspired
the overall architecture of this application

# Intention
- To gain real world experience in modern android app development
- This product is intended to be placed on the google play store and will not make any revenue

# www.linkedin.com/in/owentazelaar
