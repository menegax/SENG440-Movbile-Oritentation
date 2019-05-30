package com.example.project2_team18.Models.Achievement

import androidx.room.*

@Dao
interface AchievementDao {
    @Query("SELECT * FROM Achievement")
    fun getAll(): List<Achievement>

    @Query("SELECT * FROM Achievement WHERE title=:title")
    fun getByTitle(title: String): List<Achievement>

    @Insert
    fun insert(achievements: Achievement)

    @Query("DELETE FROM achievement where title=:title")
    fun delete(title: String)

    @Update
    fun update(achievements: Achievement)
}