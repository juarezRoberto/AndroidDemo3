package com.juarez.androiddemo3.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.androiddemo3.login.domain.LoginUseCase
import com.juarez.androiddemo3.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _login = MutableStateFlow<LoginState>(LoginState.Empty)
    val login = _login.asStateFlow()

    fun login(username: String, password: String) {
        _login.value = LoginState.Loading(true)
        loginUseCase(username, password).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _login.value = LoginState.Loading(false)
                    _login.value = LoginState.Error(Exception(resource.throwable))
                }
                is Resource.Success -> {
                    _login.value = LoginState.Loading(false)
                    _login.value = LoginState.Success(resource.data)
                }
            }
        }.catch { exception ->
            _login.value = LoginState.Loading(false)
            _login.value = LoginState.Error(exception)
        }.launchIn(viewModelScope)
    }
}