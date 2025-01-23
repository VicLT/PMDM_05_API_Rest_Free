package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
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
    suspend fun getRemoteMotorcyclesByMakeOrModel(model: String): Flow<List<Motorcycle>> {
        return remoteDataSource.getRemoteMotorcyclesByMakeOrModel(model)
    }

    suspend fun getRemoteMotorcycles(): Flow<List<Motorcycle>> {
        return remoteDataSource.getRemoteMotorcycles()
    }

    fun getLocalMotorcycles(): Flow<List<Motorcycle>> {
        return localDataSource.getLocalMotorcycles()
    }

    /*fun getLocalMotorcyclesByModel(model: String): Flow<List<Motorcycle>> {
        return localDataSource.getLocalMotorcyclesByModel(model)
    }*/

    /**
     * Get the complete sorted list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of favourite motorcycles.
     */
    fun getLocalMotorcyclesSortedByModel(filter: MotorcyclesFilter): Flow<List<Motorcycle>> {
        return when (filter) {
            MotorcyclesFilter.ALPHA_ASC -> localDataSource.getLocalMotorcyclesSortedByModelAsc()
            MotorcyclesFilter.ALPHA_DESC -> localDataSource.getLocalMotorcyclesSortedByModelDesc()
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

    suspend fun deleteAllLocalMotorcycles() {
        localDataSource.deleteAllLocalMotorcycles()
    }
}