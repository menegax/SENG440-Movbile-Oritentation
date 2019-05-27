package com.example.project2_team18.Models.User

import android.content.Context
import androidx.lifecycle.LiveData
import android.os.AsyncTask
import androidx.room.Room
import com.example.project2_team18.Models.AppDatabase


class UserRepository(context: Context) {

    private val DB_NAME = "app_database"

    private val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

    fun getUser(firstName: String, lastName: String): User? {

        val response = object : AsyncTask<Void, Void, User>() {
            override fun doInBackground(vararg voids: Void): User? {
                val andy = appDatabase.userDao().findByName(firstName, lastName)
                return andy
            }
        }.execute()
        return response.get()

    }

    fun insertUser(user: User) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                appDatabase.userDao().insertAll(user)
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
