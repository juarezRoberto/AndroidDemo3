package com.juarez.androiddemo3.login.ui

import com.juarez.androiddemo3.login.domain.TokenWrapper

sealed class LoginState {
    object Loading : LoginState()
    object Empty : LoginState()
    data class Error(val throwable: Throwable) : LoginState()
    data class Success(val tokenWrapper: TokenWrapper) : LoginState()
    object Completed : LoginState()
}