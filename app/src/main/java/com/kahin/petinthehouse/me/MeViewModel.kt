package com.kahin.petinthehouse.me

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kahin.petinthehouse.base.EventData
import com.kahin.petinthehouse.persistence.db.User
import com.kahin.petinthehouse.persistence.db.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeViewModel(private val repository: MeRepository) : ViewModel() {

    private val _user = MutableLiveData<EventData<User>>()
    val user: LiveData<EventData<User>> = _user

    fun findUser(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.findUser(userName)
            Log.d("aaa", "000000")

            launch(Dispatchers.Main) {
                Log.d("aaa", "2222222 ${user.email}")
                _user.value = EventData(user)
            }
        }
    }
}

class MeRepository(private val userDao: UserDao) {

    fun findUser(userName: String): User {
        Log.d("aaa", "111111")
        return userDao.findByUserName(userName)
    }
}