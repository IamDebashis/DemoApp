package com.example.demoapp.data.api

import com.example.demoapp.data.models.User
import com.example.demoapp.util.Constant
import com.example.demoapp.data.api.network.HttpClient
import com.example.demoapp.util.XmlMapper

class NetworkSeriveImpl : NetworkService {
    private val client = HttpClient(Constant.BASE_URL)

    @Throws(Exception::class)
    override suspend fun getUser(page: Int): List<User> {
        val response = client.get("/testApi/getUser/?page=$page")
        if (response.isSuccess) {
            val users = response.body?.let {
                XmlMapper().fromXmlToUsers(it)
            }
            return users ?: emptyList()
        }
        return emptyList()
    }
}