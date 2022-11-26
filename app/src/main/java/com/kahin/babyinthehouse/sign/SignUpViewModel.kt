package com.kahin.babyinthehouse.sign

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kahin.babyinthehouse.R
import com.kahin.babyinthehouse.persistence.db.User
import com.kahin.babyinthehouse.persistence.db.dao.UserDao
import com.kahin.babyinthehouse.sign.data.Result
import com.kahin.babyinthehouse.sign.data.model.SignedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import kotlin.coroutines.CoroutineContext

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {

    private val _signUpForm = MutableLiveData<SignUpFormState>()
    val signUpFormState: LiveData<SignUpFormState> = _signUpForm

    private val _signUpResult = MutableLiveData<SignUpResult>()
    val signUpResult: LiveData<SignUpResult> = _signUpResult

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.signUp(email, password, username)

            launch(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> _signUpResult.value = SignUpResult(success = SignedUpUserView(displayName = result.data.userName))
                    is Result.Error -> _signUpResult.value = SignUpResult(error = result.msg)
                }
            }
        }
    }

    fun signUpDataChanged(email: String, password: String, username: String) {
        if (!isEmailValid(email)) {
            _signUpForm.value = SignUpFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = SignUpFormState(passwordError = R.string.invalid_password)
        } else if (!isUsernameValid(username)) {
            _signUpForm.value = SignUpFormState(usernameError = R.string.invalid_username)
        } else
            _signUpForm.value = SignUpFormState(isDataValid = true)
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