package uc.seng440.project2_team18.Models

import androidx.room.Database
import androidx.room.RoomDatabase
import uc.seng440.project2_team18.Models.Achievement.Achievement
import uc.seng440.project2_team18.Models.Achievement.AchievementDao
import uc.seng440.project2_team18.Models.ChaseLocation.ChaseLocation
import uc.seng440.project2_team18.Models.ChaseLocation.ChaseLocationDao

@Database(entities = [Achievement::class, ChaseLocation::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun achievementDao(): AchievementDao
    abstract fun chaseLocationDao(): ChaseLocationDao

}