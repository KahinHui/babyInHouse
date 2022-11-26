package com.kahin.babyinthehouse.sign.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.kahin.babyinthehouse.R
import com.kahin.babyinthehouse.sign.data.LoginRepository
import com.kahin.babyinthehouse.sign.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // can be launched in a separate asynchronous job
            val result = loginRepository.login(username, password)

            launch(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.userName))
                    is Result.Error -> _loginResult.value = LoginResult(error = result.msg)
                }
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isUserNameValid(email)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(email: String): Boolean {
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
}