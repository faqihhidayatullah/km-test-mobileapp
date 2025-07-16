package com.example.testmobiledev

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etSentence: EditText
    private lateinit var btnCheck: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etSentence = findViewById(R.id.etSentence)
        btnCheck = findViewById(R.id.btnCheck)
        btnNext = findViewById(R.id.btnNext)

        // Tombol Periksa Palindrome
        btnCheck.setOnClickListener {
            val kalimat = etSentence.text.toString()
            if (isPalindrome(kalimat)) {
                showDialog("isPalindrome")
            } else {
                showDialog("not palindrome")
            }
        }

        // Tombol Berikutnya → ke Layar Kedua
        btnNext.setOnClickListener {
            val nama = etName.text.toString()
            if (nama.isNotEmpty()) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("EXTRA_NAME", nama)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Result")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    // Cek palindrome hanya berdasarkan huruf
    private fun isPalindrome(input: String): Boolean {
        val clean = input.filter { it.isLetter() }.lowercase()
        return clean == clean.reversed()
    }
}
