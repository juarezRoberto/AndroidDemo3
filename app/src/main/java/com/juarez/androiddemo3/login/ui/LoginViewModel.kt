package com.juarez.androiddemo3.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.androiddemo3.login.domain.LoginUseCase
import com.juarez.androiddemo3.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        _loginState.value = LoginState.Loading
        loginUseCase(username, password).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _loginState.value = LoginState.Error(Exception(resource.throwable))
                }
                is Resource.Success -> {
                    _loginState.value = LoginState.Success(resource.data)
                }
            }
        }.catch { exception ->
            _loginState.value = LoginState.Error(exception)
        }.launchIn(viewModelScope)
    }
}