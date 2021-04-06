package com.example.github.model

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.github.contract.UserContract
import com.example.github.database.AppDatabase
import java.util.regex.Pattern

class UserViewModel(application: Application) : AndroidViewModel(application) {
    var userModel = UserModel()
    var db = AppDatabase.getInstance(application.applicationContext)

    var username = ObservableField<String>() // Github 사용자 검색 화면 검색어
    var favoritename = ObservableField<String>() // 로컬 즐겨찾기 검색 화면 검색어
    var users = MutableLiveData<List<UserInfo>>() // 검색된 Github 사용자
    var favorites = MutableLiveData<List<FavoriteInfo>>() // 검색된 로컬 즐겨찾기
    var change = ObservableBoolean(false) // 뷰에 변경된 사항이 있으면 true

    /**
     * Github 사용자 검색
     */
    fun requestUserInfo() {
        userModel.let {
            if (username.get() != null && !Pattern.matches("\\s*", username.get().toString())) {
                it.getUserInfo(username.get()!!, object : UserContract.Model.UserInfoCallback {
                    override fun onResponse(responseBody: User) {
                        users.postValue(responseBody.items.sortedWith(
                            compareBy { UserInfo -> UserInfo.login }))
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
        if (favoritename.get() != null && !Pattern.matches("\\s*", favoritename.get().toString())) {
            favorites.postValue(db!!.favorite().getFavoriteInfo(favoritename.get()!!).sortedWith(
                compareBy { FavoriteInfo -> FavoriteInfo.login }))
        }
    }
}