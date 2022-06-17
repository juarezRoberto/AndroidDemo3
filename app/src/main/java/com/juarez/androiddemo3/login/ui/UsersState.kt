package com.juarez.androiddemo3.login.ui

sealed class GetUsersState {
    object Empty : GetUsersState()
    data class Loading(val isLoading: Boolean) : GetUsersState()
    data class Error(val throwable: Throwable) : GetUsersState()
    data class Success(val users: List<Any>) : GetUsersState()
}