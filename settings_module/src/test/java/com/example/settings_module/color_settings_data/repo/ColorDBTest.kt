package com.example.settings_module.color_settings_data.repo

import com.example.settings_module.color_settings.data.local.dao.ColorDao
import com.example.settings_module.color_settings.data.repo.ColorDB
import com.settings_module.fake.ColorDaoFake
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ColorDBTest {

    private lateinit var colorSettingsDao: ColorDao
    private lateinit var victim:ColorDB

    @Before fun setUp() {
        colorSettingsDao = ColorDaoFake()
        victim = ColorDB(colorSettingsDao)
    }

    @After fun tearDown() {

    }

    @Test fun insertColor(){

        runBlocking {

            //Prepare
            val color = 0xff0011

            //Execute
            victim.addColor(color)

            //Assert
            assert(victim.getColors().first().contains(color))

        }

    }

    @Test fun removeColor(){
        runBlocking {
            //Prepare
            val color = 0xff0011

            //Execute
            victim.addColor(color)
            victim.removeColor(color)

            //Assert
            assert(!victim.getColors().first().contains(color))
        }
    }
}