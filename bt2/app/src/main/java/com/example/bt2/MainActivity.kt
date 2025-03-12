package com.example.bt2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Khai báo các biến toàn cục
    private var txt1: EditText? = null
    private var txt2: EditText? = null
    private var btn1: Button? = null
    private var tv1: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        txt1 = findViewById(R.id.edtHoTen)
        txt2 = findViewById(R.id.edtTuoi)
        btn1 = findViewById(R.id.button)
        tv1 = findViewById(R.id.textView2)

        // Bắt sự kiện click cho button
        btn1!!.setOnClickListener {
            xetTuoi()
        }
    }

    private fun xetTuoi() {
        val str1 = txt1!!.text.toString().trim()
        val str2 = txt2!!.text.toString().trim()

        // Kiểm tra nếu ô nhập trống
        if (str1.isEmpty() || str2.isEmpty()) {
            tv1!!.text = "Vui lòng nhập đầy đủ thông tin!"
            return
        }

        // Chuyển đổi tuổi từ chuỗi sang số
        val tuoi = try {
            str2.toFloat()
        } catch (e: NumberFormatException) {
            tv1!!.text = "Vui lòng nhập tuổi hợp lệ!"
            return
        }

        // Xác định nhóm tuổi
        val ketQua = when {
            tuoi > 65 -> "Người già"
            tuoi in 6.0..65.0 -> "Người lớn"
            tuoi in 2.0..6.0 -> "Trẻ em"
            tuoi > 0 && tuoi < 2 -> "Em bé"
            else -> "Tuổi không hợp lệ!"
        }

        // Hiển thị kết quả
        tv1!!.text = "$str1 $str2 tuổi thuộc nhóm: $ketQua"
    }
}
