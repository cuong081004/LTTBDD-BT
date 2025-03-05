package com.example.bt1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liên kết các thành phần UI
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView userName = findViewById(R.id.user_name);
        TextView userLocation = findViewById(R.id.user_location);

        // Gán dữ liệu động (nếu cần)
        userName.setText("Gia Cường");
        userLocation.setText("Ho Chi Minh City, Vietnam");
        profileImage.setImageResource(R.drawable.a);  // Cập nhật ảnh đại diện từ code
    }
}
