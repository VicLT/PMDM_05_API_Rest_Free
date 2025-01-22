package edu.pract5.apirestfree.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.pract5.apirestfree.domain.DeleteLocalMotorcycleUC
import edu.pract5.apirestfree.domain.GetRemoteMotorcyclesUC
import edu.pract5.apirestfree.domain.GetLocalMotorcyclesSortedByModelUC
import edu.pract5.apirestfree.domain.UpdateLocalMotorcycleUC
import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.ModelMotorcyclesFilter
import edu.pract5.apirestfree.utils.YearMotorcyclesFilter
import edu.pract5.apirestfree.utils.modelMotorcyclesFilter
import edu.pract5.apirestfree.utils.yearMotorcyclesFilter
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
 * @param getMotorcyclesUC Use case to get the motorcycles from the API.
 * @param getLocalMotorcyclesSortedByModelUC Use case to get the sorted favourite motorcycles from the local DB.
 * @param updateLocalMotorcycleUC Use case to update the favourite motorcycle in the local DB.
 */
class MainViewModel (
    private val getMotorcyclesUC: GetRemoteMotorcyclesUC,
    private val getLocalMotorcyclesSortedByModelUC: GetLocalMotorcyclesSortedByModelUC,
    private val updateLocalMotorcycleUC: UpdateLocalMotorcycleUC,
    private val deleteLocalMotorcycleUC: DeleteLocalMotorcycleUC
) : ViewModel() {
    var isFavouriteMotorcyclesSelected: Boolean = false
        set(value) {
            field = value
            if (yearFilter) {
                _motorcycles.value = sortByYear(
                    _favMotorcycles.value.takeIf { field } ?: _apiMotorcycles.value
                )
            } else {
                _motorcycles.value = sortByModel(
                    _favMotorcycles.value.takeIf { field } ?: _apiMotorcycles.value
                )
            }
        }

    private var _motorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    val motorcycles: StateFlow<List<Motorcycle>>
        get() = _motorcycles.asStateFlow()

    private var _apiMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    private var _favMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())

    private var yearFilter = false

    init {
        getLocalMotorcyclesSortedByModel()
        getRemoteMotorcycles()
        getAllMotorcycles()
    }

    /**
     * Actualiza el filtro y ordena la lista de palabras combinadas.
     */
    fun sortMotorcyclesByModel() {
        yearFilter = false
        modelMotorcyclesFilter =
            if (modelMotorcyclesFilter == ModelMotorcyclesFilter.ALPHA_ASC) {
                ModelMotorcyclesFilter.ALPHA_DESC
            } else {
                ModelMotorcyclesFilter.ALPHA_ASC
            }
        _motorcycles.value = sortByModel(_motorcycles.value)
    }

    /**
     * Actualiza el filtro y ordena la lista de palabras combinadas.
     */
    /*fun sortMotorcyclesByYear() {
        yearFilter = true
        yearMotorcyclesFilter =
            if (yearMotorcyclesFilter == YearMotorcyclesFilter.NUMBER_ASC) {
                YearMotorcyclesFilter.NUMBER_DESC
            } else {
                YearMotorcyclesFilter.NUMBER_ASC
            }
        _motorcycles.value = sortByYear(_motorcycles.value)
    }*/

    /**
     * Insertar o borrar una palabra favorita en la BD local.
     * @param motorcycle Id, nombre, descripción y estado favorita.
     */
    fun updateLocalMotorcycle(motorcycle: Motorcycle) {
        viewModelScope.launch {
            updateLocalMotorcycleUC.invoke(motorcycle)
        }
    }

    fun deleteLocalMotorcycle(motorcycle: Motorcycle) {
        viewModelScope.launch {
            deleteLocalMotorcycleUC.invoke(motorcycle)
        }
    }

    /**
     * Busca en la lista una palabra aleatoria.
     * @return Palabra con nombre y descripción o null.
     */
    fun getRandomMotorcycle(): Motorcycle? =
        if (isFavouriteMotorcyclesSelected) {
            _motorcycles.value.filter { motorcycle -> motorcycle.favourite }
        } else {
            _motorcycles.value
        }.randomOrNull()

    /**
     * Recupera las palabras de la API.
     */
    fun getRemoteMotorcycles() {
        _apiMotorcycles.value = emptyList()
        viewModelScope.launch {
            getMotorcyclesUC.invoke().collect { motorcycles ->
                _apiMotorcycles.value = motorcycles

                motorcycles.forEach { motorcycle ->
                    updateLocalMotorcycleUC.invoke(motorcycle)
                }
            }
        }
    }

    /**
     * Recupera las palabras favoritas ordenadas de la BD local.
     */
    private fun getLocalMotorcyclesSortedByModel() {
        viewModelScope.launch {
            getLocalMotorcyclesSortedByModelUC.invoke(filter = modelMotorcyclesFilter).collect {
                _favMotorcycles.value = it.map { word ->
                    word.favourite = true
                    word
                }
            }
        }
    }

    /**
     * Combina las palabras de la API con las favoritas y las ordena.
     */
    private fun getAllMotorcycles() {
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
                if (yearFilter) {
                    _motorcycles.value = if (isFavouriteMotorcyclesSelected) {
                        sortByYear(motorcycles.filter { motorcycle
                            -> motorcycle.favourite
                        })
                    } else {
                        sortByYear(motorcycles)
                    }
                } else {
                    _motorcycles.value = if (isFavouriteMotorcyclesSelected) {
                        sortByModel(motorcycles.filter { motorcycle
                            -> motorcycle.favourite
                        })
                    } else {
                        sortByModel(motorcycles)
                    }
                }
            }
        }
    }

    /**
     * Ordena las palabras de la lista combinada.
     * @param motorcycles Lista de palabras.
     * @return Lista de palabras ordenadas.
     */
    private fun sortByModel(motorcycles: List<Motorcycle>): List<Motorcycle> {
        return when (modelMotorcyclesFilter) {
            ModelMotorcyclesFilter.ALPHA_ASC -> motorcycles.sortedBy { motorcycle ->
                motorcycle.model.uppercase()
            }

            ModelMotorcyclesFilter.ALPHA_DESC -> motorcycles.sortedByDescending { motorcycle ->
                motorcycle.model.uppercase()
            }
        }
    }

    /**
     * Ordena las palabras de la lista combinada.
     * @param motorcycles Lista de palabras.
     * @return Lista de palabras ordenadas.
     */
    private fun sortByYear(motorcycles: List<Motorcycle>): List<Motorcycle> {
        return when (yearMotorcyclesFilter) {
            YearMotorcyclesFilter.NUMBER_ASC -> motorcycles.sortedBy { motorcycle ->
                motorcycle.year
            }

            YearMotorcyclesFilter.NUMBER_DESC -> motorcycles.sortedByDescending { motorcycle ->
                motorcycle.year
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
    private val getLocalMotorcyclesSortedByModelUC: GetLocalMotorcyclesSortedByModelUC,
    private val updateLocalMotorcycleUC: UpdateLocalMotorcycleUC,
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