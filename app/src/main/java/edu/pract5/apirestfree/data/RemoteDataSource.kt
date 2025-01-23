package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import kotlinx.coroutines.flow.Flow

/**
 * Class RemoteDataSource.kt
 * Responsible for making the request to the motorcycles API.
 * @author Víctor Lamas
 *
 * @property api The API to get the motorcycles.
 */
class RemoteDataSource {
    private val api = MotorcyclesAPI.getRetrofit2Api()

    /**
     * Gets a list of motorcycles from the API.
     *
     * @return A flow list of motorcycles.
     */
    suspend fun getRemoteMotorcyclesByMakeOrModel(model: String) : Flow<List<Motorcycle>> {
        return api.getRemoteMotorcyclesByMakeOrModel(model)
    }

    suspend fun getRemoteMotorcycles() : Flow<List<Motorcycle>> {
        return api.getRemoteMotorcycles()
    }
}