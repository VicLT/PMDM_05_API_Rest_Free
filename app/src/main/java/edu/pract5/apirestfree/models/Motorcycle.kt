package edu.pract5.apirestfree.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
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
    //@SerializedName("bore_stroke")
    var boreStroke: String? = null,
    //@SerializedName("clutch")
    var clutch: String? = null,
    //@SerializedName("compression")
    var compression: String? = null,
    //@SerializedName("cooling")
    var cooling: String? = null,
    @SerializedName("displacement")
    var displacement: String? = null,
    //@SerializedName("engine")
    var engine: String? = null,
    //@SerializedName("frame")
    var frame: String? = null,
    //@SerializedName("front_brakes")
    var frontBrakes: String? = null,
    //@SerializedName("front_suspension")
    var frontSuspension: String? = null,
    @SerializedName("front_tire")
    var frontTire: String? = null,
    //@SerializedName("front_wheel_travel")
    var frontWheelTravel: String? = null,
    //@SerializedName("fuel_capacity")
    var fuelCapacity: String? = null,
    //@SerializedName("fuel_control")
    var fuelControl: String? = null,
    //@SerializedName("fuel_system")
    var fuelSystem: String? = null,
    //@SerializedName("gearbox")
    var gearbox: String? = null,
    //@SerializedName("ground_clearance")
    var groundClearance: String? = null,
    //@SerializedName("ignition")
    var ignition: String? = null,
    //@SerializedName("lubrication")
    var lubrication: String? = null,
    @SerializedName("make")
    var make: String = "",
    @SerializedName("model")
    var model: String = "",
    @SerializedName("power")
    var power: String? = null,
    //@SerializedName("rear_brakes")
    var rearBrakes: String? = null,
    //@SerializedName("rear_suspension")
    var rearSuspension: String? = null,
    @SerializedName("rear_tire")
    var rearTire: String? = null,
    //@SerializedName("rear_wheel_travel")
    var rearWheelTravel: String? = null,
    //@SerializedName("seat_height")
    var seatHeight: String? = null,
    //@SerializedName("starter")
    var starter: String? = null,
    @SerializedName("torque")
    var torque: String? = null,
    //@SerializedName("total_height")
    var totalHeight: String? = null,
    //@SerializedName("total_length")
    var totalLength: String? = null,
    @SerializedName("total_weight")
    var totalWeight: String? = null,
    //@SerializedName("total_width")
    var totalWidth: String? = null,
    //@SerializedName("transmission")
    var transmission: String? = null,
    @SerializedName("type")
    var type: String? = null,
    //@SerializedName("valves_per_cylinder")
    var valvesPerCylinder: String? = null,
    //@SerializedName("wheelbase")
    var wheelbase: String? = null,
    @SerializedName("year")
    var year: String? = null,

    @Ignore
    var favourite: Boolean = false
) : Parcelable /*{
    constructor(
        boreStroke: String?,
        clutch: String?,
        compression: String?,
        cooling: String?,
        displacement: String?,
        engine: String?,
        frame: String?,
        frontBrakes: String?,
        frontSuspension: String,
        frontTire: String,
        frontWheelTravel: String?,
        fuelCapacity: String?,
        fuelControl: String?,
        fuelSystem: String?,
        gearbox: String?,
        groundClearance: String?,
        ignition: String?,
        lubrication: String?,
        make: String,
        model: String,
        power: String?,
        rearBrakes: String?,
        rearSuspension: String?,
        rearTire: String?,
        rearWheelTravel: String?,
        seatHeight: String?,
        starter: String?,
        torque: String?,
        totalHeight: String?,
        totalLength: String?,
        totalWeight: String?,
        totalWidth: String?,
        transmission: String?,
        type: String?,
        valvesPerCylinder: String?,
        wheelbase: String?,
        year: String?
    ) : this(
        boreStroke,
        clutch,
        compression,
        cooling,
        displacement,
        engine,
        frame,
        frontBrakes,
        frontSuspension,
        frontTire,
        frontWheelTravel,
        fuelCapacity,
        fuelControl,
        fuelSystem,
        gearbox,
        groundClearance,
        ignition,
        lubrication,
        make,
        model,
        power,
        rearBrakes,
        rearSuspension,
        rearTire,
        rearWheelTravel,
        seatHeight,
        starter,
        torque,
        totalHeight,
        totalLength,
        totalWeight,
        totalWidth,
        transmission,
        type,
        valvesPerCylinder,
        wheelbase,
        year,
        false
    )
}*/