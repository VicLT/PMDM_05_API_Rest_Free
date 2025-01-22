package edu.pract5.apirestfree.models

import android.os.Parcelable
import androidx.room.Entity
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
@Entity(primaryKeys = ["make", "model"])
data class Motorcycle(
    //var boreStroke: String? = null,
    //var clutch: String? = null,
    //var compression: String? = null,
    //var cooling: String? = null,
    @SerializedName("displacement")
    var displacement: String? = null,
    //var engine: String? = null,
    //var frame: String? = null,
    //var frontBrakes: String? = null,
    //var frontSuspension: String? = null,
    @SerializedName("front_tire")
    var frontTire: String? = null,
    //var frontWheelTravel: String? = null,
    //var fuelCapacity: String? = null,
    //var fuelControl: String? = null,
    //var fuelSystem: String? = null,
    @SerializedName("gearbox")
    var gearbox: String? = null,
    //var groundClearance: String? = null,
    //var ignition: String? = null,
    //var lubrication: String? = null,
    @SerializedName("make")
    var make: String = "",
    @SerializedName("model")
    var model: String = "",
    @SerializedName("power")
    var power: String? = null,
    //var rearBrakes: String? = null,
    //var rearSuspension: String? = null,
    @SerializedName("rear_tire")
    var rearTire: String? = null,
    //var rearWheelTravel: String? = null,
    //var seatHeight: String? = null,
    //var starter: String? = null,
    @SerializedName("torque")
    var torque: String? = null,
    //var totalHeight: String? = null,
    //var totalLength: String? = null,
    @SerializedName("total_weight")
    var totalWeight: String? = null,
    //var totalWidth: String? = null,
    //var transmission: String? = null,
    @SerializedName("type")
    var type: String? = null,
    //var valvesPerCylinder: String? = null,
    //var wheelbase: String? = null,
    @SerializedName("year")
    var year: String? = null,

    // Personalizados
    @SerializedName("viewed")
    var viewed: Boolean = false,
    @SerializedName("favourite")
    var favourite: Boolean = false
) : Parcelable