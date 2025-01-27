package edu.pract5.apirestfree.ui.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.pract5.apirestfree.R
import edu.pract5.apirestfree.databinding.ActivityDetailBinding
import edu.pract5.apirestfree.models.Motorcycle
import java.util.Locale

/**
 * Displays the details of a selected motorcycle.
 * @author Víctor Lamas
 *
 * @property binding Reference to the binding of the activity to access the views.
 */
@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    /**
     * Displays motorcycle details if passed through an Intent.
     *
     * @param savedInstanceState The saved instance state.
     */
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

        if (motorcycle != null) {
            setupDetailView(motorcycle)
        } else {
            Toast.makeText(
                this@DetailActivity,
                getString(R.string.txt_no_data),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val EXTRA_MOTORCYCLE = "motorcycle"
        fun navigateToDetail(activity: Activity, motorcycle: Motorcycle) {
            activity.startActivity(Intent(activity, DetailActivity::class.java).apply {
                putExtra(EXTRA_MOTORCYCLE, motorcycle)
            })
        }
    }

    /**
     * Sets up the detail view with the motorcycle data.
     *
     * @param motorcycle The motorcycle to display.
     */
    private fun setupDetailView(motorcycle: Motorcycle) {
        binding.tvHeight.text = if (motorcycle.totalHeight.isNullOrEmpty()) "No data" else motorcycle.totalHeight
        binding.tvWidth.text = if (motorcycle.totalWidth.isNullOrEmpty()) "No data" else motorcycle.totalWidth
        binding.tvMakeAndModel.text = String.format(
            getString(R.string.txt_make_and_model),
            motorcycle.make,
            motorcycle.model
        ).uppercase(Locale.getDefault())
        binding.tvYearData.text = motorcycle.year.ifEmpty { "-" }
        binding.tvTypeData.text = if (motorcycle.type.isNullOrEmpty()) "-" else motorcycle.type
        binding.tvDisplacementData.text = if (motorcycle.displacement.isNullOrEmpty()) "-" else motorcycle.displacement
        binding.tvPowerData.text = if (motorcycle.power.isNullOrEmpty()) "-" else motorcycle.power
        binding.tvTorqueData.text = if (motorcycle.torque.isNullOrEmpty()) "-" else motorcycle.torque
        binding.tvGearboxData.text = if (motorcycle.gearbox.isNullOrEmpty()) "-" else motorcycle.gearbox
        binding.tvFrontTireData.text = if (motorcycle.frontTire.isNullOrEmpty()) "-" else motorcycle.frontTire
        binding.tvRearTireData.text = if (motorcycle.rearTire.isNullOrEmpty()) "-" else motorcycle.rearTire
        binding.tvTotalWeightData.text = if (motorcycle.totalWeight.isNullOrEmpty()) "-" else motorcycle.totalWeight

        // Configure the Toolbar and enable the “back” button.
        setSupportActionBar(binding.mToolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.txt_back)

        // Open the web browser with the search for the motorcycle in Google Images.
        binding.tvMakeAndModel.setOnClickListener {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(
                        getString(R.string.url_google_images),
                        motorcycle.make,
                        motorcycle.model,
                        motorcycle.year
                    )
                )
            ).apply {
                if (this.resolveActivity(packageManager) != null) {
                    startActivity(this)
                } else {
                    Toast.makeText(
                        this@DetailActivity,
                        getString(R.string.txt_no_browser),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}