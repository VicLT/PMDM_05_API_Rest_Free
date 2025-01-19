package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
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
    fun getRemoteMotorcycles(): Flow<List<Motorcycle>> {
        return remoteDataSource.getRemoteMotorcycles()
    }

    /**
     * Get the ascendant sorted list of favourite motorcycles from the local DB.
     *
     * @return Cold flow ascendant sorted list.
     */
    fun getAscSortedFavMotorcycles(): Flow<List<Motorcycle>> {
        return localDataSource.getAscFavMotorcycles()
    }

    /**
     * Get the descendant sorted list of favourite motorcycles from the local DB.
     *
     * @return Cold flow descendant sorted list.
     */
    fun getDescSortedFavMotorcycles(): Flow<List<Motorcycle>> {
        return localDataSource.getDescFavMotorcycles()
    }

    /**
     * Inserts a favourite motorcycle in the local DB.
     *
     * @param favMotorcycle Motorcycle marked as favourite.
     */
    suspend fun saveFavMotorcycle(favMotorcycle: Motorcycle) {
        localDataSource.saveFavMotorcycle(favMotorcycle)
    }

    /**
     * Deletes a favourite motorcycle in the local DB.
     *
     * @param favMotorcycle Motorcycle marked as favourite.
     */
    suspend fun deleteFavMotorcycle(favMotorcycle: Motorcycle) {
        localDataSource.deleteFavMotorcycle(favMotorcycle)
    }
}