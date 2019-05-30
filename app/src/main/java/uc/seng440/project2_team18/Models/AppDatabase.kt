package uc.seng440.project2_team18.Models

import androidx.room.Database
import androidx.room.RoomDatabase
import uc.seng440.project2_team18.Models.Achievement.Achievement
import uc.seng440.project2_team18.Models.Achievement.AchievementDao
import uc.seng440.project2_team18.Models.User.User
import uc.seng440.project2_team18.Models.User.UserDao

@Database(entities = arrayOf(User::class, Achievement::class), version = 1)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun achievementDao(): AchievementDao

}