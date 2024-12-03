package com.example.myapplication

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.myapplication.Model.TaskModel
import com.example.myapplication.databinding.AddTaskBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheetFragment(
    private val onTaskAdded: (TaskModel) -> Unit,
    private val onTaskUpdated: (TaskModel) -> Unit,
    private val task: TaskModel? = null,
    private val action: String,
    private val onDismissCallback: () -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: AddTaskBottomsheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddTaskBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etNewTask.requestFocus()
        if (task != null) {
            binding.etNewTask.setText(task.taskText)
        }

        binding.etNewTask.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    binding.btnAddTask.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_blue_light
                        )
                    )
                } else {
                    binding.btnAddTask.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.darker_gray
                        )
                    )
                }
            }

        })

        binding.btnAddTask.setOnClickListener {
            val taskText = binding.etNewTask.text.toString()
            if (taskText.isNotEmpty()) {
                when(action){
                    "ADD" -> {
                        val newTask = TaskModel(1, false, taskText)
                        onTaskAdded(newTask)
                        dismiss()
                    }
                    "UPDATE" -> {
                        task?.also {
                            onTaskUpdated(TaskModel(it.id, task.isDone, taskText))
                        }
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissCallback()
    }
}