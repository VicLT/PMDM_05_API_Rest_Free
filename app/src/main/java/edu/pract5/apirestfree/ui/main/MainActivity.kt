package edu.pract5.apirestfree.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import edu.pract5.apirestfree.R
import edu.pract5.apirestfree.RoomApplication
import edu.pract5.apirestfree.data.LocalDataSource
import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.data.RemoteDataSource
import edu.pract5.apirestfree.databinding.ActivityMainBinding
import edu.pract5.apirestfree.domain.GetMotorcyclesUseCase
import edu.pract5.apirestfree.domain.GetSortedFavMotorcyclesUseCase
import edu.pract5.apirestfree.domain.UpdateFavMotorcycleUseCase
import edu.pract5.apirestfree.ui.detail.DetailViewModel
import edu.pract5.apirestfree.utils.checkConnection
import kotlinx.coroutines.launch

/**
 * Class MainActivity.kt
 * Manages operations and data status in the UI.
 *
 * @author Víctor Lamas
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentScrollPosition = 0
    private var currentFavScrollPosition = 0

    private val vm: MainViewModel by viewModels {
        val db = (application as RoomApplication).motorcyclesDB
        val localDataSource = LocalDataSource(db.motorcyclesDao())
        val remoteDataSource = RemoteDataSource()
        val getMotorcyclesUseCase = GetMotorcyclesUseCase(
            MotorcyclesRepository(
                remoteDataSource,
                localDataSource
            )
        )
        val getSortedFavMotorcyclesUseCase = GetSortedFavMotorcyclesUseCase(
            MotorcyclesRepository(
                remoteDataSource,
                localDataSource
            )
        )
        val updateFavMotorcyclesUseCase = UpdateFavMotorcycleUseCase(
            MotorcyclesRepository(
                remoteDataSource,
                localDataSource
            )
        )
        MainViewModelFactory(
            getMotorcyclesUseCase,
            getSortedFavMotorcyclesUseCase,
            updateFavMotorcyclesUseCase
        )
    }

    private val adapter = MotorcyclesAdapter(
        onClick = { motorcycle ->
            //showWord(word)
        },
        onClickFav = { motorcycle ->
            motorcycle.favourite = !motorcycle.favourite
            //vm.updateWord(word)
        }
    )

    /**
     * Initializes the UI and displays the motorcycles in the RecyclerView.
     *
     * @param savedInstanceState Activity status.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                drawAllMotorcycles()
            }
        }
    }

    /**
     * It gets all the motorcycles from the API, sorts them and displays them in the VR.
     * Restores the last displayed position.
     *
     * @param returnToTop Back to the beginning of the RecyclerView.
     */
    private suspend fun drawAllMotorcycles(returnToTop: Boolean = false) {
        adapter.submitList(emptyList())

        if (checkConnection(this)) {
            //binding.swipeRefresh.isRefreshing = true

            vm.motorcycles.collect { motorcycle ->
                adapter.submitList(motorcycle) {
                    /*if (returnToTop) {
                        binding.recyclerView.scrollToPosition(0)
                    } else if (vm.isFavouriteMotorcyclesSelected) {
                        restoreScrollPosition(currentFavScrollPosition)
                    } else {
                        restoreScrollPosition(currentScrollPosition)
                    }*/
                }

                //binding.swipeRefresh.isRefreshing = false
            }
        } else {
            //binding.swipeRefresh.isRefreshing = false
            Toast.makeText(
                this@MainActivity,
                getString(R.string.txt_noConnection),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}