package com.example.project2_team18.Models.Achievement

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Achievement(
    @PrimaryKey val title: String,
    val status: String
)