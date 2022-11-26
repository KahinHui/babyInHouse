package com.kahin.babyinthehouse.sign.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kahin.babyinthehouse.App
import com.kahin.babyinthehouse.sign.data.LoginDataSource
import com.kahin.babyinthehouse.sign.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(),
                    userDao = App.appDb.userDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}