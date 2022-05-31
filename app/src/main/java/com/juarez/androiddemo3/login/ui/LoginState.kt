package com.juarez.androiddemo3.login.ui

sealed class LoginState {
    object Loading : LoginState()
    object Empty : LoginState()
    data class Error(val exception: Throwable) : LoginState()
    data class Success(val jwt: String) : LoginState()
}