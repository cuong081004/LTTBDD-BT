package com.example.bt3

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvSelectedUser: TextView
    private lateinit var listViewBooks: ListView
    private lateinit var btnDoi: Button
    private lateinit var btnThem: Button

    private val users = arrayOf("Nguyen Van A", "Tran Van B", "Le Thi C")
    private val books = mutableListOf("Sách 01", "Sách 02", "Sách 03", "Sách 04")
    private val borrowedBooks = mutableMapOf<String, MutableList<String>>()

    private lateinit var bookAdapter: ArrayAdapter<String>

    private var selectedUser: String = "Nguyen Van A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSelectedUser = findViewById(R.id.tvSelectedUser)
        listViewBooks = findViewById(R.id.listViewBooks)
        btnDoi = findViewById(R.id.btnDoi)
        btnThem = findViewById(R.id.btnThem)

        // Khi bấm vào "Đổi", hiển thị danh sách nhân viên
        btnDoi.setOnClickListener {
            showUserSelectionDialog()
        }

        // Adapter cho ListView
        bookAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, books)
        listViewBooks.adapter = bookAdapter
        listViewBooks.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        btnThem.setOnClickListener {
            borrowBooks(selectedUser)
        }
    }

    // Hiển thị danh sách nhân viên để chọn
    private fun showUserSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn nhân viên")
        builder.setItems(users) { _, which ->
            selectedUser = users[which]
            tvSelectedUser.text = selectedUser
            updateBookSelection(selectedUser)
        }
        builder.show()
    }

    // Cập nhật danh sách sách mượn của nhân viên
    private fun borrowBooks(user: String) {
        val checkedBooks = mutableListOf<String>()
        for (i in 0 until listViewBooks.count) {
            if (listViewBooks.isItemChecked(i)) {
                checkedBooks.add(books[i])
            }
        }
        borrowedBooks[user] = checkedBooks
        Toast.makeText(this, "$user đã mượn ${checkedBooks.size} sách", Toast.LENGTH_SHORT).show()
    }

    // Hiển thị sách đã mượn của nhân viên
    private fun updateBookSelection(user: String) {
        val borrowed = borrowedBooks[user] ?: mutableListOf()
        for (i in 0 until listViewBooks.count) {
            listViewBooks.setItemChecked(i, books[i] in borrowed)
        }
    }
}
