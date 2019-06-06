package uc.seng440.project2_team18.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration4To5 : Migration(4, 5) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE achievement ADD achieved INTEGER NOT NULL DEFAULT (0)")
    }
}