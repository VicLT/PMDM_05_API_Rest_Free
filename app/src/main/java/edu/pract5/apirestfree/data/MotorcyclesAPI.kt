package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.BuildConfig
import edu.pract5.apirestfree.data.MotorcyclesAPI.Companion.BASE_URL
import edu.pract5.apirestfree.models.Motorcycle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Class Retrofit2Api.kt
 * Connection to the Motorcycles API via Retrofit2.
 * @author Víctor Lamas
 *
 * @property BASE_URL The base URL of the API.
 */
class MotorcyclesAPI {
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
 *
 */
interface MotorcyclesAPIInterface {
    /**
     * Gets a list of motorbikes from the API.
     *
     * @param make The make of the motorcycle to get.
     * @return List of 30 motorcycles.
     */
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    @GET("v1/motorcycles")
    suspend fun getRemoteMotorcyclesByMakeOrModel(
        @Query("make") make: String = " ",
        @Query("model") model: String = " "
    ): List<Motorcycle>
}