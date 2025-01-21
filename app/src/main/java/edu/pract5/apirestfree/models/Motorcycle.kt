package edu.pract5.apirestfree.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * DataClass Motorcycle.kt
 * Represents the properties of a motorcycle.
 * @author Víctor Lamas
 *
 * @param boreStroke The bore and stroke of the motorcycle.
 * @param clutch The clutch of the motorcycle.
 * @param compression The compression of the motorcycle.
 * @param cooling The cooling of the motorcycle.
 * @param displacement The displacement of the motorcycle.
 * @param engine The engine of the motorcycle.
 * @param frame The frame of the motorcycle.
 * @param frontBrakes The front brakes of the motorcycle.
 * @param frontSuspension The front suspension of the motorcycle.
 * @param frontTire The front tire of the motorcycle.
 * @param frontWheelTravel The front wheel travel of the motorcycle.
 * @param fuelCapacity The fuel capacity of the motorcycle.
 * @param fuelControl The fuel control of the motorcycle.
 * @param fuelSystem The fuel system of the motorcycle.
 * @param gearbox The gearbox of the motorcycle.
 * @param groundClearance The ground clearance of the motorcycle.
 * @param ignition The ignition of the motorcycle.
 * @param lubrication The lubrication of the motorcycle.
 * @param make The make of the motorcycle.
 * @param model The model of the motorcycle.
 * @param power The power of the motorcycle.
 * @param rearBrakes The rear brakes of the motorcycle.
 * @param rearSuspension The rear suspension of the motorcycle.
 * @param rearTire The rear tire of the motorcycle.
 * @param rearWheelTravel The rear wheel travel of the motorcycle.
 * @param seatHeight The seat height of the motorcycle.
 * @param starter The starter of the motorcycle.
 * @param torque The torque of the motorcycle.
 * @param totalHeight The total height of the motorcycle.
 * @param totalLength The total length of the motorcycle.
 * @param totalWeight The total weight of the motorcycle.
 * @param totalWidth The total width of the motorcycle.
 * @param transmission The transmission of the motorcycle.
 * @param type The type of the motorcycle.
 * @param valvesPerCylinder The valves per cylinder of the motorcycle.
 * @param wheelbase The wheelbase of the motorcycle.
 * @param year The year of the motorcycle.
 * @param favourite If the motorcycle is favourite.
 */
@Parcelize
@Entity(primaryKeys = ["make", "model"])
data class Motorcycle(
    @SerializedName("bore_stroke")
    val boreStroke: String?,
    @SerializedName("clutch")
    val clutch: String?,
    @SerializedName("compression")
    val compression: String?,
    @SerializedName("cooling")
    val cooling: String?,
    @SerializedName("displacement")
    val displacement: String?,
    @SerializedName("engine")
    val engine: String?,
    @SerializedName("frame")
    val frame: String?,
    @SerializedName("front_brakes")
    val frontBrakes: String?,
    @SerializedName("front_suspension")
    val frontSuspension: String,
    @SerializedName("front_tire")
    val frontTire: String,
    @SerializedName("front_wheel_travel")
    val frontWheelTravel: String?,
    @SerializedName("fuel_capacity")
    val fuelCapacity: String?,
    @SerializedName("fuel_control")
    val fuelControl: String?,
    @SerializedName("fuel_system")
    val fuelSystem: String?,
    @SerializedName("gearbox")
    val gearbox: String?,
    @SerializedName("ground_clearance")
    val groundClearance: String?,
    @SerializedName("ignition")
    val ignition: String?,
    @SerializedName("lubrication")
    val lubrication: String?,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("power")
    val power: String?,
    @SerializedName("rear_brakes")
    val rearBrakes: String?,
    @SerializedName("rear_suspension")
    val rearSuspension: String?,
    @SerializedName("rear_tire")
    val rearTire: String?,
    @SerializedName("rear_wheel_travel")
    val rearWheelTravel: String?,
    @SerializedName("seat_height")
    val seatHeight: String?,
    @SerializedName("starter")
    val starter: String?,
    @SerializedName("torque")
    val torque: String?,
    @SerializedName("total_height")
    val totalHeight: String?,
    @SerializedName("total_length")
    val totalLength: String?,
    @SerializedName("total_weight")
    val totalWeight: String?,
    @SerializedName("total_width")
    val totalWidth: String?,
    @SerializedName("transmission")
    val transmission: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("valves_per_cylinder")
    val valvesPerCylinder: String?,
    @SerializedName("wheelbase")
    val wheelbase: String?,
    @SerializedName("year")
    val year: String?,

    @Ignore
    var favourite: Boolean = false
) : Parcelable {
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
}