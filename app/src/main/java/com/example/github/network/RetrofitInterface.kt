package com.example.github.network

import com.example.github.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitInterface {

    /**
     * Github 사용자 검색
     * @Query query : Qualifiers allow you to limit your search to specific areas of GitHub.
     * @Query per_page : Results per page (max 100).
     * @Query page : Page number of the results to fetch.
     */
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/users")
    fun requestUserInfo(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<User>
}