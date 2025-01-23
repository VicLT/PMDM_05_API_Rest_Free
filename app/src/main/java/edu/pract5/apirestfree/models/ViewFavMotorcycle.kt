package edu.pract5.apirestfree.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ViewFavMotorcycle(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("viewed")
    var viewed: Boolean = false,
    @SerializedName("favourite")
    var favourite: Boolean = false
)