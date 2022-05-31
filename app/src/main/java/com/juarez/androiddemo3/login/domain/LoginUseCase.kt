package com.juarez.androiddemo3.login.domain

import com.juarez.androiddemo3.login.data.LoginRepository
import com.juarez.androiddemo3.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(private val repository: LoginRepository) {

    operator fun invoke(username: String, password: String): Flow<Resource<String>> {
        return repository.login(username, password)
    }
}