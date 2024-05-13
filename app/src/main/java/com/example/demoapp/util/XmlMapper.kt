package com.example.demoapp.util

import android.util.Xml
import com.example.demoapp.data.models.User
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.Stack

class XmlMapper {

    @Throws(XmlPullParserException::class, IOException::class)
    fun fromXmlToUsers(xmlString: String): List<User> {
        val xmlPullParser = Xml.newPullParser()
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        xmlPullParser.setInput(xmlString.reader())


        var eventType = xmlPullParser.eventType
        val tagStack = Stack<String>()
        val userMap = mutableMapOf<String, Any>()
        val userList = mutableListOf<User>()
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    val tagName = xmlPullParser.name
                    tagStack.push(tagName)
                }

                XmlPullParser.TEXT -> {
                    val value = xmlPullParser.text.trim()
                    when (tagStack.peek()) {
                        "Id" -> {
                            userMap["id"] = value
                        }

                        "Name" -> {
                            userMap["name"] = value

                        }

                        "Email" -> {
                            userMap["email"] = value

                        }

                        "Address" -> {
                            userMap["address"] = value

                        }

                        "Created" -> {
                            userMap["created"] = value
                        }
                    }

                }

                XmlPullParser.END_TAG -> {
                    if (tagStack.pop() == "User") {
                        val user = User(
                            id = (userMap["id"] ?: "").toString(),
                            name = (userMap["name"] ?:"").toString(),
                            email = (userMap["email"] ?: "").toString(),
                            address = (userMap["address"] ?: "").toString(),
                            createdAt = (userMap["created"] ?: "").toString()
                        )
                        userList.add(user)
                        userMap.clear()
                    }
                }

            }
            eventType = xmlPullParser.next()
        }

        return userList
    }
}
