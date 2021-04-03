package com.example.github.model

import com.example.github.contract.UserContract
import com.example.github.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserModel : UserContract.Model{

    override fun getUserInfo(login: String, callback: UserContract.Model.UserInfoCallback) {
        val query = "$login in:login"

        val repo = RetrofitService.client?.requestUserInfo(query, 100, 1)

        repo?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let {
                        callback.onResponse(it)
                    }
                } else {
                    callback.onFailure(response.isSuccessful.toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onFailure(t.toString())
            }
        })
    }
}