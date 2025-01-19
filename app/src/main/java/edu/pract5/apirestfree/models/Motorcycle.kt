package edu.pract5.apirestfree.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * DataClass Motorcycle.kt
 * Represents the properties of a motorcycle.
 * @author Víctor Lamas
 *
 * @param motorcycleId The id of the motorcycle.
 * @param make The make of the motorcycle.
 * @param model The model of the motorcycle.
 * @param year The year of the motorcycle.
 * @param favourite If the motorcycle is favourite.
 */
@Entity
data class Motorcycle(
    @SerializedName("motorcycleId")
    @PrimaryKey(autoGenerate = true)
    var motorcycleId: Int = 0,
    @SerializedName("make")
    var make: String? = null,
    @SerializedName("model")
    var model: String? = null,
    @SerializedName("year")
    var year: String? = null,

    @Ignore
    var favourite: Boolean = false
)