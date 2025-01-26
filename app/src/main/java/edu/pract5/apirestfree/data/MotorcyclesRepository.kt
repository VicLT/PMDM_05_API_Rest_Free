package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import edu.pract5.apirestfree.utils.MotorcyclesFilter
import kotlinx.coroutines.flow.Flow

/**
 * Class MotorcyclesRepository.kt
 * Acts as an intermediary between the ViewModel and the data sources.
 * @author Víctor Lamas
 *
 * @param remoteDataSource Data inherited from the RemoteDataSource class.
 * @param localDataSource Data inherited from the LocalDataSource class.
 */
class MotorcyclesRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    /**
     * Get the complete list of API motorcycles.
     *
     * @param model The model of the motorcycle to get.
     * @return Cold flow list of motorcycles.
     */
    fun getRemoteMotorcycles(model: String): Flow<List<Motorcycle>> {
        return remoteDataSource.getRemoteMotorcycles(model)
    }

    /**
     * Get the complete sorted list of motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of motorcycles.
     */
    fun getLocalMotorcyclesSortedByModel(filter: MotorcyclesFilter): Flow<List<Motorcycle>> {
        return when (filter) {
            MotorcyclesFilter.ALPHA_ASC ->
                localDataSource.getLocalMotorcyclesSortedByModelAsc()
            MotorcyclesFilter.ALPHA_DESC ->
                localDataSource.getLocalMotorcyclesSortedByModelDesc()
        }
    }

    /**
     * Inserts or deletes a motorcycle in the local DB.
     *
     * @param motorcycle Motorcycle marked for removal.
     */
    suspend fun updateLocalMotorcycle(motorcycle: Motorcycle) {
        if (motorcycle.deleted) {
            localDataSource.saveLocalMotorcycle(motorcycle)
        } else {
            localDataSource.deleteLocalMotorcycle(motorcycle)
        }
    }
}