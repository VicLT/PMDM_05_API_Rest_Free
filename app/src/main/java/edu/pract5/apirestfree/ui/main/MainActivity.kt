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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.pract5.apirestfree.R
import edu.pract5.apirestfree.RoomApplication
import edu.pract5.apirestfree.data.LocalDataSource
import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.data.RemoteDataSource
import edu.pract5.apirestfree.databinding.ActivityMainBinding
import edu.pract5.apirestfree.domain.GetMotorcyclesUseCase
import edu.pract5.apirestfree.domain.GetSortedFavMotorcyclesUseCase
import edu.pract5.apirestfree.domain.UpdateFavMotorcycleUseCase
import edu.pract5.apirestfree.models.Motorcycle
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
            showRandomMotorcycle(motorcycle)
        },
        onClickFav = { motorcycle ->
            motorcycle.favourite = !motorcycle.favourite
            vm.updateMotorcycle(motorcycle)
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

    override fun onStart() {
        super.onStart()

        binding.swipeRefresh.setOnRefreshListener {
            if (checkConnection(this)) {
                vm.getApiMotorcycles()
            } else {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.txt_noConnection),
                    Toast.LENGTH_SHORT
                ).show()
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
            binding.swipeRefresh.isRefreshing = true

            vm.motorcycles.collect { motorcycle ->
                adapter.submitList(motorcycle) {
                    if (returnToTop) {
                        binding.recyclerView.scrollToPosition(0)
                    } else if (vm.isFavouriteMotorcyclesSelected) {
                        restoreScrollPosition(currentFavScrollPosition)
                    } else {
                        restoreScrollPosition(currentScrollPosition)
                    }
                }

                binding.swipeRefresh.isRefreshing = false
            }
        } else {
            binding.swipeRefresh.isRefreshing = false
            Toast.makeText(
                this@MainActivity,
                getString(R.string.txt_noConnection),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Guarda la posición actual del RecyclerView.
     *
     * @return Posición actual del RecyclerView.
     */
    /*private fun saveScrollPosition(): Int {
        val layoutManager = binding.recyclerView.layoutManager as? LinearLayoutManager
        return layoutManager?.findFirstVisibleItemPosition() ?: 0
    }*/

    /**
     * Restaura la posición guardada en el RecyclerView.
     *
     * @param scrollPosition Posición guardada.
     */
    private fun restoreScrollPosition(scrollPosition: Int) {
        binding.recyclerView.post {
            val layoutManager = binding.recyclerView.layoutManager as? LinearLayoutManager
            layoutManager?.scrollToPositionWithOffset(scrollPosition, 0)
        }
    }

    /**
     * Muestra una motocicleta (aleatoria) en un MaterialAlertDialog.
     *
     * @param motorcycle Motocicleta de la lista mostrada.
     */
    private fun showRandomMotorcycle(motorcycle: Motorcycle?) {
        if (motorcycle != null) {
            MaterialAlertDialogBuilder(this)
                .setTitle(motorcycle.make)
                .setMessage(motorcycle.model)
                .setPositiveButton(getString(R.string.btn_alert_dialog), null)
                .show()
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.warning_title))
                .setMessage(getString(R.string.warning_message))
                .setPositiveButton(getString(R.string.btn_alert_dialog), null)
                .show()
        }
    }
}