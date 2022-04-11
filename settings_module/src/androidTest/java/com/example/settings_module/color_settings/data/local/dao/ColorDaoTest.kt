package com.example.settings_module.color_settings.data.local.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.settings_module.color_settings.data.local.model.MyColor
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ColorDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    private lateinit var colorSettingsDao: ColorDao

    @Before
    fun insertColor() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun testInset() {
        runBlocking {
            val color = MyColor(0xff1234)
            colorSettingsDao.insertColor(
                color
            )
            assert(colorSettingsDao.getAllColors().first().contains(color))
        }
    }

    @Test
    fun testDelete() {
        runBlocking {
            val color = MyColor(0xff1234)
            colorSettingsDao.deleteColor(
                color
            )
            assert(!colorSettingsDao.getAllColors().first().contains(color))
        }
    }
}