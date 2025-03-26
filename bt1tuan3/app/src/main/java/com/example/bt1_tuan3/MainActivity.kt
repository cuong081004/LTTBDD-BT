package com.example.bt1_tuan3

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bt1_tuan3.ui.theme.Bt1tuan3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bt1tuan3Theme {
                AppNavigation()
            }
        }
    }
}

// Navigation setup
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("next_screen") { NextScreen(navController) }
        composable("text_detail") { TextDetailScreen(navController) }
    }
}


// Home Screen
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp)) // Khoảng cách trên cùng

        Image(
            painter = painterResource(R.drawable.a),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Jetpack Compose",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                textAlign = TextAlign.Center,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Button(
            onClick = { navController.navigate("next_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "I'm ready",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

// Next Screen (màn hình tiếp theo)
@Composable
fun NextScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Tiêu đề chính
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = "UI Components List",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF007BFF),
            modifier = Modifier.padding(bottom = 12.dp),
            textAlign = TextAlign.Center,
            )
        }

        // Danh mục
        SectionTitle("Display")
        ComponentItem("Text", "Displays text"){
            navController.navigate("text_detail")
        }
        ComponentItem("Image", "Displays an image"){
            navController.navigate("text_detail")
        }

        SectionTitle("Input")
        ComponentItem("TextField", "Input field for text"){
            navController.navigate("text_detail")
        }
        ComponentItem("PasswordField", "Input field for passwords"){
            navController.navigate("text_detail")
        }

        SectionTitle("Layout")
        ComponentItem("Column", "Arranges elements vertically"){
            navController.navigate("text_detail")
        }
        ComponentItem("Row", "Arranges elements horizontally"){
            navController.navigate("text_detail")
        }
    }
}

// Tiêu đề danh mục
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
    )
}

// Thành phần hiển thị từng mục
@Composable
fun ComponentItem(title: String, description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFF90CAF9), RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Thanh tiêu đề có nút quay lại
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f)) // Đẩy tiêu đề vào giữa
                    Text(
                        text = "Text Detail",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f) // Chiếm không gian để cân bằng với Icon
                    )
                    Spacer(modifier = Modifier.weight(1f)) // Cân bằng không gian trống
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Blue
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nội dung hiển thị văn bản
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StyledText()
        }
    }
}

@Composable
fun StyledText() {
    Text(
        text = buildAnnotatedString {
            append("The ")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                append("quick ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFFBB7B19))) {
                append("Brown ")
            }
            append("\nfox j u m p s ")
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)) {
                append("over ")
            }
            append("\nthe ")
            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline)) {
                append("lazy ")
            }
            append("dog.")
        },
        fontSize = 18.sp,
        textAlign = TextAlign.Center
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Bt1tuan3Theme {
        HomeScreen(navController = rememberNavController())
    }
}
@Preview(showBackground = true)
@Composable
fun NextScreenPreview() {
    Bt1tuan3Theme {
        NextScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun TextDetailScreenPreview() {
    Bt1tuan3Theme {
        TextDetailScreen(navController = rememberNavController())
    }
}
