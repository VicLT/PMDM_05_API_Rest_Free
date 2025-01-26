package edu.pract5.apirestfree.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.pract5.apirestfree.data.MotorcyclesRepository
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
import edu.pract5.apirestfree.utils.motorcyclesFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Class MainViewModel.kt
 * Manages operations and data status in the MainActivity UI.
 * @author Víctor Lamas
 *
 * @param repository It allows retrieving all motorcycles and their properties.
 */
class MainViewModel (private val repository: MotorcyclesRepository) : ViewModel() {
    /**
     * Indicates if the deleted motorcycles are selected or not in order to change de data source.
     */
    var areDeletedMotorcyclesSelected: Boolean = false
        set(value) {
            field = value
            _motorcycles.value = sortByMotorcyclesFilter(
                _localMotorcycles.value.takeIf { field } ?: _remoteMotorcycles.value
            )
        }

    private var _motorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    val motorcycles: StateFlow<List<Motorcycle>>
        get() = _motorcycles.asStateFlow()

    private var _remoteMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    private var _localMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())

    init {
        getLocalMotorcycles()
        getRemoteMotorcycles()
        mergeMotorcycles()
    }

    /**
     * Updates the list when a bike has been deleted.
     */
    fun deleteRemoteMotorcycle(motorcycle: Motorcycle) {
        if (motorcycle.deleted) {
            viewModelScope.launch {
                _remoteMotorcycles.value = _remoteMotorcycles.value.filter {
                    it != motorcycle
                }
            }
        }
    }

    /**
     * Updates the list when a motorcycle has returned to the list of undeleted motorcycles.
     */
    fun addRemoteMotorcycle(motorcycle: Motorcycle) {
        if (!motorcycle.deleted) {
            viewModelScope.launch {
                _remoteMotorcycles.value += motorcycle
            }
        }
    }

    /**
     * Updates the filter and sorts the list of combined motorcycles.
     */
    fun sortMotorcycles() {
        motorcyclesFilter =
            if (motorcyclesFilter == MotorcyclesFilter.ALPHA_ASC) {
                MotorcyclesFilter.ALPHA_DESC
            } else {
                MotorcyclesFilter.ALPHA_ASC
            }
        _motorcycles.value = sortByMotorcyclesFilter(_motorcycles.value)
    }

    /**
     * Inserts or deletes in the local DB a motorcycle marked as deleted.
     *
     * @param motorcycle Object with their properties.
     */
    fun updateLocalMotorcycle(motorcycle: Motorcycle) {
        viewModelScope.launch {
            repository.updateLocalMotorcycle(motorcycle)
        }
    }

    /**
     * Search in the list for a random motorcycle.
     *
     * @return Object motorcycle or null.
     */
    fun getRandomMotorcycle(): Motorcycle? =
        if (areDeletedMotorcyclesSelected) {
            _motorcycles.value.filter { motorcycle -> motorcycle.deleted }
        } else {
            _motorcycles.value
        }.randomOrNull()

    /**
     * Retrieves motorcycles from the API.
     */
    fun getRemoteMotorcycles() {
        _remoteMotorcycles.value = emptyList()

        viewModelScope.launch {
            repository.getRemoteMotorcycles().collect { remoteMotorcycles ->
                _remoteMotorcycles.value = remoteMotorcycles.filter { remoteMotorcycle ->
                    _localMotorcycles.value.none { localMotorcycle ->
                        localMotorcycle.model == remoteMotorcycle.model
                    }
                }
            }
        }
    }

    /**
     * Retrieves sorted motorcycles from the local DB.
     */
    private fun getLocalMotorcycles() {
        viewModelScope.launch {
            repository.getLocalMotorcyclesSortedByModel(motorcyclesFilter).collect { localMotorcycles ->
                _localMotorcycles.value = localMotorcycles.map { localMotorcycle ->
                    localMotorcycle.apply {
                        deleted = true
                    }
                }
            }
        }
    }

    /**
     * Combines API motorcycles with local motorcycles and sorts them.
     */
    private fun mergeMotorcycles() {
        viewModelScope.launch {
            combine(_remoteMotorcycles, _localMotorcycles) { remoteMotorcycles, localMotorcycles ->
                // Filter out remote motorcycles that are not on the local list.
                val filteredRemoteMotorcycles = remoteMotorcycles.filter { remoteMotorcycle ->
                    localMotorcycles.none { localMotorcycle ->
                        localMotorcycle.model == remoteMotorcycle.model
                    }
                }

                //  Mark as deleted the remote motorcycles that match the local ones.
                val combinedMotorcycles = filteredRemoteMotorcycles.map { remoteMotorcycle ->
                    remoteMotorcycle.apply {
                        deleted = localMotorcycles.any { localMotorcycle ->
                            localMotorcycle.model == remoteMotorcycle.model
                        }
                    }
                } + localMotorcycles // Combines local and filtered lists.

                combinedMotorcycles
            }.catch { exception ->
                Log.e("MainViewModel", exception.message.toString())
            }.collect { motorcycles ->
                // Filter and update according to whether motorcycles are deleted or not.
                _motorcycles.value = if (areDeletedMotorcyclesSelected) {
                    sortByMotorcyclesFilter(motorcycles.filter { motorcycle ->
                        motorcycle.deleted
                    })
                } else {
                    sortByMotorcyclesFilter(motorcycles.filter { motorcycle ->
                        !motorcycle.deleted
                    })
                }
            }
        }
    }

    /**
     * Sort the motorcycles in the combined list.
     *
     * @param motorcycles List of motorcycles.
     * @return List of sorted motorcycles.
     */
    private fun sortByMotorcyclesFilter(motorcycles: List<Motorcycle>): List<Motorcycle> {
        return when (motorcyclesFilter) {
            MotorcyclesFilter.ALPHA_ASC -> motorcycles.sortedBy { motorcycle ->
                motorcycle.model.uppercase()
            }

            MotorcyclesFilter.ALPHA_DESC -> motorcycles.sortedByDescending { motorcycle ->
                motorcycle.model.uppercase()
            }
        }
    }
}

/**
 * Clase MainViewModelFactory.kt
 * Creates an instance of MainViewModel.
 *
 * @param repository It allows retrieving all motorcycles and their properties.
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MotorcyclesRepository)
    : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}