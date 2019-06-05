package uc.seng440.project2_team18.Models.ChaseLocation

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.example.project2_team18.migrations.Migration1To2
import com.example.project2_team18.migrations.Migration2To3
import uc.seng440.project2_team18.Models.AppDatabase
import uc.seng440.project2_team18.migrations.Migration3To4


class ChaseLocationRepository(context: Context) {

    private val DB_NAME = "app_database"

    private val appDatabase: AppDatabase

    companion object {
        val MIGRATION_1_TO_2 = Migration1To2()
        val MIGRATION_2_TO_3 = Migration2To3()
        val MIGRATION_3_TO_4 = Migration3To4()
    }

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .addMigrations(MIGRATION_1_TO_2, MIGRATION_2_TO_3, MIGRATION_3_TO_4).build()
    }

    fun getAllChaseLocations(): List<ChaseLocation> {
        val response = object : AsyncTask<Void, Void, List<ChaseLocation>>() {
            override fun doInBackground(vararg voids: Void): List<ChaseLocation> {
                val chaseLocationsList = appDatabase.chaseLocationDao().getAll()
                return chaseLocationsList
            }
        }.execute()
        return response.get()
    }

    fun getChaseLocationByTitle(title: String): List<ChaseLocation> {
        val response = object : AsyncTask<Void, Void, List<ChaseLocation>>() {
            override fun doInBackground(vararg voids: Void): List<ChaseLocation> {
                val chaseLocationsList = appDatabase.chaseLocationDao().getByTitle(title)
                return chaseLocationsList
            }
        }.execute()
        return response.get()
    }

    fun insertChaseLocation(chaseLocation: ChaseLocation) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                appDatabase.chaseLocationDao().insert(chaseLocation)
                return null
            }
        }.execute()
    }

    fun updateChaseLocation(title: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void?): Void? {
                appDatabase.chaseLocationDao().update(title)
                return null
            }
        }.execute()
    }

    fun deleteChaseLocation(title: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void?): Void? {
                appDatabase.chaseLocationDao().delete(title)
                return null
            }
        }.execute()
    }

}
