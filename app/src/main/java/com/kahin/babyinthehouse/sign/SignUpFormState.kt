package com.kahin.babyinthehouse.sign

/**
 * Data validation state of the login form.
 */
data class SignUpFormState (val emailError: Int? = null,
                            val passwordError: Int? = null,
                            val usernameError: Int? = null,
                            val isDataValid: Boolean = false)