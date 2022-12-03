package com.kahin.petinthehouse.sign.data

import com.kahin.petinthehouse.persistence.db.User
import com.kahin.petinthehouse.persistence.db.dao.UserDao

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val userDao: UserDao) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun login(email: String, password: String): Result<User> {
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