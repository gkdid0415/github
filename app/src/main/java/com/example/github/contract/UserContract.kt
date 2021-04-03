package com.example.github.contract

import com.example.github.model.User

interface UserContract {

    interface Model {

        /**
         * Github 사용자 검색 요청 결과 Callback
         */
        interface UserInfoCallback {
            fun onResponse(responseBody: User)

            fun onFailure(error: String?)
        }

        /**
         * Github 사용자 검색 API 호출
         * 기능 요구사항
         * 1) 검색 API에 사용되는 필드는 사용자 이름으로 제한합니다.
         * 2) page는 1, per_page는 100으로 설정합니다.
         */
        fun getUserInfo(login: String, callback: UserInfoCallback)
    }
}