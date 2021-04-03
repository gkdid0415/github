package com.example.github.network

object RetrofitService {
    private const val GITHUB_URL = "https://api.github.com"

    val client = RetrofitClient().getClient(GITHUB_URL)?.create(RetrofitInterface::class.java)
}