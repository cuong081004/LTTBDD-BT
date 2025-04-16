package com.example.bt2tuan7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bt2tuan7.ui.theme.UthSmartTasksTheme

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as UTHApplication).database.taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UthSmartTasksTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        composable("home") {
                            HomeScreen(
                                viewModel = taskViewModel,
                                onUserProfileClick = {
                                    navController.navigate("user-profile")
                                },
                                onTaskClick = { index, task ->
                                    navController.navigate("edit-task/${task.id}")
                                }
                            )
                        }
                        composable("user-profile") {
                            UserProfileScreen(
                                viewModel = taskViewModel,
                                onAddTaskClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = "edit-task/{taskId}",
                            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
                            val tasks by taskViewModel.tasks.collectAsState()
                            val task = tasks.find { it.id == taskId }
                            if (task != null) {
                                EditTaskScreen(
                                    viewModel = taskViewModel,
                                    taskId = taskId,
                                    task = task,
                                    onBackClick = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}