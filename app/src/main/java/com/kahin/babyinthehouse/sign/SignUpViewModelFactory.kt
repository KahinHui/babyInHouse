package com.kahin.babyinthehouse.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kahin.babyinthehouse.App

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class SignUpViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(
                repository = SignUpRepository(
                    dataSource = SignUpDataSource(),
                    userDao = App.appDb.userDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}