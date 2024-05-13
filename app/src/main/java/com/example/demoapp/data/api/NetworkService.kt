package com.example.demoapp.data.api

import com.example.demoapp.data.models.User

interface NetworkService {
   suspend fun getUser(page:Int):List<User>
}