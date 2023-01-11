package com.otaz.imdbmovieapp.presentation.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.otaz.imdbmovieapp.R
import com.otaz.imdbmovieapp.network.MovieService
import com.otaz.imdbmovieapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    val viewModel : MovieListViewModel by viewModels()

    @OptIn(ExperimentalUnitApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                // Anytime [val movies: MutableState<List<Movie>>] from [MovieListFragment] changes, this value below
                // [movies] will be updated here and in any composable that uses this value.
                val movies = viewModel.movies.value

                for (movie in movies){
                    Log.d(TAG, "MovieListFragment: ${movie.title}")
                }

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Movie List",
                        style = TextStyle(
                            fontSize = TextUnit(21F, TextUnitType.Sp)
                        )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            findNavController().navigate(R.id.viewMovie)
                        }
                    ) {
                        Text(text = "To Movie Fragment")

                    }
                }
            }
        }

    }
}