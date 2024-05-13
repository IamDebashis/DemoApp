package com.example.demoapp.data.repository

import com.example.demoapp.data.api.NetworkService
import com.example.demoapp.data.models.User
import com.example.demoapp.domain.repository.UserRepository

class UserRepositoryImpl constructor(
    private val networkService: NetworkService
) : UserRepository {


    override suspend fun getUsers(page: Int): List<User> {
        return networkService.getUser(page)
    }
}