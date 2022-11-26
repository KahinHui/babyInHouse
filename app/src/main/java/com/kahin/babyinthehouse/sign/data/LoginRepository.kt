package com.kahin.babyinthehouse.sign.data

import com.kahin.babyinthehouse.persistence.db.User
import com.kahin.babyinthehouse.persistence.db.dao.UserDao
import com.kahin.babyinthehouse.sign.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, val userDao: UserDao) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(email: String, password: String): Result<User> {
        // handle login
//        val result = dataSource.login(username, password)
        val dbUser = userDao.findByEmail(email)
        val result = if (dbUser != null) {
            if (dbUser.email == email && dbUser.password == password) {
                setLoggedInUser(dbUser)

                Result.Success(dbUser)
            } else {
                Result.Error("Invalid email or password", NullPointerException("Invalid email or password"))
            }
        } else {
            Result.Error("Invalid email", NullPointerException("Invalid email"))
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: User) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}