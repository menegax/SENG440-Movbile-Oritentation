package uc.seng440.project2_team18.Models.Achievement

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievement")
    fun getAll(): List<Achievement>

    @Insert
    fun insert(achievements: Achievement)

    @Delete
    fun delete(achievement: Achievement)
}