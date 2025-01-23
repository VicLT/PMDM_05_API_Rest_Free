package edu.pract5.apirestfree.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * DataClass Motorcycle.kt
 * Represents the properties of a motorcycle.
 * Only important params to show in documentation.
 * @author Víctor Lamas
 *
 * @param make The make of the motorcycle.
 * @param model The model of the motorcycle.
 * @param favourite If the motorcycle is favourite.
 */
@Parcelize
@Entity//(primaryKeys = ["make", "model"])
data class Motorcycle(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("displacement")
    var displacement: String? = null,
    @SerializedName("front_tire")
    var frontTire: String? = null,
    @SerializedName("gearbox")
    var gearbox: String? = null,
    @SerializedName("make")
    var make: String = "",
    @SerializedName("model")
    var model: String = "",
    @SerializedName("power")
    var power: String? = null,
    @SerializedName("rear_tire")
    var rearTire: String? = null,
    @SerializedName("torque")
    var torque: String? = null,
    @SerializedName("total_weight")
    var totalWeight: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("year")
    var year: String? = null,

    @SerializedName("viewed")
    var viewed: Boolean = false,
    @SerializedName("favourite")
    var favourite: Boolean = false
) : Parcelable