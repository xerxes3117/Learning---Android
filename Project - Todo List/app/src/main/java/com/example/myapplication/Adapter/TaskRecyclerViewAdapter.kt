package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.TaskModel
import com.example.myapplication.databinding.TasksRecyclerViewRowBinding

class TaskRecyclerViewAdapter(
    private val onCheckBoxClick: (Int, Boolean) -> Unit,
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {
    private val taskList = ArrayList<TaskModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TasksRecyclerViewRowBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskList[position], position)
    }

    override fun getItemCount(): Int = taskList.size

    inner class ViewHolder(private val binding: TasksRecyclerViewRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel, position: Int) {
            binding.apply {
                checkBox.isChecked = task.isDone
                taskTextView.text = task.taskText

                checkBox.setOnClickListener {
                    onCheckBoxClick(task.id, checkBox.isChecked)
                }
            }
        }
    }

    fun getTaskId(position: Int): Int {
        return taskList[position].id
    }

    fun addTask(task: TaskModel) {
        taskList.add(task)
        notifyItemInserted(taskList.size - 1)
    }

    fun updateTask(task: TaskModel) {
        val index = taskList.indexOfFirst { it.id == task.id }
        taskList[index] = task
        notifyItemChanged(index)
    }

    fun removeTask(id: Int) {
        val index = taskList.indexOfFirst { it.id == id }
        taskList.removeAt(index)
        notifyItemRemoved(index)
    }

    fun setTaskList(taskList: MutableList<TaskModel>) {
        this.taskList.clear()
        this.taskList.addAll(taskList)
        notifyItemRangeInserted(0, taskList.size)
    }
}