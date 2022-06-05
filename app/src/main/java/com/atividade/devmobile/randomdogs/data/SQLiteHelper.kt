package com.atividade.devmobile.randomdogs.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.atividade.devmobile.randomdogs.models.FactModel

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /*
        Esta classe é responsável por fazer toda a comunicação com o banco de dados do `SQLite` através
        da super classe [SQLiteOpenHelper]
        Nesta classe é onde ocorre a criação das tabelas, bem como toda a comunicação necessária
        para os métodos de `intert`, `delete` e `query`/`get` utilizados para esse projeto.
     */

    override fun onCreate(db: SQLiteDatabase) {

        val query = "CREATE TABLE $TABLE_NAME($ID VARCHAR, $MESSAGE VARCHAR);"

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
        private const val ID = "id"
        private const val MESSAGE = "message"
    }

    fun existsAtStorage(message: String): Boolean {
        val list = getFacts()

        for (e in list) {
            if (e.message == message) {
                return true
            }
        }
        return false
    }

    fun insertFact(fact: FactModel): Long {
        val db = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(ID, fact.id)
        contentValues.put(MESSAGE, fact.message)

        val status = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return status
    }

    fun deleteFact(id: String) {
        val db = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(ID, id)

        db.delete(TABLE_NAME, "id=?", arrayOf(id))
        db.close()
    }

    fun deleteAll() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, arrayOf())
        db.close()
    }

    fun getFacts(): ArrayList<FactModel> {

        val list = ArrayList<FactModel>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: String
        var message: String

        if (cursor.moveToFirst()) {
            do {
                val idIndex: Int = cursor.getColumnIndex("$ID")
                val messageIndex: Int = cursor.getColumnIndex("$MESSAGE")

                id = cursor.getString(idIndex)
                message = cursor.getString(messageIndex)

                val fact = FactModel(id, message)

                list.add(fact)
            } while (cursor.moveToNext())
        }
        return list
    }

}