package uc.seng440.project2_team18.Models.Achievement

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.example.project2_team18.migrations.Migration1To2
import com.example.project2_team18.migrations.Migration2To3
import uc.seng440.project2_team18.Models.AppDatabase
import uc.seng440.project2_team18.migrations.Migration3To4
import uc.seng440.project2_team18.migrations.Migration4To5


class AchievementRepository(context: Context) {

    private val DB_NAME = "app_database"

    private val appDatabase: AppDatabase

    companion object {
        val MIGRATION_1_TO_2 = Migration1To2()
        val MIGRATION_2_TO_3 = Migration2To3()
        val MIGRATION_3_TO_4 = Migration3To4()
        val MIGRATION_4_TO_5 = Migration4To5()
    }

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .addMigrations(MIGRATION_1_TO_2, MIGRATION_2_TO_3, MIGRATION_3_TO_4, MIGRATION_4_TO_5).build()
    }

    fun getAllAchievements(): List<Achievement> {
        val response = object : AsyncTask<Void, Void, List<Achievement>>() {
            override fun doInBackground(vararg voids: Void): List<Achievement> {
                val achievementList = appDatabase.achievementDao().getAll()
                return achievementList
            }
        }.execute()
        return response.get()
    }

    fun getAchievementByTitle(title: String): List<Achievement> {
        val response = object : AsyncTask<Void, Void, List<Achievement>>() {
            override fun doInBackground(vararg voids: Void): List<Achievement> {
                val achievementList = appDatabase.achievementDao().getByTitle(title)
                return achievementList
            }
        }.execute()
        return response.get()
    }

    fun insertAchievement(achievement: Achievement) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                appDatabase.achievementDao().insert(achievement)
                return null
            }
        }.execute()
    }

    fun updateAchievement(title: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void?): Void? {
                appDatabase.achievementDao().update(title)
                return null
            }
        }.execute()
    }

    fun deleteAchievement(title: String) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void?): Void? {
                appDatabase.achievementDao().delete(title)
                return null
            }
        }.execute()
    }

}
