package com.kahin.petinthehouse.me

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kahin.petinthehouse.App

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class MeViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeViewModel::class.java)) {
            return MeViewModel(
                repository = MeRepository(
                    userDao = App.appDb.userDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}