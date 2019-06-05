package uc.seng440.project2_team18.Models.ChaseLocation

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity
data class ChaseLocation(
    @PrimaryKey val title: String,
    val latitude: Double,
    val longitude: Double,
    var visited: Boolean
)