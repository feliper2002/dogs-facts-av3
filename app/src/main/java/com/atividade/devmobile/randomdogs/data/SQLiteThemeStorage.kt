package com.atividade.devmobile.randomdogs.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.atividade.devmobile.randomdogs.models.FactModel

class SQLiteThemeStorage(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME($VALUE INT);"

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME;"

        db.execSQL(query)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "facts.db"
        private const val TABLE_NAME = "fact"
        private const val VALUE = "value"
    }

    fun updateTheme(value: Int) {
        val db = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(VALUE, value)

        db.update(TABLE_NAME, null, "value=?", arrayOf("$value"))
        db.close()
    }
}