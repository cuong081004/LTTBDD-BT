package com.example.bt2_tuan3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bt2_tuan3.ui.theme.Bt2_tuan3Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bt2_tuan3Theme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("screen") { Screen(navController) }
        composable("next_screen1") { NextScreen1(navController) }
        composable("next_screen2") { NextScreen2(navController) }
        composable("home") { Home() }

    }
}

// Màn hình Splash
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Chờ 2 giây
        navController.navigate("screen") // Điều hướng đến màn hình
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.b),
            contentDescription = "UTH Logo",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "UTH SmartTasks",
            fontSize = 24.sp
        )
    }
}

// Màn hình chính
@Composable
fun Screen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(R.drawable.a),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Easy Time Management",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first.",
                textAlign = TextAlign.Center,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Button(
            onClick = { navController.navigate("next_screen1") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// Màn hình tiếp theo 1
@Composable
fun NextScreen1(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(R.drawable.b),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Increase Work Effectiveness",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Time management and the determination of more important tasks will give your job statistics better and always improve",
                textAlign = TextAlign.Center,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔙 Nút "Return" quay lại màn hình trước
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_circle_left_24),
                    contentDescription = "Return",
                    tint = Color(0xFF007BFF), // Màu xanh dương
                    modifier = Modifier.size(70.dp) // Tăng kích thước icon
                )
            }

            // 🔜 Nút "Next" chuyển sang màn hình tiếp theo
            Button(
                onClick = { navController.navigate("next_screen2") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ✅ Màn hình tiếp theo 2 (bổ sung)
@Composable
fun NextScreen2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(R.drawable.b),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Reminder Notification",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set",
                textAlign = TextAlign.Center,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔙 Nút "Return" quay lại màn hình trước
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_circle_left_24),
                    contentDescription = "Return",
                    tint = Color(0xFF007BFF), // Màu xanh dương
                    modifier = Modifier.size(70.dp) // Tăng kích thước icon
                )
            }

            // 🔜 Nút "Next" chuyển sang màn hình tiếp theo
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Home() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Page",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Bt2_tuan3Theme {
        SplashScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Bt2_tuan3Theme {
        Screen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun NextScreen1Preview() {
    Bt2_tuan3Theme {
        NextScreen1(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun NextScreen2Preview() {
    Bt2_tuan3Theme {
        NextScreen2(rememberNavController())
    }
}
