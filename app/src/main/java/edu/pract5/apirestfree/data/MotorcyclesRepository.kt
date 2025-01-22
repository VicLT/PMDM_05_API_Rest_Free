package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.ModelMotorcyclesFilter
import kotlinx.coroutines.flow.Flow

/**
 * Class MotorcyclesRepository.kt
 * Acts as an intermediary between the ViewModel and the data sources.
 * @author Víctor Lamas
 *
 * @param remoteDataSource Datos heredados de la clase RemoteDataSource.
 * @param localDataSource Datos heredados de la clase LocalDataSource.
 */
class MotorcyclesRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    /**
     * Get the complete list of API motorcycles.
     *
     * @return Cold flow list of motorcycles.
     */
    fun getRemoteMotorcyclesByMakeOrModel(): Flow<List<Motorcycle>> {
        return remoteDataSource.getRemoteMotorcyclesByMakeOrModel()
    }

    /**
     * Get the complete sorted list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of favourite motorcycles.
     */
    fun getLocalMotorcyclesSortedByModel(filter: ModelMotorcyclesFilter): Flow<List<Motorcycle>> {
        return when (filter) {
            ModelMotorcyclesFilter.ALPHA_ASC -> localDataSource.getLocalMotorcyclesSortedByModelAsc()
            ModelMotorcyclesFilter.ALPHA_DESC -> localDataSource.getLocalMotorcyclesSortedByModelDesc()
        }
    }

    /**
     * Inserts a favourite motorcycle in the local DB.
     *
     * @param motorcycle Motorcycle marked as favourite.
     */
    suspend fun saveLocalMotorcycle(motorcycle: Motorcycle) {
        localDataSource.saveLocalMotorcycle(motorcycle)
    }

    /**
     * Deletes a favourite motorcycle in the local DB.
     *
     * @param motorcycle Motorcycle marked as favourite.
     */
    suspend fun deleteLocalMotorcycle(motorcycle: Motorcycle) {
        localDataSource.deleteLocalMotorcycle(motorcycle)
    }
}