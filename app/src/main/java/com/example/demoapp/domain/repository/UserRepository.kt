package com.example.demoapp.domain.repository

import com.example.demoapp.data.models.User

interface UserRepository {

   suspend fun getUsers(page:Int) :List<User>

}