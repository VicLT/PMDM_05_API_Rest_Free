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
    fun getMotorcycles(): Flow<List<Motorcycle>> {
        return remoteDataSource.getMotorcycles()
    }

    /**
     * Get the complete sorted list of favourite motorcycles from the local DB.
     *
     * @param filter Ascendant or descendant sorting filter.
     * @return Cold flow list of favourite motorcycles.
     */
    fun getSortedFavMotorcycles(filter: MotorcyclesFilter): Flow<List<Motorcycle>> {
        return when (filter) {
            MotorcyclesFilter.ALPHA_ASC -> localDataSource.getAscFavMotorcycles()
            MotorcyclesFilter.ALPHA_DESC -> localDataSource.getDescFavMotorcycles()
        }
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