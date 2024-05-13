package com.example.demoapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.example.demoapp.util.XmlMapper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    val demoxml =
            "<User>" +
            "<Id>1</Id>" +
            "<Name>Test - 1</Name>" +
            "<Email>test-1@test.com</Email>" +
            "<Address>India</Address>" +
            "<Created>May 12, 2024, 1:41 pm</Created>" +
            "</User>"

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.demoapp", appContext.packageName)
    }


    @Test
    fun test_xml_adapter() {
        val user = XmlMapper().fromXmlToUsers(demoxml)
        assert(user[0].id == "1")
    }
}