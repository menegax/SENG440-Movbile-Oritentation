package com.example.project2_team18.Models.Achievement

import android.content.Context
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import androidx.room.Room
import com.example.project2_team18.Models.AppDatabase
import com.example.project2_team18.Models.User.User


class AchievementRepository(context: Context) {

    private val DB_NAME = "app_database"

    private val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
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

    fun insertAchievement(achievement: Achievement) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                appDatabase.achievementDao().insert(achievement)
                return null
            }
        }.execute()
    }


//    fun updateTask(note: Note) {
//        note.setModifiedAt(AppUtils.getCurrentDateTime())
//
//        object : AsyncTask<Void, Void, Void>() {
//            override fun doInBackground(vararg voids: Void): Void? {
//                noteDatabase.daoAccess().updateTask(note)
//                return null
//            }
//        }.execute()
//    }
//
//    fun deleteTask(id: Int) {
//        val task = getTask(id)
//        if (task != null) {
//            object : AsyncTask<Void, Void, Void>() {
//                override fun doInBackground(vararg voids: Void): Void? {
//                    noteDatabase.daoAccess().deleteTask(task.getValue())
//                    return null
//                }
//            }.execute()
//        }
//    }
//
//    fun deleteTask(note: Note) {
//        object : AsyncTask<Void, Void, Void>() {
//            override fun doInBackground(vararg voids: Void): Void? {
//                noteDatabase.daoAccess().deleteTask(note)
//                return null
//            }
//        }.execute()
//    }
//

}
