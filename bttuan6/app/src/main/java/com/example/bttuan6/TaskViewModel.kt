package com.example.bttuan6

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Task(
    val title: String,
    val description: String,
    val color: Long
)

class TaskViewModel : ViewModel() {
    private val colors = listOf(
        Color(0xFF87CEFA).toArgb().toLong(), // Blue
        Color(0xFFFF9999).toArgb().toLong(), // Pink
        Color(0xFF99FF99).toArgb().toLong()  // Green
    )

    private val initialTasks = listOf(
        Task("Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[0]),
        Task("Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[1]),
        Task("Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[2]),
        Task("Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[1])
    )

    private val _tasks = MutableStateFlow<List<Task>>(initialTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(title: String, description: String) {
        val newTask = Task(
            title = title,
            description = description,
            color = colors.random()
        )
        _tasks.value = _tasks.value + newTask
    }

    fun updateTask(index: Int, newTitle: String, newDescription: String) {
        val currentTasks = _tasks.value.toMutableList()
        if (index in currentTasks.indices) {
            val updatedTask = currentTasks[index].copy(
                title = newTitle,
                description = newDescription
            )
            currentTasks[index] = updatedTask
            _tasks.value = currentTasks
        }
    }

    fun deleteTask(index: Int) {
        val currentTasks = _tasks.value.toMutableList()
        if (index in currentTasks.indices) {
            currentTasks.removeAt(index)
            _tasks.value = currentTasks
        }
    }
}