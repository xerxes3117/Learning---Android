package com.example.myapplication.Utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.Model.TaskModel

class DatabaseHandler(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TaskListDB"
        private const val TABLE_NAME = "tasks"
        private const val ID = "id"
        private const val COL_TASK = "task"
        private const val COL_STATUS = "status"
        private const val CREATE_TODO_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TASK TEXT, $COL_STATUS BOOLEAN)"
        private const val GET_ALL_TASKS = "SELECT * FROM $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTask(task: TaskModel): Long {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_TASK, task.taskText)
            put(COL_STATUS, task.isDone)
        }
        val id = db.insert(TABLE_NAME, null, cv)
        db.close()
        return id
    }

    fun getTaskById(id: Long?): TaskModel? {
        if (id == null) return null

        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID = ?", arrayOf(id.toString()))
        val task = cursor.use {
            if (it.moveToFirst()) {
                TaskModel(it.getInt(0), it.getInt(2) == 1, it.getString(1))
            } else null
        }
        db.close()
        return task
    }

    fun getAllTasks(): MutableList<TaskModel> {
        val taskList = mutableListOf<TaskModel>()
        val db = readableDatabase
        val cursor = db.rawQuery(GET_ALL_TASKS, null)
        cursor.use {
            while (it.moveToNext()) {
                taskList.add(TaskModel(it.getInt(0), it.getInt(2) == 1, it.getString(1)))
            }
        }
        db.close()
        return taskList
    }

    fun updateStatus(id: Long?, status: Boolean) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_STATUS, status)
        }
        db.update(TABLE_NAME, cv, "$ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun updateTask(id: Long?, text: String): TaskModel? {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(COL_TASK, text)
        }
        db.update(TABLE_NAME, cv, "$ID=?", arrayOf(id.toString()))
        db.close()

        return getTaskById(id)
    }

    fun deleteTask(id: Long?): TaskModel? {
        val task = getTaskById(id)
        val db = writableDatabase
        db.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString()))
        db.close()

        return task
    }
}