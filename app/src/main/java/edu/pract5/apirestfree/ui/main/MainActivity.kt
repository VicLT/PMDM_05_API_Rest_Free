package edu.pract5.apirestfree.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.pract5.apirestfree.RoomApplication
import edu.pract5.apirestfree.data.LocalDataSource
import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.data.RemoteDataSource
import edu.pract5.apirestfree.databinding.ActivityMainBinding

/**
 * Class MainActivity.kt
 * Manages operations and data status in the UI.
 *
 * @author Víctor Lamas
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //private var currentScrollPosition = 0
    //private var currentFavScrollPosition = 0

    private val vm: MainViewModel by viewModels {
        val db = (application as RoomApplication).motorcyclesDB
        val localDataSource = LocalDataSource(db.motorcyclesDao())
        val remoteDataSource = RemoteDataSource()
        val repository = MotorcyclesRepository(remoteDataSource, localDataSource)
        MainViewModelFactory(repository)
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
    }
}