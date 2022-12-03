package com.kahin.petinthehouse.sign

import android.util.Patterns
import androidx.lifecycle.*
import com.kahin.petinthehouse.R
import com.kahin.petinthehouse.base.EventData
import com.kahin.petinthehouse.persistence.db.User
import com.kahin.petinthehouse.persistence.db.dao.UserDao
import com.kahin.petinthehouse.sign.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {

    private val _signUpForm = MutableLiveData<EventData<SignUpFormState>>()
    val signUpFormState: LiveData<EventData<SignUpFormState>> = _signUpForm

    private val _signUpResult = MutableLiveData<EventData<SignUpResult>>()
    val signUpResult: LiveData<EventData<SignUpResult>> = _signUpResult

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.signUp(email, password, username)

            launch(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> _signUpResult.value = EventData(SignUpResult(success = SignedUpUserView(displayName = result.data.userName)))
                    is Result.Error -> _signUpResult.value = EventData(SignUpResult(error = result.msg))
                    else -> {}
                }
            }
        }
    }

    fun signUpDataChanged(email: String, password: String, username: String) {
        if (!isEmailValid(email)) {
            _signUpForm.value = EventData(SignUpFormState(emailError = R.string.invalid_email))
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = EventData(SignUpFormState(passwordError = R.string.invalid_password))
        } else if (!isUsernameValid(username)) {
            _signUpForm.value = EventData(SignUpFormState(usernameError = R.string.invalid_username))
        } else
            _signUpForm.value = EventData(SignUpFormState(isDataValid = true))
    }

    // A placeholder email validation check
    private fun isEmailValid(email: String): Boolean {
        return if (email.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    // A placeholder username validation check
    private fun isUsernameValid(username: String): Boolean {
        return username.isNotBlank()
    }
}

class SignUpRepository(val dataSource: SignUpDataSource, val userDao: UserDao) {

    var user: User? = null
        private set

    init {
        user = null
    }

    fun signUp(
        email: String,
        password: String,
        username: String
    ): Result<User> {
        val dbUser = userDao.findByEmail(email)
        if (dbUser != null) {
            return Result.Error("Email is existed")
        } else {
            userDao.insert(User(UUID.randomUUID().toString(), email, password, username))

            val newUser = userDao.findByEmail(email)
            val result = if (newUser != null) {
                Result.Success(newUser)//dataSource.signUp(email, password, username)
            } else {
                Result.Error("Sign up failed")
            }

            if (result is Result.Success) {
                setSignedInUser(result.data)
            }

            return result
        }
    }

    private fun setSignedInUser(user: User) {
        this.user = user
    }
}

class SignUpDataSource {

    fun signUp(
        email: String,
        password: String,
        username: String
    ): Result<User> {
        return try {
            val user = User(UUID.randomUUID().toString(), email, password, username)

            Result.Success(user)
        } catch (e: Throwable) {
            Result.Error("Error signing up", IOException("Error signing up", e))
        }
    }
}