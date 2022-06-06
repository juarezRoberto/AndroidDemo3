package com.juarez.androiddemo3.login.domain

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String?
)

fun TokenDto.toModel(): TokenWrapper {
    return TokenWrapper(token)
}