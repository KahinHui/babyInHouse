package com.kahin.petinthehouse.sign

/**
 * Authentication result : success (user details) or error message.
 */
data class SignUpResult(
    val success: SignedUpUserView? = null,
    val error: String? = null
)