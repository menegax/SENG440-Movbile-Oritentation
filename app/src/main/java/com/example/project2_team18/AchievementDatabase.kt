package com.example.project2_team18

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Achievement::class], version = 1)
abstract class AchievementDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
}