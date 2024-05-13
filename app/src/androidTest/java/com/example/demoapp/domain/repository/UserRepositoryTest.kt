package com.example.demoapp.domain.repository

import com.example.demoapp.data.api.NetworkSeriveImpl
import com.example.demoapp.data.repository.UserRepositoryImpl
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(NetworkSeriveImpl())
    }

    @Test
    fun getUsers(): Unit = runBlocking {
        val users = userRepository.getUsers(1)
        assert(users[0].id == "1")
    }
}