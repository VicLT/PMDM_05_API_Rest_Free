package edu.pract5.apirestfree.ui.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import edu.pract5.apirestfree.R
import edu.pract5.apirestfree.RoomApplication
import edu.pract5.apirestfree.data.LocalDataSource
import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.data.RemoteDataSource
import edu.pract5.apirestfree.databinding.ActivityMainBinding
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.ui.detail.DetailActivity
import edu.pract5.apirestfree.utils.checkConnection
import kotlinx.coroutines.launch

/**
 * Class MainActivity.kt
 * Displays API or local motorcycles and allows sorting them by model or displaying a random one.
 * @author Víctor Lamas
 *
 * @property binding Reference to the binding of the activity to access the views.
 * @property currentScrollPosition Current position of the RecyclerView.
 * @property currentDeletedScrollPosition Current position of the RecyclerView with deleted motorcycles.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var query: String? = null
    private var currentScrollPosition = 0
    private var currentDeletedScrollPosition = 0

    private val vm: MainViewModel by viewModels {
        val db = (application as RoomApplication).motorcyclesDB
        val localDataSource = LocalDataSource(db.motorcyclesDao())
        val remoteDataSource = RemoteDataSource()
        val repository = MotorcyclesRepository(remoteDataSource, localDataSource)
        MainViewModelFactory(repository)
    }

    /**
     * Adapter for displaying motorcycles in the RecyclerView.
     * It allows to show the detail of a motorcycle or delete it.
     * Allows you to undo the deletion of a motorcycle.
     */
    private val adapter = MotorcyclesAdapter(
        onClickDetail = { motorcycle ->
            DetailActivity.navigateToDetail(this@MainActivity, motorcycle)
        },
        onClickDelete = { motorcycle ->
            motorcycle.deleted = !motorcycle.deleted
            vm.updateLocalMotorcycle(motorcycle)

            if (!vm.areDeletedMotorcyclesSelected) {
                vm.deleteRemoteMotorcycle(motorcycle)
            } else {
                vm.addRemoteMotorcycle(motorcycle)
            }

            Snackbar.make(
                binding.root,
                String.format(getString(R.string.txt_deleted_motorcycle), motorcycle.model),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.txt_undo)) {
                motorcycle.deleted = !motorcycle.deleted
                vm.updateLocalMotorcycle(motorcycle)

                if (!vm.areDeletedMotorcyclesSelected) {
                    vm.addRemoteMotorcycle(motorcycle)
                } else {
                    vm.deleteRemoteMotorcycle(motorcycle)
                }
            }.show()
        }
    )

    /**
     * Initializes the UI and displays the motorcycles in the RecyclerView.
     *
     * @param savedInstanceState Activity status.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
     * Initializes the swipe refresh, the top menu and the bottom navigation.
     */
    override fun onStart() {
        super.onStart()

        /**
         * Search handling in the SearchView to update the list of motorcycles
         * according to the text entered.
         */
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                /**
                 * Called when the user submits the query.
                 *
                 * @param query The query text that is to be submitted.
                 * @return True if the query has been handled by the listener,
                 * false to let the SearchView perform the default action.
                 */
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                /**
                 * Called when the query text is changed by the user.
                 *
                 * @param search The new content of the query text field.
                 * @return False if the SearchView should perform the default
                 * action of showing any suggestions if available. True if the
                 * action was handled by the listener.
                 */
                override fun onQueryTextChange(search: String?): Boolean {
                    query = search?.trim()
                    if (checkConnection(this@MainActivity)) {
                        if (query.isNullOrEmpty()) {
                            vm.getRemoteMotorcycles(" ")
                        } else {
                            vm.getRemoteMotorcycles(query!!)
                        }
                    }
                    return true
                }
            }
        )

        /**
         * Refreshes the motorcycles from the API.
         */
        binding.swipeRefresh.setOnRefreshListener {
            if (checkConnection(this)) {
                vm.getRemoteMotorcycles()
            } else {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.txt_no_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }
            showNoItemsWarning()
        }

        /**
         * Sorts the motorcycles by model.
         * Shows a random motorcycle.
         */
        binding.mToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.opt_menu_about -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.txt_about_title))
                        .setMessage(getString(R.string.txt_about_description))
                        .setPositiveButton(getString(R.string.btn_alert_dialog), null)
                        .show()
                    true
                }
                R.id.opt_menu_sort_by_model -> {
                    vm.sortMotorcycles()
                    currentScrollPosition = 0
                    currentDeletedScrollPosition = 0
                    true
                }
                R.id.opt_menu_random_model -> {
                    showRandomMotorcycle(vm.getRandomMotorcycle())
                    true
                }
                else -> false
            }
        }

        /**
         * Bottom navigation to show all motorcycles or only the deleted ones.
         */
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.opt_all_motorcycles -> {
                    currentDeletedScrollPosition = saveScrollPosition()
                    vm.areDeletedMotorcyclesSelected = false
                    binding.swipeRefresh.isEnabled = true
                    true
                }
                R.id.opt_deleted_motorcycles -> {
                    currentScrollPosition = saveScrollPosition()
                    vm.areDeletedMotorcyclesSelected = true
                    binding.swipeRefresh.isEnabled = false
                    true
                }
                else -> false
            }
        }
    }

    /**
     * It gets all the motorcycles from the API, sorts and displays them in the RV.
     * Restores the last displayed position.
     *
     * @param returnToTop Back to the beginning of the RecyclerView.
     */
    private suspend fun drawAllMotorcycles(returnToTop: Boolean = false) {
        adapter.submitList(emptyList())

        if (checkConnection(this)) {
            binding.swipeRefresh.isRefreshing = true

            vm.motorcycles.collect { motorcycles ->
                adapter.submitList(motorcycles) {
                    if (returnToTop) {
                        binding.recyclerView.scrollToPosition(0)
                    } else if (vm.areDeletedMotorcyclesSelected) {
                        restoreScrollPosition(currentDeletedScrollPosition)
                    } else {
                        restoreScrollPosition(currentScrollPosition)
                    }

                    showNoItemsWarning()
                }

                binding.swipeRefresh.isRefreshing = false
            }
        } else {
            binding.swipeRefresh.isRefreshing = false
            Toast.makeText(
                this@MainActivity,
                getString(R.string.txt_no_connection),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Saves the current position of the RecyclerView.
     *
     * @return Current position of the RecyclerView.
     */
    private fun saveScrollPosition(): Int {
        val layoutManager = binding.recyclerView.layoutManager as? LinearLayoutManager
        return layoutManager?.findFirstVisibleItemPosition() ?: 0
    }

    /**
     * Restores the saved position in the RecyclerView.
     *
     * @param scrollPosition Saved position.
     */
    private fun restoreScrollPosition(scrollPosition: Int) {
        binding.recyclerView.post {
            val layoutManager = binding.recyclerView.layoutManager as? LinearLayoutManager
            layoutManager?.scrollToPositionWithOffset(scrollPosition, 0)
        }
    }

    /**
     * Displays a (random) motorcycle in a MaterialAlertDialog.
     *
     * @param motorcycle Motorcycle from the shown list.
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
                .setTitle(getString(R.string.txt_warning_title))
                .setMessage(getString(R.string.txt_warning_message))
                .setPositiveButton(getString(R.string.btn_alert_dialog), null)
                .show()
        }
    }

    /**
     * Shows a warning if there are no motorcycles in the list.
     */
    private fun showNoItemsWarning() {
        if (adapter.itemCount == 0) {
            binding.ivNoItems.visibility = View.VISIBLE
            binding.tvNoItems.visibility = View.VISIBLE
        } else {
            binding.ivNoItems.visibility = View.GONE
            binding.tvNoItems.visibility = View.GONE
        }
    }
}