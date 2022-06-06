package com.juarez.androiddemo3.login.data

import com.juarez.androiddemo3.api.MacroPayApi
import com.juarez.androiddemo3.login.domain.TokenWrapper
import com.juarez.androiddemo3.login.domain.toModel
import com.juarez.androiddemo3.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

interface LoginRepository {
    fun login(username: String, password: String): Flow<Resource<TokenWrapper>>
}


class LoginRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val macroPayApi: MacroPayApi
) : LoginRepository {

    override fun login(username: String, password: String): Flow<Resource<TokenWrapper>> = flow {
        delay(1000)

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", username)
            .addFormDataPart("password", password)
            .build()

        val response = withContext(defaultDispatcher) {
            macroPayApi.login(body)
        }


        if (response.isSuccessful && response.body() != null) {
            val tokenResponse = response.body()!!

            if (tokenResponse.success) {
                val token = response.body()!!.toModel()
                emit(Resource.Success(token))
            } else emit(Resource.Error(Exception("Invalid credentials")))

        } else {
            emit(Resource.Error(Exception("Some error login")))
        }
    }
}