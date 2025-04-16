package com.example.bttuan7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bttuan7.ui.theme.Bttuan7Theme

val ColorSaver: Saver<Color, Long> = Saver(
    save = { it.toArgb().toLong() }, // Lưu Color thành Long
    restore = { Color(it.toInt()) }  // Khôi phục từ Long thành Color
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bttuan7Theme {
                val navController = rememberNavController()
                // Sử dụng ColorSaver cho selectedColor
                var selectedColor by rememberSaveable(stateSaver = ColorSaver) {
                    mutableStateOf(Color.White)
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "selector",
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        composable("selector") {
                            ThemeSelectorScreen(
                                selectedColor = selectedColor,
                                onColorSelect = { selectedColor = it },
                                onApplyClick = {
                                    navController.navigate("applied")
                                }
                            )
                        }
                        composable("applied") {
                            ThemeAppliedScreen(
                                selectedColor = selectedColor,
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

@Composable
fun ThemeSelectorScreen(
    selectedColor: Color,
    onColorSelect: (Color) -> Unit,
    onApplyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Setting", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        Text(
            "Choosing the right theme sets the tone and personality of your app",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val colors = listOf(Color(0xFF87CEFA), Color(0xFFDA70D6), Color(0xFF2F2F2F))
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color)
                        .border(
                            width = if (selectedColor == color) 3.dp else 0.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onColorSelect(color) }
                )
            }
        }

        Button(onClick = onApplyClick) {
            Text("Apply")
        }
    }
}

@Composable
fun ThemeAppliedScreen(
    selectedColor: Color,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(selectedColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Theme Applied", fontSize = 22.sp, color = Color.White)
        Text(
            "Your app is now using the selected theme!",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true, name = "Selector Screen Preview")
@Composable
fun ThemeSelectorPreview() {
    Bttuan7Theme {
        ThemeSelectorScreen(
            selectedColor = Color(0xFF87CEFA),
            onColorSelect = {},
            onApplyClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Applied Screen Preview")
@Composable
fun ThemeAppliedPreview() {
    Bttuan7Theme {
        ThemeAppliedScreen(
            selectedColor = Color(0xFF2F2F2F), // ví dụ màu Dark
            onBackClick = {}
        )
    }
}

