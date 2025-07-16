package com.example.testmobiledev.api

import com.example.testmobiledev.model.User

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)
