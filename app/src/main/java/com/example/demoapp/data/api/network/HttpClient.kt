package com.example.demoapp.data.api.network

import android.util.Xml
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpRetryException
import java.net.HttpURLConnection
import java.net.URL

class HttpClient(var baseUrl: String) {


    @Throws(IOException::class, FileNotFoundException::class, HttpRetryException::class)
    suspend fun get(urlString: String): Response = withContext(Dispatchers.IO) {
        val url = URL(baseUrl + urlString)
        val connection = url.openConnection() as HttpURLConnection
        var body: String? = null
        try {
            connection.requestMethod = "GET"
            val errorCode = connection.responseCode
            val errorbody = connection.errorStream?.let(::readInputStream)
            try {
                body = connection.inputStream?.let(::readInputStream)
            } catch (_: Exception) {
            }
            return@withContext Response(
                code = errorCode,
                errorBody = errorbody,
                body = body
            )
        } finally {
            connection.disconnect()
        }

    }

    suspend fun post(urlString: String, requestBody: Map<String, Any>): Response =
        withContext(Dispatchers.IO) {
            val url = URL(baseUrl + urlString)
            val connection = url.openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "POST"
                connection.doOutput = true
                val outputStream: OutputStream = connection.outputStream
                println(mapToJson(requestBody))
                outputStream.write(mapToJson(requestBody).toByteArray(Charsets.UTF_8))
                outputStream.flush()

                val errorCode = connection.responseCode
                val errorbody = connection.errorStream?.let(::readInputStream)
                var body: String? = null
                try {
                    body = connection.inputStream?.let(::readInputStream)
                } catch (_: Exception) {
                }
                return@withContext Response(
                    code = errorCode,
                    errorBody = errorbody,
                    body = body
                )
            } finally {
                connection.disconnect()
            }

        }

    private fun mapToJson(map: Map<String, Any>): String {
        val json = StringBuilder()
        json.append("{")
        map.forEach {
            json.append(""""${it.key}":"${it.value}"""")
        }
        json.append("}")
        return json.toString()
    }

    private fun readInputStream(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
        return stringBuilder.toString()
    }

    private fun parseXmlData(xmlData: String): UserData? {
        var userData: UserData? = null
        val parser: XmlPullParser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(xmlData.reader())

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (parser.name == "user") {
                        val id = parser.getAttributeValue(null, "id").toInt()
                        val name = parser.getAttributeValue(null, "name")
                        val email = parser.getAttributeValue(null, "email")
                        userData = UserData(id, name, email)
                    }
                }
            }
            eventType = parser.next()
        }

        return userData
    }
}

data class UserData(
    val id: Int,
    val name: String,
    val email: String
)
