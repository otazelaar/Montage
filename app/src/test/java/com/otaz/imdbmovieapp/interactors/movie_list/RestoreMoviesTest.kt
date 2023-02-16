package com.otaz.imdbmovieapp.interactors.movie_list

import com.otaz.imdbmovieapp.cache.AppDatabaseFake
import com.otaz.imdbmovieapp.cache.MovieDaoFake
import com.otaz.imdbmovieapp.cache.model.MovieEntityMapper
import com.otaz.imdbmovieapp.domain.model.Movie
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.network.data.MockWebServerResponses
import com.otaz.imdbmovieapp.network.model.MovieDtoMapper
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

/**
 * The system in test is use case: [SearchMovies]
 *
 * The plan for testing mirrors that use case and is ordered as follows:
 * 1. Test that loading status is emitted
 * 2. Test if cache is empty to start
 * 3. Test if cache is no longer empty after executing use case
 *      a. if complete, this means the network operation is complete and all the queries are working properly
 * 4. Test that data, List<Movie>, is emitted as a flow from the cache to the UI
 * 5. Test that loading status is changed to false
 */

class RestoreMoviesTest {
    private lateinit var mockWebServer: MockWebServer
    private val appDatabase = AppDatabaseFake()
    private lateinit var baseUrl: HttpUrl
    private val FAKE_APIKEY = "1234"
    private val FAKE_QUERY = "Iron Man"


    // System in test
    private lateinit var restoreMovies: RestoreMovies

    // Dependencies
    private lateinit var searchMovies: SearchMovies
    private lateinit var movieService: MovieService
    private lateinit var movieDao: MovieDaoFake
    private val dtoMapper = MovieDtoMapper()
    private val entityMapper = MovieEntityMapper()


    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // maybe try "/" if "." doesn't work. technically mitch says we need to use whatever comes before the "." but the only text before that is the base url.
        baseUrl = mockWebServer.url(".")
        movieService = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)

        movieDao = MovieDaoFake(appDatabase)

        searchMovies = SearchMovies(
            movieDao = movieDao,
            movieService = movieService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper,
        )

        // Instantiate the system in test
        restoreMovies = RestoreMovies(
            movieDao = movieDao,
            entityMapper = entityMapper,
        )
    }

    /**
     * 1. Get Movies from the network and insert into cache
     * 2. Restore and show movies are retrieved from cache
     */

    @Test
    fun getMoviesFromNetwork_restoreFromCache(): Unit = runBlocking {
        // Condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.movieListResponse)
        )

        assert(movieDao.getAllMovies(1, 10).isEmpty())

        searchMovies.execute(
            apikey = FAKE_APIKEY,
            query = FAKE_QUERY,
            page = 1,
            isNetworkAvailable = true,
        ).toList()

        // Confirm the cache is no longer empty
        assert(movieDao.getAllMovies(1, 10).isNotEmpty())

        // Run our use case
        val flowItems = restoreMovies.execute(
            page = 1,
            query = FAKE_QUERY,
        ).toList()

        // First emission should be LOADING
        assert(flowItems[0].loading)

        // Second emission should be the list of movies
        // We are checking if the listed of movies emitted is greater than 0
        val movies = flowItems[1].data
        assert((movies?.size ?: 0) > 0)

        // Confirm they are actually Movie objects
        assert(movies?.get(index = 0) is Movie)

        // Confirm that loading is false
        assert(!flowItems[1].loading)

    }

    @AfterEach
    fun shutDown(){
        mockWebServer.shutdown()
    }
}






