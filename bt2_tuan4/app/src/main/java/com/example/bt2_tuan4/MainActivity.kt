package com.example.bt2_tuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.bt2_tuan4.model.RetrofitInstance
import com.example.bt2_tuan4.model.Task
import com.example.bt2_tuan4.R
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "taskList") {
                composable("taskList") { TaskListScreen(navController) }
                composable("taskDetail/{taskId}") { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                    taskId?.let { TaskDetailScreen(it, navController) }
                }
            }
        }
    }
}

@Composable
fun TaskListScreen(navController: NavController) {
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getTasks()
                if (response.isSuccessful) {
                    tasks = response.body()?.data ?: emptyList()
                } else {
                    errorMessage = "Lỗi API: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Lỗi khi gọi API: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Danh sách công việc", style = MaterialTheme.typography.headlineMedium)

        when {
            isLoading -> {
                Text(text = "Đang tải dữ liệu...", color = Color.Gray)
            }
            errorMessage != null -> {
                Text(text = errorMessage!!, color = Color.Red)
            }
            tasks.isEmpty() -> {
                NoTaskPlaceholder()
            }
            else -> {
                LazyColumn {
                    items(tasks) { task -> TaskItem(task, navController) }
                }
            }
        }
    }
}

@Composable
fun NoTaskPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.no_task),
                contentDescription = "No Tasks",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "No Tasks Yet!",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Stay productive—add something to do",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewNoTaskPlaceholder() {
    NoTaskPlaceholder()
}


@Composable
fun TaskItem(task: Task, navController: NavController){
    val backgroundColors = listOf(
        Color(0xFFB3E5FC), Color(0xFFFFCDD2),
        Color(0xFFC8E6C9), Color(0xFFFFF59D), Color(0xFFD1C4E9)
    )
    val backgroundColor = backgroundColors[task.id % backgroundColors.size]

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = { navController.navigate("taskDetail/${task.id}")}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Next",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun TaskDetailScreen(taskId: Int, navController: NavController) {
    var task by remember { mutableStateOf<Task?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(taskId) {
        try {
            val response = RetrofitInstance.api.getTasks()
            if (response.isSuccessful) {
                val allTasks = response.body()?.data ?: emptyList()
                task = allTasks.find { it.id == taskId }
            } else {
                errorMessage = "Lỗi API: ${response.code()}"
            }
        } catch (e: Exception) {
            errorMessage = "Lỗi khi gọi API: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF03A9F4), shape = CircleShape) // Màu xanh dương, bo tròn
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Detail",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF03A9F4) // Màu xanh dương
                ),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        when {
            isLoading -> Text(text = "Đang tải dữ liệu...", color = Color.Gray)
            errorMessage != null -> Text(text = errorMessage!!, color = Color.Red)
            task == null -> Text(text = "Không tìm thấy công việc", color = Color.Gray)
            else -> {
                Text(
                    text = task!!.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = task!!.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                // Thẻ chứa thông tin Category, Status, Priority
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color = Color(0xFFFFCDD2), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.ic_category),
                                contentDescription = "Category",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Category", style = MaterialTheme.typography.bodySmall)
                            Text(task!!.category, style = MaterialTheme.typography.titleSmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.ic_status),
                                contentDescription = "Status",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Status", style = MaterialTheme.typography.bodySmall)
                            Text(task!!.status, style = MaterialTheme.typography.titleSmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(R.drawable.ic_priority),
                                contentDescription = "Priority",
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Priority", style = MaterialTheme.typography.bodySmall)
                            Text(task!!.priority, style = MaterialTheme.typography.titleSmall)
                        }
                    }
                }

                // Subtasks
                Text(text = "Subtasks", style = MaterialTheme.typography.titleMedium)
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    task!!.subtasks.forEach { subtask ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(checked = subtask.isCompleted, onCheckedChange = {})
                            Text(
                                text = subtask.title,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                // Attachments
                Text(text = "Attachments", style = MaterialTheme.typography.titleMedium)
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    task!!.attachments.forEach { attachment ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_attachment),
                                contentDescription = "Attachment",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = attachment.fileName,
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}



