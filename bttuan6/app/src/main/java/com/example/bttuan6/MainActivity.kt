package com.example.bttuan6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bttuan6.ui.theme.UthSmartTasksTheme
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

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
                                    navController.navigate("edit-task/$index")
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
                            route = "edit-task/{index}",
                            arguments = listOf(navArgument("index") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val index = backStackEntry.arguments?.getInt("index") ?: -1
                            val tasks by taskViewModel.tasks.collectAsState() // Thu thập tasks bằng collectAsState
                            val task = tasks.getOrNull(index)
                            if (task != null) {
                                EditTaskScreen(
                                    viewModel = taskViewModel,
                                    taskIndex = index,
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