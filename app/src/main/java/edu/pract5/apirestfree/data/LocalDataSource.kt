package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import kotlinx.coroutines.flow.Flow

/**
 * Class LocalDataSource.kt
 * Responsible for managing the local database of deleted motorcycles.
 * @author Víctor Lamas
 *
 * @param db The database of cities.
 */
class LocalDataSource(private val db: MotorcyclesDao) {
    /**
     * Insert a motorcycle in the local DB.
     *
     * @param motorcycle Motorcycle that wants to be deleted.
     */
    suspend fun saveLocalMotorcycle(motorcycle: Motorcycle) {
        db.saveLocalMotorcycle(motorcycle)
    }

    /**
     * Get the list of motorcycles from the local DB by ascending sort.
     *
     * @return Cold flow of ascendant sorted list of deleted motorcycles.
     */
    fun getLocalMotorcyclesSortedByModelAsc(): Flow<List<Motorcycle>> {
        return db.getLocalMotorcyclesSortedByModelAsc()
    }

    /**
     * Get the list of motorcycles from the local DB by descendant sort.
     *
     * @return Cold flow of descendant sorted list of deleted motorcycles.
     */
    fun getLocalMotorcyclesSortedByModelDesc(): Flow<List<Motorcycle>> {
        return db.getLocalMotorcyclesSortedByModelDesc()
    }

    /**
     * Deletes a motorcycle from the local DB.
     * @param motorcycle Deleted motorcycle.
     */
    suspend fun deleteLocalMotorcycle(motorcycle: Motorcycle) {
        db.deleteLocalMotorcycle(motorcycle)
    }
}