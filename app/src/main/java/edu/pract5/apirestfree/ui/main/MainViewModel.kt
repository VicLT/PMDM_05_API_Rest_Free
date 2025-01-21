package edu.pract5.apirestfree.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.pract5.apirestfree.domain.GetMotorcyclesUseCase
import edu.pract5.apirestfree.domain.GetSortedFavMotorcyclesUseCase
import edu.pract5.apirestfree.domain.UpdateFavMotorcycleUseCase
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
 * @param getMotorcyclesUseCase Use case to get the motorcycles from the API.
 * @param getSortedFavMotorcyclesUseCase Use case to get the sorted favourite motorcycles from the local DB.
 * @param updateFavMotorcycleUseCase Use case to update the favourite motorcycle in the local DB.
 */
class MainViewModel (
    private val getMotorcyclesUseCase: GetMotorcyclesUseCase,
    private val getSortedFavMotorcyclesUseCase: GetSortedFavMotorcyclesUseCase,
    private val updateFavMotorcycleUseCase: UpdateFavMotorcycleUseCase
) : ViewModel() {
    var isFavouriteMotorcyclesSelected: Boolean = false
        set(value) {
            field = value
            _motorcycles.value = sortByMotorcyclesFilter(
                _favMotorcycles.value.takeIf { field } ?: _apiMotorcycles.value
            )
        }

    private var _motorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    val motorcycles: StateFlow<List<Motorcycle>>
        get() = _motorcycles.asStateFlow()

    private var _apiMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())
    private var _favMotorcycles: MutableStateFlow<List<Motorcycle>> = MutableStateFlow(emptyList())

    init {
        getFavMotorcycles()
        getApiMotorcycles()
        getAllMotorcycles()
    }

    /**
     * Actualiza el filtro y ordena la lista de palabras combinadas.
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
     * Insertar o borrar una palabra favorita en la BD local.
     * @param word Id, nombre, descripción y estado favorita.
     */
    fun updateMotorcycle(motorcycle: Motorcycle) {
        viewModelScope.launch {
            updateFavMotorcycleUseCase.invoke(motorcycle)
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
    fun getApiMotorcycles() {
        _apiMotorcycles.value = emptyList()
        viewModelScope.launch {
            getMotorcyclesUseCase.invoke().collect {
                _apiMotorcycles.value = it
            }
        }
    }

    /**
     * Recupera las palabras favoritas ordenadas de la BD local.
     */
    private fun getFavMotorcycles() {
        viewModelScope.launch {
            getSortedFavMotorcyclesUseCase.invoke(filter = motorcyclesFilter).collect {
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
                _motorcycles.value = if (isFavouriteMotorcyclesSelected) {
                    sortByMotorcyclesFilter(motorcycles.filter { motorcycle
                        -> motorcycle.favourite
                    })
                } else {
                    sortByMotorcyclesFilter(motorcycles)
                }
            }
        }
    }

    /**
     * Ordena las palabras de la lista combinada.
     * @param words Lista de palabras.
     * @return Lista de palabras ordenadas.
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
class MainViewModelFactory(
    private val getMotorcyclesUseCase: GetMotorcyclesUseCase,
    private val getSortedFavMotorcyclesUseCase: GetSortedFavMotorcyclesUseCase,
    private val updateFavMotorcycleUseCase: UpdateFavMotorcycleUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getMotorcyclesUseCase,
            getSortedFavMotorcyclesUseCase,
            updateFavMotorcycleUseCase
        ) as T
    }
}