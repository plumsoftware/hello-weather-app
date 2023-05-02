package ru.plumsoftware.helloweather.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object DatabaseEntry {
    object DatabaseEntry {
        const val TABLE_NAME = "locations"
        const val CITY_NAME = "_city_name"
        const val COUNTRY_NAME = "_country_name"
        const val COUNTRY_CODE = "_country_code"
    }

    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS ${DatabaseEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${DatabaseEntry.CITY_NAME} TEXT," +
                "${DatabaseEntry.COUNTRY_NAME} TEXT," +
                "${DatabaseEntry.COUNTRY_CODE} TEXT)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseEntry.TABLE_NAME}"

    class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "ru.plumsoftware.HelloWeather.Location.db"
        }
    }
}