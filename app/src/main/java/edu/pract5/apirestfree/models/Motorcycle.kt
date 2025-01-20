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
 * @param type The type of the motorcycle.
 * @param displacement The displacement of the motorcycle.
 * @param power The power of the motorcycle.
 * @param torque The torque of the motorcycle.
 * @param gearbox The gearbox of the motorcycle.
 * @param frontTire The front tire of the motorcycle.
 * @param rearTire The rear tire of the motorcycle.
 * @param totalWeight The total weight of the motorcycle.
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
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("displacement")
    var displacement: String? = null,
    @SerializedName("power")
    var power: String? = null,
    @SerializedName("torque")
    var torque: String? = null,
    @SerializedName("gearbox")
    var gearbox: String? = null,
    @SerializedName("front_tire")
    var frontTire: String? = null,
    @SerializedName("rear_tire")
    var rearTire: String? = null,
    @SerializedName("total_weight")
    var totalWeight: String? = null,

    @Ignore
    var favourite: Boolean = false
)