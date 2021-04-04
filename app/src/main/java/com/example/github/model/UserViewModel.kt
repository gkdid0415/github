package com.example.github.model

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.github.contract.UserContract
import com.example.github.database.AppDatabase
import java.util.regex.Pattern

class UserViewModel(application: Application) : AndroidViewModel(application) {
    var userModel = UserModel()
    var db = AppDatabase.getInstance(application.applicationContext)

    var username = ObservableField<String>() // 검색어
    var users = MutableLiveData<List<UserInfo>>() // 검색된 Github 사용자
    var favorites = MutableLiveData<List<FavoriteInfo>>() // 검색된 로컬 즐겨찾기

    /**
     * Github 사용자 검색
     */
    fun requestUserInfo() {
        userModel.let {
            if (username.get() != null && !Pattern.matches("\\s*", username.get().toString())) {
                it.getUserInfo(username.get()!!, object : UserContract.Model.UserInfoCallback {
                    override fun onResponse(responseBody: User) {
                        users.postValue(responseBody.items.sortedWith(
                            compareBy(String.CASE_INSENSITIVE_ORDER) { UserInfo -> UserInfo.login }))
                    }

                    override fun onFailure(error: String?) {
                        users.postValue(null)
                    }
                })
            }
        }
    }

    /**
     * 로컬 즐겨찾기 검색
     */
    fun requestFavoriteInfo() {
        if (username.get() != null && !Pattern.matches("\\s*", username.get().toString())) {
            favorites.postValue(db!!.favorite().getFavoriteInfo(username.get()!!).sortedWith(
                compareBy(String.CASE_INSENSITIVE_ORDER) { FavoriteInfo -> FavoriteInfo.login }))
        }
    }
}