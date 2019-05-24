package com.example.project2_team18

import android.os.AsyncTask
import androidx.room.Room
import java.lang.ref.WeakReference

class LoadDatabaseTask(adapter: MyAchievementRecyclerViewAdapter) : AsyncTask<Unit, Unit, AchievementDatabase?>() {
    private val adapter = WeakReference(adapter)

    override fun doInBackground(vararg p0: Unit?): AchievementDatabase? {
        var database: AchievementDatabase? = null
        adapter.get()?.let {
            database = Room.databaseBuilder(it.context.applicationContext, AchievementDatabase::class.java, "achievements").build()
        }
        return database
    }

    override fun onPostExecute(database: AchievementDatabase?) {
        adapter.get()?.let {
            it.database = database
        }
    }
}
