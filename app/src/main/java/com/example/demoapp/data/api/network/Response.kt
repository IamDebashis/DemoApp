package com.example.demoapp.data.api.network

data class Response(
    val code: Int,
    val errorBody: String?,
    val body: String?
) {

    val isSuccess: Boolean
        get() = code in 200..299


}