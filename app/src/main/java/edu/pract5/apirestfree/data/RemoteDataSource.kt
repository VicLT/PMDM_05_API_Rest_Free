package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle
import kotlinx.coroutines.flow.flow

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
    fun getMotorcycles() = flow {
        emit(api.getMotorcycles())
    }

    /**
     * Gets a list of motorcycles from the API that match the model.
     *
     * @param model The model of the motorcycles to get.
     * @return The list of motorcycles.
     */
    suspend fun getMotorcyclesByModel(model: String): List<Motorcycle> {
        return api.getMotorcyclesByModel(model)
    }
}