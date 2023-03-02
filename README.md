# www.linkedin.com/in/owentazelaar
# Movie Database App

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
  - Used to aquire database of movies for the MovieListScreen
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

# Intention
- To gain real world experience in modern android app development

# www.linkedin.com/in/owentazelaar
