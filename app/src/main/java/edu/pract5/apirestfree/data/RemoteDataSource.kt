package edu.pract5.apirestfree.data

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
    fun getRemoteMotorcyclesByMakeOrModel() = flow {
        emit(api.getRemoteMotorcyclesByMakeOrModel())
    }
}