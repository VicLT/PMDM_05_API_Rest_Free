package edu.pract5.apirestfree.ui.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import edu.pract5.apirestfree.R
import edu.pract5.apirestfree.databinding.ActivityDetailBinding
import edu.pract5.apirestfree.models.Motorcycle

@Suppress("DEPRECATION")
class DetailMotorcycleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val TAG = DetailMotorcycleActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
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

        val motorcycle =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_MOTORCYCLE, Motorcycle::class.java)
            } else {
                intent.getParcelableExtra(EXTRA_MOTORCYCLE)
            }

        Log.d(TAG, "onCreate: $motorcycle")

        if (motorcycle != null) {
            setupDetailView(motorcycle)
        } else {
            Toast.makeText(
                this@DetailMotorcycleActivity,
                "No city data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val EXTRA_MOTORCYCLE = "motorcycle"
        fun navigateToDetail(activity: Activity, motorcycle: Motorcycle) {
            activity.startActivity(Intent(activity, DetailMotorcycleActivity::class.java).apply {
                putExtra(EXTRA_MOTORCYCLE, motorcycle)
            })
        }
    }

    private fun setupDetailView(motorcycle: Motorcycle) {
        binding.tvMakeAndModel.text = String.format(getString(
            R.string.txt_make_and_model
        ),
            motorcycle.make,
            motorcycle.model
        )



        // Configura la Toolbar y habilita el botón de "volver"
        setSupportActionBar(binding.mToolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = R.string.txt_make_and_model.toString()
    }
}