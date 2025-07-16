package com.example.testmobiledev

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testmobiledev.adapter.UserAdapter
import com.example.testmobiledev.api.ApiClient
import com.example.testmobiledev.api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private var currentPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewUsers)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        userAdapter = UserAdapter(mutableListOf()) { user ->
            val resultIntent = Intent()
            resultIntent.putExtra("selected_name", "${user.first_name} ${user.last_name}")
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        swipeRefresh.setOnRefreshListener {
            currentPage = 1
            userAdapter.clearUsers()
            loadUsers(currentPage)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (!rv.canScrollVertically(1) && !isLoading) {
                    loadUsers(++currentPage)
                }
            }
        })

        loadUsers(currentPage)
    }

    private fun loadUsers(page: Int) {
        isLoading = true
        findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = true

        ApiClient.apiService.getUsers(page).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isLoading = false
                findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = false
                if (response.isSuccessful) {
                    response.body()?.data?.let { userAdapter.addUsers(it) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading = false
                findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).isRefreshing = false
                Toast.makeText(this@ThirdActivity, "Gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
