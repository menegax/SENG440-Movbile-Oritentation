package uc.seng440.project2_team18.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3To4 : Migration(3, 4) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE ChaseLocation( " +
                "title TEXT PRIMARY KEY NOT NULL, " +
                "latitude REAL NOT NULL, " +
                "longitude REAL NOT NULL, " +
                "visited INT NOT NULL)")
    }
}