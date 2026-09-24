package com.example.myapp.data

data class User(
    val id: Long = 0,
    val name: String,
    val emailOrPhone: String,
    val password: String
)

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    object EmptyFields : LoginResult()
    object UserNotFound : LoginResult()
    object WrongPassword : LoginResult()
}

sealed class RegisterResult {
    data class Success(val userId: Long) : RegisterResult()
    object EmptyFields : RegisterResult()
    object AlreadyExists : RegisterResult()
    object TermsNotAgreed : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}
