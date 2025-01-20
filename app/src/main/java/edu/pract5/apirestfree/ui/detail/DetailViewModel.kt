package edu.pract5.apirestfree.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.pract5.apirestfree.data.MotorcyclesRepository

/**
 * Class DetailViewModel.kt
 * Gestiona las operaciones y el estado de los datos en la UI de DetailActivity.
 * @author Víctor Lamas
 *
 * @param repository Permite recuperar todas las palabras y sus propiedades.
 */
class DetailViewModel (private val repository: MotorcyclesRepository) : ViewModel() {
    /*var isFavouriteWordsSelected: Boolean = false
        set(value) {
            field = value
            _words.value = sortByWordsFilter(
                _favWords.value.takeIf { field } ?: _apiWords.value
            )
        }

    private var _words: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())
    val words: StateFlow<List<Word>>
        get() = _words.asStateFlow()

    private var _apiWords: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())
    private var _favWords: MutableStateFlow<List<Word>> = MutableStateFlow(emptyList())

    init {
        getFavWords()
        getApiWords()
        getAllWords()
    }*/

    /**
     * Actualiza el filtro y ordena la lista de palabras combinadas.
     */
    /*fun sortWords() {
        wordsFilter =
            if (wordsFilter == WordsFilter.ALPHA_ASC) {
                WordsFilter.ALPHA_DESC
            } else {
                WordsFilter.ALPHA_ASC
            }
        _words.value = sortByWordsFilter(_words.value)
    }*/

    /**
     * Insertar o borrar una palabra favorita en la BD local.
     * @param word Id, nombre, descripción y estado favorita.
     */
    /*fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.updateFavWord(word)
        }
    }*/

    /**
     * Busca en la lista una palabra aleatoria.
     * @return Palabra con nombre y descripción o null.
     */
    /*fun getRandomWord(): Word? =
        if (isFavouriteWordsSelected) {
            _words.value.filter { word -> word.favourite }
        } else {
            _words.value
        }.randomOrNull()*/

    /**
     * Recupera las palabras de la API.
     */
    /*fun getApiWords() {
        _apiWords.value = emptyList()
        viewModelScope.launch {
            repository.getAllApiWords().collect {
                _apiWords.value = it
            }
        }
    }*/

    /**
     * Recupera las palabras favoritas ordenadas de la BD local.
     */
    /*private fun getFavWords() {
        viewModelScope.launch {
            repository.getSortedFavWords(filter = wordsFilter).collect {
                _favWords.value = it.map { word ->
                    word.favourite = true
                    word
                }
            }
        }
    }*/

    /**
     * Combina las palabras de la API con las favoritas y las ordena.
     */
    /*private fun getAllWords() {
        viewModelScope.launch {
            combine(_apiWords, _favWords) { apiWords, favWords ->
                apiWords.map { apiWord ->
                    apiWord.apply {
                        favourite = favWords.any { favWord ->
                            favWord.idWord == apiWord.idWord }
                    }
                }
            }.catch { exception ->
                Log.e("MainViewModel", exception.message.toString())
            }.collect { words ->
                _words.value = if (isFavouriteWordsSelected) {
                    sortByWordsFilter(words.filter { word
                        -> word.favourite
                    })
                } else {
                    sortByWordsFilter(words)
                }
            }
        }
    }*/

    /**
     * Ordena las palabras de la lista combinada.
     * @param words Lista de palabras.
     * @return Lista de palabras ordenadas.
     */
    /*private fun sortByWordsFilter(words: List<Word>): List<Word> {
        return when (wordsFilter) {
            WordsFilter.ALPHA_ASC -> words.sortedBy { word ->
                word.word?.uppercase()
            }

            WordsFilter.ALPHA_DESC -> words.sortedByDescending { word ->
                word.word?.uppercase()
            }
        }
    }*/
}

/**
 * Clase DetailViewModelFactory.kt
 * Creates an instance of DetailViewModel.
 *
 * @param repository It allows retrieving all motorcycles and their properties.
 */
@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val repository: MotorcyclesRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}