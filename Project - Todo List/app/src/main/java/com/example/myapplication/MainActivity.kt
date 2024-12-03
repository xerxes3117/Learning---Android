package com.example.myapplication

import RecyclerItemTouchHelper
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.TaskRecyclerViewAdapter
import com.example.myapplication.Model.TaskModel
import com.example.myapplication.Utils.DatabaseHandler
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseHandler
    private lateinit var recyclerViewAdapter: TaskRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(this)

        initRecyclerView()
        setupClickHandlers()
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = TaskRecyclerViewAdapter(
            onCheckBoxClick = { id, isChecked -> onCheckBoxClick(id, isChecked) },
        )
        binding.recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        val callback = RecyclerItemTouchHelper(
            this@MainActivity,
            recyclerViewAdapter,
            onSwipeRight = { id -> onSwipeRight(id) },
            onTaskDeleted = { id -> onTaskDeleted(id) }
        )
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        loadTasks()
    }

    private fun onSwipeRight(id: Int) {
        db.getTaskById(id.toLong())?.let {
            showAddTaskBottomSheet(it, "UPDATE")
        }
        recyclerViewAdapter.notifyItemChanged(id)
    }

    private fun onTaskDeleted(id: Int) {
        db.deleteTask(id.toLong())?.let {
            recyclerViewAdapter.removeTask(id)
        }
    }

    private fun setupClickHandlers() {
        binding.floatingActionButton.setOnClickListener {
            showAddTaskBottomSheet(null, "ADD")
        }
    }

    private fun onCheckBoxClick(id: Int, isChecked: Boolean) {
        db.updateStatus(id.toLong(), isChecked)
        db.getTaskById(id.toLong())?.let {
            recyclerViewAdapter.updateTask(it)
        }
    }

    private fun loadTasks() {
        val taskList =
            db.getAllTasks() //@DOUBTS: This process is synchronous. How does it work different from NodeJS or JS in browser
        recyclerViewAdapter.setTaskList(taskList)
    }

    private fun showAddTaskBottomSheet(task: TaskModel?, action: String) {
        val addTaskBottomSheet = AddTaskBottomSheetFragment(
            onTaskAdded = { newTask -> onNewTaskAdded(newTask) },
            onTaskUpdated = { newTask -> onTaskUpdated(newTask) },
            task,
            action,
            onDismissCallback = { onDismissCallback() }
        )
        addTaskBottomSheet.show(supportFragmentManager, "AddTaskBottomSheet")
    }

    private fun onNewTaskAdded(newTask: TaskModel) {
        val id = db.addTask(newTask)
        db.getTaskById(id)?.let {
            recyclerViewAdapter.addTask(it)
        }
    }

    private fun onTaskUpdated(updatedTask: TaskModel) {
        db.updateTask(updatedTask.id.toLong(), updatedTask.taskText)?.let {
            recyclerViewAdapter.updateTask(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onDismissCallback () {
        recyclerViewAdapter.notifyDataSetChanged()
    }
}