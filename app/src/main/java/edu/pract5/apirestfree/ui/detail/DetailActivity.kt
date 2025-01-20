package edu.pract5.apirestfree.ui.detail

import android.app.Activity
import android.content.Intent
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
import edu.pract5.apirestfree.ui.main.MainViewModel
import edu.pract5.apirestfree.ui.main.MainViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val vm: DetailViewModel by viewModels {
        val db = (application as RoomApplication).motorcyclesDB
        val localDataSource = LocalDataSource(db.motorcyclesDao())
        val remoteDataSource = RemoteDataSource()
        val repository = MotorcyclesRepository(remoteDataSource, localDataSource)
        MainViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    }
}