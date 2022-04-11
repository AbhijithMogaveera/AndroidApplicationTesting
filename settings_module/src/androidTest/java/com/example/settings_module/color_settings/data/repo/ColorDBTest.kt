package com.example.settings_module.color_settings.data.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.settings_module.color_settings.data.local.dao.ColorDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ColorDBTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var colorSettingsDao: ColorDao

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

}