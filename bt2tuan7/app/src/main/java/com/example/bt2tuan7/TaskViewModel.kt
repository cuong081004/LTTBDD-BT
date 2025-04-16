package com.example.bt2tuan7

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt2tuan7.data.TaskDao
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    private val colors = listOf(
        Color(0xFF87CEFA).toArgb().toLong(), // Blue
        Color(0xFFFF9999).toArgb().toLong(), // Pink
        Color(0xFF99FF99).toArgb().toLong()  // Green
    )

    val tasks: StateFlow<List<Task>> = taskDao.getAllTasks()
        .map { taskEntities -> taskEntities.map { it.toTask() } }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), // Stop collecting after 5 seconds of inactivity
            initialValue = emptyList() // Initial value for the StateFlow
        )

    init {
        // Khởi tạo dữ liệu ban đầu nếu cần
        viewModelScope.launch {
            if (taskDao.getAllTasks().map { it.isEmpty() }.firstOrNull() == true) {
                val initialTasks = listOf(
                    Task(0, "Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[0]),
                    Task(0, "Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[1]),
                    Task(0, "Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[2]),
                    Task(0, "Complete Android Project", "Finish the UI, integrate API, and write documentation", colors[1])
                )
                initialTasks.forEach { task ->
                    taskDao.insertTask(task.toTaskEntity())
                }
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            val newTask = Task(
                id = 0, // Room sẽ tự động tạo id
                title = title,
                description = description,
                color = colors.random()
            )
            taskDao.insertTask(newTask.toTaskEntity())
        }
    }

    fun updateTask(taskId: Int, newTitle: String, newDescription: String) {
        viewModelScope.launch {
            val taskToUpdate = tasks.value.find { it.id == taskId }
            if (taskToUpdate != null) {
                val updatedTask = taskToUpdate.copy(
                    title = newTitle,
                    description = newDescription
                )
                taskDao.updateTask(updatedTask.toTaskEntity())
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            taskDao.deleteTask(taskId)
        }
    }
}