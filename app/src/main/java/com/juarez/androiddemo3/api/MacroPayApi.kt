package com.juarez.androiddemo3.api

import com.juarez.androiddemo3.login.domain.TokenDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MacroPayApi {

    @POST("https://testandroid.macropay.com.mx/")
    suspend fun login(
        @Body body: RequestBody
    ): Response<TokenDto>
}