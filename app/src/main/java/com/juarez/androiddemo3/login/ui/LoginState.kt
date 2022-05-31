package com.juarez.androiddemo3.login.ui

sealed class LoginState {
    data class Loading(val isLoading: Boolean) : LoginState()
    object Empty : LoginState()
    data class Error(val exception: Throwable) : LoginState()
    data class Success(val data: String) : LoginState()
}