package com.example.bt2tuan7

import com.example.bt2tuan7.data.TaskEntity

data class Task(
    val id: Int = 0, // Thêm id để ánh xạ với TaskEntity
    val title: String,
    val description: String,
    val color: Long
)

// Hàm chuyển đổi từ TaskEntity sang Task
fun TaskEntity.toTask() = Task(
    id = id,
    title = title,
    description = description,
    color = color
)

// Hàm chuyển đổi từ Task sang TaskEntity
fun Task.toTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    color = color
)