package com.example.github.model

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.github.contract.UserContract
import java.util.regex.Pattern

class UserViewModel(application: Application): AndroidViewModel(application) {
    var userModel = UserModel()
    var login = ObservableField<String>()
    var users = MutableLiveData<List<UserInfo>>()
    var favorites = MutableLiveData<List<UserInfo>>()

    fun onClickButton() {
        userModel.let {
            if (login.get() != null && !Pattern.matches("\\s*", login.get().toString())) {
                it.getUserInfo(login.get()!!, object : UserContract.Model.UserInfoCallback {
                    override fun onResponse(responseBody: User) {
                        users.value = responseBody.items
                    }

                    override fun onFailure(error: String?) {
                        users.value = null
                    }
                })
            }
        }
    }
}