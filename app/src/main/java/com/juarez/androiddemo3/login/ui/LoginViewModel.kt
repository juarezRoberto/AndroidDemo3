package com.juarez.androiddemo3.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.androiddemo3.login.data.LoginRepository
import com.juarez.androiddemo3.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val repository: LoginRepository
) : ViewModel() {

    private val _usersState = MutableStateFlow<GetUsersState>(GetUsersState.Empty)
    val usersState = _usersState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        _loginState.update { LoginState.Loading }

        loginUseCase(username, password).onEach { token ->
            _loginState.update { LoginState.Success(token) }
        }.catch { throwable ->
            _loginState.update { LoginState.Error(throwable) }
        }.onCompletion {
            _loginState.update { LoginState.Completed }
        }.launchIn(viewModelScope)
    }

    fun getUsers() {
        viewModelScope.launch {
            _usersState.update { GetUsersState.Loading(true) }
            try {
                val users = repository.getUsers()
                _usersState.update { GetUsersState.Success(users) }
            } catch (e: Throwable) {
                _usersState.update { GetUsersState.Error(e) }
            } finally {
                _usersState.update { GetUsersState.Loading(false) }
            }
        }
    }
}