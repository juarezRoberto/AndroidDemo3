package com.juarez.androiddemo3.login.data

import com.juarez.androiddemo3.api.MacroPayApi
import com.juarez.androiddemo3.login.domain.TokenWrapper
import com.juarez.androiddemo3.login.domain.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

interface LoginRepository {
    fun login(username: String, password: String): Flow<TokenWrapper>
    suspend fun getUsers(): List<Any>
}


class LoginRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val macroPayApi: MacroPayApi
) : LoginRepository {

    override fun login(username: String, password: String): Flow<TokenWrapper> = flow {
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
                emit(token)
            } else throw Exception("Invalid credentials")

        } else throw Exception("Some error login")
    }

    override suspend fun getUsers(): List<Any> {
        return withContext(defaultDispatcher) {
            delay(4000)
            listOf("Jose", "Roberto", "Joseph")
        }
    }


}