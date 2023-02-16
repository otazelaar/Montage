package com.otaz.imdbmovieapp.interactors.movie

import com.otaz.imdbmovieapp.cache.AppDatabaseFake
import com.otaz.imdbmovieapp.cache.MovieDaoFake
import com.otaz.imdbmovieapp.cache.MovieSpecDaoFake
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.cache.model.MovieSpecEntityMapper
import com.otaz.imdbmovieapp.domain.model.MovieSpecs
import com.otaz.imdbmovieapp.interactors.movie_list.SearchMovies
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.data.MockWebServerResponses
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
import com.otaz.imdbmovieapp.network.model.MovieSpecDtoMapper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetMovieTest {
    private val appDatabase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val FAKE_APIKEY = "1234"
    private val FAKE_QUERY = "Iron Man"

    // system in test
    private lateinit var getMovie: GetMovie
    private val MOVIE_ID = "tt0371746"

    // Dependencies
    private lateinit var searchMovies: SearchMovies
    private lateinit var movieService: MovieService
    private lateinit var movieSpecDaoFake: MovieSpecDaoFake
    private lateinit var movieDaoFake: MovieDaoFake
    private val movieSpecDtoMapper = MovieSpecDtoMapper()
    private val movieDtoMapper = MovieDtoMapper()
    private val movieEntityMapper = MovieEntityMapper()


    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url(".")
        movieService = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)

        movieSpecDaoFake = MovieSpecDaoFake(appDatabase)
        movieDaoFake = MovieDaoFake(appDatabase)

        searchMovies = SearchMovies(
            movieDao = movieDaoFake,
            movieService = movieService,
            entityMapper = movieEntityMapper,
            dtoMapper = movieDtoMapper,
        )

        // Instantiate the system in test
        getMovie = GetMovie(
            movieService = movieService,
            dtoMapper = movieSpecDtoMapper,
        )
    }

    /**
     * 1. Get some recipes from the network and insert into cache
     * 2. Try to retrieve recipes by their specific recipe id
     */
    @Test
    fun getMoviesFromNetwork_getMovieById(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.movieListResponse)
        )

        // confirm the cache is empty to start
        assert(movieDaoFake.getAllMovies(1, 10).isEmpty())

        // get recipes from network and insert into cache
        searchMovies.execute(
            apikey = FAKE_APIKEY,
            query = FAKE_QUERY,
            page = 1,
            isNetworkAvailable = true,
        ).toList()

        // confirm the cache is no longer empty
        assert(movieDaoFake.getAllMovies(1, 10).isNotEmpty())

        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.movieWithIdtt0371746)
        )

        // run use case
        val movieAsFlow = getMovie.execute(
            apikey = FAKE_APIKEY,
            id = MOVIE_ID,
        ).toList()

        // first emission should be `loading`
        assert(movieAsFlow[0].loading)

        // second emission should be the movie
        val movie = movieAsFlow[1].data
        assert(movie?.id == MOVIE_ID)

        // confirm it is actually a MovieSpecs object
        assert(movie is MovieSpecs)

        // 'loading' should be false now
        assert(!movieAsFlow[1].loading)
    }


    /**
     * 1. Try to get a movie that does not exist in the cache
     * Result should be:
     * 1. MovieSpecs is retrieved from network and inserted into cache
     * 2. MovieSpecs is returned as flow from cache
     */
    @Test
    fun attemptGetNullMovieFromCache_getMovieById(): Unit = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.movieWithIdtt0371746)
        )

        // confirm the cache is empty to start
        movieSpecDaoFake.getMovieById(MOVIE_ID)?.id?.let { assert(it.isEmpty()) }

        // run use case
        val movieAsFlow = getMovie.execute(
            apikey = FAKE_APIKEY,
            id = MOVIE_ID,
        ).toList()

        // first emission should be `loading`
        assert(movieAsFlow[0].loading)

        // second emission should be the recipe
        val movie = movieAsFlow[1].data
        assert(movie?.id == MOVIE_ID)

        // confirm the recipe is in the cache now
        assert(movieSpecDaoFake.getMovieById(MOVIE_ID)?.id == MOVIE_ID)

        // confirm it is actually a Recipe object
        assert(movie is MovieSpecs)

        // 'loading' should be false now
        assert(!movieAsFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}