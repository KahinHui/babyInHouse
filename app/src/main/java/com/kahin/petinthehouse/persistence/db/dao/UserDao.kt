package com.kahin.petinthehouse.persistence.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kahin.petinthehouse.persistence.db.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE email LIKE :email")
    fun findByEmail(email: String): User?

    @Query("SELECT * FROM user WHERE user_name LIKE :userName")
    fun findByUserName(userName: String): User

    @Delete
    fun delete(user: User)
}