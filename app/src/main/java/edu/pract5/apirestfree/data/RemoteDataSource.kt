package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.models.Motorcycle

/**
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
     * @return The list of motorcycles.
     */
    suspend fun getMotorcycles(): List<Motorcycle> {
        return api.getMotorcycles()
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