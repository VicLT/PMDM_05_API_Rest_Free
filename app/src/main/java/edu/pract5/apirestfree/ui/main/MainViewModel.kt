package edu.pract5.apirestfree.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.pract5.apirestfree.domain.DeleteLocalMotorcycleUC
import edu.pract5.apirestfree.domain.GetLocalMotorcyclesUC
import edu.pract5.apirestfree.domain.GetRemoteMotorcyclesUC
import edu.pract5.apirestfree.domain.SaveLocalMotorcycleUC
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
import edu.pract5.apirestfree.utils.motorcyclesFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Class MainViewModel.kt
 * Manages operations and data status in the MainActivity UI.
 * @author Víctor Lamas
 *
 * @param getRemoteMotorcyclesUC Use case to get the motorcycles from the API.
 * @param getLocalMotorcyclesUC Use case to get the sorted favourite motorcycles from the local DB.
 * @param saveLocalMotorcycleUC Use case to update the favourite motorcycle in the local DB.
 */
class MainViewModel (
    private val getRemoteMotorcyclesUC: GetRemoteMotorcyclesUC,
    private val getLocalMotorcyclesUC: GetLocalMotorcyclesUC,
    private val saveLocalMotorcycleUC: SaveLocalMotorcycleUC,
    private val deleteLocalMotorcycleUC: DeleteLocalMotorcycleUC
) : ViewModel() {
    private var _localMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    val localMotorcycles: StateFlow<List<Motorcycle>>
        get() = _localMotorcycles.asStateFlow()

    //private var _remoteMotorcycles: List<Motorcycle> = emptyList()

    init {
        importRemoteMotorcyclesToLocalDb()
        importLocalMotorcycles()
    }

    private fun importRemoteMotorcyclesToLocalDb() {
        //_remoteMotorcycles = emptyList()

        viewModelScope.launch {
            /*getRemoteMotorcyclesUC.invoke().let {
                _remoteMotorcycles = it
            }*/

            getRemoteMotorcyclesUC.invoke().forEach { motorcycle ->
                saveLocalMotorcycle(motorcycle)
            }
        }
    }

    private fun importLocalMotorcycles() {
        _localMotorcycles.value = emptyList()

        viewModelScope.launch {
            getLocalMotorcyclesUC.invoke().collect {
                _localMotorcycles.value = it
            }
        }
    }

    fun resetMotorcycles() {
        deleteAllLocalMotorcycles()
        importRemoteMotorcyclesToLocalDb()
        importLocalMotorcycles()
    }

    private fun deleteAllLocalMotorcycles() {
        viewModelScope.launch {
            deleteLocalMotorcycleUC.invoke()
        }
    }

    fun saveLocalMotorcycle(motorcycle: Motorcycle) {
        viewModelScope.launch {
            saveLocalMotorcycleUC.invoke(motorcycle)
        }
    }

    fun deleteLocalMotorcycle(motorcycle: Motorcycle) {
        viewModelScope.launch {
            deleteLocalMotorcycleUC.invoke(motorcycle)
        }
    }

    /*private fun getAllMotorcycles() {
        viewModelScope.launch {
            combine(_apiMotorcycles, _favMotorcycles) { apiMotorcycles, favMotorcycles ->
                apiMotorcycles.map { apiMotorcycle ->
                    apiMotorcycle.apply {
                        favourite = favMotorcycles.any { favMotorcycle ->
                            favMotorcycle.make == apiMotorcycle.make
                            && favMotorcycle.model == apiMotorcycle.model
                        }
                    }
                }
            }.catch { exception ->
                Log.e("MainViewModel", exception.message.toString())
            }.collect { motorcycles ->
                _motorcycles.value = if (isFavouriteMotorcyclesSelected) {
                    sortByModel(motorcycles.filter { motorcycle
                        -> motorcycle.favourite
                    })
                } else {
                    sortByModel(motorcycles)
                }
            }
        }
    }*/

    private fun sortByModel(motorcycles: List<Motorcycle>): List<Motorcycle> {
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
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getMotorcyclesUC: GetRemoteMotorcyclesUC,
    private val getLocalMotorcyclesSortedByModelUC: GetLocalMotorcyclesUC,
    private val updateLocalMotorcycleUC: SaveLocalMotorcycleUC,
    private val deleteLocalMotorcycleUC: DeleteLocalMotorcycleUC
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getMotorcyclesUC,
            getLocalMotorcyclesSortedByModelUC,
            updateLocalMotorcycleUC,
            deleteLocalMotorcycleUC
        ) as T
    }
}