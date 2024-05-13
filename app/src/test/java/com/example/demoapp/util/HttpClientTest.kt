package com.example.demoapp.util

import com.example.demoapp.data.api.network.HttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class HttpClientTest {
    private val TAG = javaClass.simpleName
    lateinit var httpClient: HttpClient

    @Before
    fun setUp() {
        httpClient = HttpClient(Constant.BASE_URL)
    }

    @Test
    fun `check get request`() = runBlocking {
            val response = httpClient.get("/xml_demo")
            assert(!response.isSuccess)

    }

    @Test
    fun `post success`() = runBlocking {
      val response =  httpClient.post("/post_demo", mapOf("name" to "debashis"))
        println(response)
        assert(response.isSuccess)
    }


}