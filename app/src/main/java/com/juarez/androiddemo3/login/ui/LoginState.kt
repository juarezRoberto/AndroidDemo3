package com.juarez.androiddemo3.login.ui

import com.juarez.androiddemo3.login.domain.TokenWrapper

sealed class LoginState {
    object Loading : LoginState()
    object Empty : LoginState()
    data class Error(val exception: Throwable) : LoginState()
    data class Success(val tokenWrapper: TokenWrapper) : LoginState()
}