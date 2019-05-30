package com.example.project2_team18.Models

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.project2_team18.Models.Achievement.Achievement
import com.example.project2_team18.Models.Achievement.AchievementDao

@Database(entities = [Achievement::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
}