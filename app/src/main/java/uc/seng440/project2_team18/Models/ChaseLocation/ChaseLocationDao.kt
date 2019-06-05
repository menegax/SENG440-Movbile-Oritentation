package uc.seng440.project2_team18.Models.ChaseLocation

import androidx.room.*

@Dao
interface ChaseLocationDao{
    @Query("SELECT * FROM ChaseLocation")
    fun getAll(): List<ChaseLocation>

    @Query("SELECT * FROM ChaseLocation WHERE title=:title")
    fun getByTitle(title: String): List<ChaseLocation>

    @Insert
    fun insert(chaseLocations: ChaseLocation)

    @Query("DELETE FROM ChaseLocation where title=:title")
    fun delete(title: String)

    @Update
    fun update(chaseLocations: ChaseLocation)
}