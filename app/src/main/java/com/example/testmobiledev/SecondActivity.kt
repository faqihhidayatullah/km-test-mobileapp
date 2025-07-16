package com.example.testmobiledev

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvSelectedUser: TextView
    private lateinit var btnChooseUser: Button


    private val chooseUserLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedName = result.data?.getStringExtra("selected_name")
            tvSelectedUser.text = selectedName ?: ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            finish() // Kembali ke MainActivity
        }


        val name = intent.getStringExtra("EXTRA_NAME") ?: ""

        findViewById<TextView>(R.id.tvWelcome).text = "Welcome"
        tvName = findViewById(R.id.tvName)
        tvName.text = name

        tvSelectedUser = findViewById(R.id.tvSelectedUser)
        btnChooseUser = findViewById(R.id.btnChooseUser)

        btnChooseUser.setOnClickListener {
            chooseUserLauncher.launch(Intent(this, ThirdActivity::class.java))
        }
    }
}
