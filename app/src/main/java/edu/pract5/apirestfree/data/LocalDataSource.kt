package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import kotlinx.coroutines.flow.Flow

/**
 * Class LocalDataSource.kt
 * Responsible for managing the local database of motorcycles.
 * @author Víctor Lamas
 *
 * @param db The database of cities.
 * @constructor Creates a LocalDataSource.
 */
class LocalDataSource(private val db: MotorcyclesDao) {
    /**
     * Insert a deleted motorcycle in the local DB.
     *
     * @param motorcycle Motorcycle that wants to be deleted.
     */
    suspend fun saveLocalMotorcycle(motorcycle: Motorcycle) {
        db.saveLocalMotorcycle(motorcycle)
    }

    /*fun getLocalMotorcyclesByModel(model: String): Flow<List<Motorcycle>> {
        return db.getLocalMotorcyclesByModel(model)
    }*/

    /**
     * Get the list of deleted motorcycles from the local DB by ascending sort.
     *
     * @return Cold flow of ascendant sorted list of deleted motorcycles.
     */
    fun getLocalMotorcyclesSortedByModelAsc(): Flow<List<Motorcycle>> {
        return db.getLocalMotorcyclesSortedByModelAsc()
    }

    /**
     * Get the list of deleted motorcycles from the local DB by descendant sort.
     *
     * @return Cold flow of descendant sorted list of deleted motorcycles.
     */
    fun getLocalMotorcyclesSortedByModelDesc(): Flow<List<Motorcycle>> {
        return db.getLocalMotorcyclesSortedByModelDesc()
    }

    /**
     * Deletes a deleted motorcycle from the local DB.
     * @param motorcycle Deleted motorcycle.
     */
    suspend fun deleteLocalMotorcycle(motorcycle: Motorcycle) {
        db.deleteLocalMotorcycle(motorcycle)
    }
}