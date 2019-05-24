package com.example.project2_team18

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AchievementDao {
//    @Insert
//    fun insert(achievement: Achievement): String

    @Query("SELECT * FROM achievements")
    fun getAll(): List<Achievement>
}