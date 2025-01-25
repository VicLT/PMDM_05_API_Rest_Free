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

    /*fun deleteRemoteMotorcycle(motorcycle: Motorcycle) {
        if (motorcycle.deleted) {
            viewModelScope.launch {
                _remoteMotorcycles.value = _remoteMotorcycles.value.filter {
                    it != motorcycle
                }
            }
        }
    }

    fun addRemoteMotorcycle(motorcycle: Motorcycle) {
        if (!motorcycle.deleted) {
            viewModelScope.launch {
                _remoteMotorcycles.value += motorcycle
            }
        }
    }*/

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
     * Insert or delete a deleted motorcycle in the local DB.
     *
     * @param motorcycle Object wit their properties.
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
     * Retrieves the API motorcycles.
     */
    fun getRemoteMotorcycles() {
        _remoteMotorcycles.value = emptyList()

        viewModelScope.launch {
            /*repository.getRemoteMotorcycles().collect {
                _remoteMotorcycles.value = it
            }*/
            repository.getRemoteMotorcycles().collect { remoteMotorcycles ->
                _remoteMotorcycles.value = remoteMotorcycles.filter { remoteMotorcycle ->
                    _localMotorcycles.value.none { deletedMotorcycle ->
                        deletedMotorcycle.model == remoteMotorcycle.model
                    }
                }
            }
        }
    }

    /**
     * Retrieves sorted deleted motorcycles from the local DB.
     */
    private fun getLocalMotorcycles() {
        viewModelScope.launch {
            repository.getLocalMotorcyclesSortedByModel(filter = motorcyclesFilter).collect { localMotorcycles ->
                _localMotorcycles.value = localMotorcycles.map { localMotorcycle ->
                    localMotorcycle.deleted = true
                    localMotorcycle
                }
            }
        }
    }

    /**
     * Combines API motorcycles with deleted motorcycles and sorts them.
     */
    private fun mergeMotorcycles() {
        viewModelScope.launch {
            combine(_remoteMotorcycles, _localMotorcycles) { remoteMotorcycles, localMotorcycles ->
                /*remoteMotorcycles.map { remoteMotorcycle ->
                    remoteMotorcycle.apply {
                        deleted = localMotorcycles.any { deletedMotorcycle ->
                            deletedMotorcycle.model == remoteMotorcycle.model
                        }
                    }
                }*/

                // Filtrar remoteMotorcycles para excluir motocicletas eliminadas
                val filteredRemoteMotorcycles = remoteMotorcycles.filter { remoteMotorcycle ->
                    localMotorcycles.none { localMotorcycle ->
                        localMotorcycle.model == remoteMotorcycle.model
                    }
                }

                // Combinar listas y marcar como eliminadas si corresponde
                filteredRemoteMotorcycles.map { remoteMotorcycle ->
                    remoteMotorcycle.apply {
                        deleted = localMotorcycles.any { localMotorcycle ->
                            localMotorcycle.model == remoteMotorcycle.model
                        }
                    }
                }
            }.catch { exception ->
                Log.e("MainViewModel", exception.message.toString())
            }.collect { motorcycles ->
                _motorcycles.value = if (areDeletedMotorcyclesSelected) {
                    sortByMotorcyclesFilter(motorcycles.filter { motorcycle ->
                        motorcycle.deleted
                    })
                } else {
                    sortByMotorcyclesFilter(motorcycles)
                    /*sortByMotorcyclesFilter(motorcycles.filter { motorcycle ->
                        !motorcycle.deleted
                    })*/
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