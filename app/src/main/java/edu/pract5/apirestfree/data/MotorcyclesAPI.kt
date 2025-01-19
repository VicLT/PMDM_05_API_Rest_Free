package edu.pract5.apirestfree.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Responsible for making the request to the motorcycles API.
 * It uses Retrofit2 to make the request.
 * @author Víctor Lamas
 *
 * @property BASE_URL The base URL of the API.
 */
class CitiesAPI {
    companion object {
        private const val BASE_URL = "https://api.api-ninjas.com/"

        fun getRetrofit2Api(): MotorcyclesAPIInterface {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(MotorcyclesAPIInterface::class.java)
        }
    }
}

/**
 * Defines the methods to make the request to the motorcycles API.
 * @author Víctor Lamas
 */
interface MotorcyclesAPIInterface {
    /**
     * Gets a list of motorbikes from the API.
     *
     * @param limiter The maximum number of cities to get.
     * @return The list of cities.
     */
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    @GET("v1/motorcycles")
    suspend fun getCities(
        @Query("limit") limiter: Int = 30
    ): List<City>

    /**
     * Gets a list of cities from the API that match the name.
     *
     * @param name The name of the cities to get.
     * @param limit The maximum number of cities to get.
     * @return The list of cities.
     */
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    @GET("v1/city")
    suspend fun getCitiesByName(
        @Query("name") name: String,
        @Query("limit") limit: Int = 30
    ): List<City>
}