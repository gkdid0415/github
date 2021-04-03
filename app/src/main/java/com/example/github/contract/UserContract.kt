package com.example.github.contract

import com.example.github.model.User

interface UserContract {

    interface Model {

        interface UserInfoCallback {
            fun onResponse(responseBody: User)

            fun onFailure(error: String?)
        }

        fun getUserInfo(login: String, callback: UserInfoCallback)
    }
}