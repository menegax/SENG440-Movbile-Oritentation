package com.example.project2_team18.Models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project2_team18.Models.User.User
import com.example.project2_team18.Models.User.UserDao

@Database(entities = arrayOf(User::class), version = 1)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}