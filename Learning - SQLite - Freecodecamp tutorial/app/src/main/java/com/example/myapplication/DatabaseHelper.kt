package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    1
) {
    companion object {
        const val DATABASE_NAME = "customer.db"
        const val TABLE_NAME = "CUSTOMER_TABLE"
        const val COL_NAME = "NAME"
        const val COL_AGE = "AGE"
        const val COL_IS_ACTIVE = "IS_ACTIVE"
    }

    //This is called the first time a database is accessed. There should be code in here to create a new database.
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStatement =
            "CREATE TABLE $TABLE_NAME(ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_NAME TEXT,$COL_AGE INT,$COL_IS_ACTIVE BOOLEAN)"
        db?.execSQL(createTableStatement)
    }

    //This method is called when the database is upgraded.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addOne(customer: CustomerModel): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, customer.name)
        cv.put(COL_AGE, customer.age)
        cv.put(COL_IS_ACTIVE, customer.isActive)

        val insert = db.insert(TABLE_NAME, null, cv)
        return insert != -1L
    }

    fun getEveryone(): List<CustomerModel> {
        val returnList: MutableList<CustomerModel> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val id = result.getInt(0)
                val name = result.getString(1)
                val age = result.getInt(2)
                val isActive = result.getInt(3) == 1
                val customer = CustomerModel(id, name, age, isActive)
                returnList.add(customer)
            } while (result.moveToNext())
        }
        result.close()
        db.close()
        return returnList
    }

    fun deleteOne(customer: CustomerModel): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "ID = ?", arrayOf(customer.id.toString()))
        db.close()
        return result > 0
    }
}