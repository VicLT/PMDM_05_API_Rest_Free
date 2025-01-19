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
     * Insert a favourite motorcycle in the local DB.
     *
     * @param motorcycle Motorcycle marked as favourite.
     */
    suspend fun saveFavMotorcycle(motorcycle: Motorcycle) {
        db.saveFavMotorcycle(motorcycle)
    }

    /**
     * Get the list of favourite motorcycles from the local DB by ascending sort.
     *
     * @return Cold flow of ascendant sorted list of favourite motorcycles.
     */
    fun getAscFavMotorcycles(): Flow<List<Motorcycle>> {
        return db.getFavMotorcyclesAsc()
    }

    /**
     * Get the list of favourite motorcycles from the local DB by descendant sort.
     *
     * @return Cold flow of descendant sorted list of favourite motorcycles.
     */
    fun getDescFavMotorcycles(): Flow<List<Motorcycle>> {
        return db.getFavMotorcyclesDesc()
    }

    /**
     * Elimina una palabra favorita de la BD local.
     * @param motorcycle Motorcycle marked as favourite.
     */
    suspend fun deleteFavMotorcycle(motorcycle: Motorcycle) {
        db.deleteFavMotorcycle(motorcycle)
    }
}