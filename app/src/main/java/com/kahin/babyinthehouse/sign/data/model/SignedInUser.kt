package com.kahin.babyinthehouse.sign.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class SignedInUser(
    val userId: String,
    val email: String,
    val password: String,
    val userName: String
)