package com.example.project2_team18

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
class Achievement(@PrimaryKey val title: String,
                  val status: String) {
}