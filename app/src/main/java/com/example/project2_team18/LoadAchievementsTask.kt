package com.example.project2_team18

import android.os.AsyncTask

class LoadAchievementsTask(private val database: AchievementDatabase,
                           private val adapter: MyAchievementRecyclerViewAdapter) : AsyncTask<Unit, Unit, List<Achievement>>() {
    override fun doInBackground(vararg p0: Unit?): List<Achievement> {
        val achievementDao = database.achievementDao()
        return achievementDao.getAll()
    }

    override fun onPostExecute(achievements: List<Achievement>) {
        adapter.mValues = achievements
    }
}
