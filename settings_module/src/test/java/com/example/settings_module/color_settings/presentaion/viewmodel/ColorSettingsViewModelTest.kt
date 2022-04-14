package com.example.settings_module.color_settings.presentaion.viewmodel

import com.example.settings_module.color_settings.data.local.model.MyColor
import com.example.settings_module.color_settings.data.repo.ColorDB
import com.settings_module.fake.ColorDaoFake
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test

class ColorSettingsViewModelTest {

    private lateinit var viewModel:ColorSettingsViewModel
    private lateinit var colorDao:ColorDaoFake

    private val color1 = 0xff9443
    private val color2 = 0xff9444
    private val color3 = 0xff4443

    @Before
    fun setUp() {
        colorDao = ColorDaoFake()
        runBlocking {
            colorDao.insertColor(
                MyColor(color1)
            )
            colorDao.insertColor(
                MyColor(color2)
            )
            colorDao.insertColor(
                MyColor(color3)
            )
        }
        viewModel = ColorSettingsViewModel(
            ColorDB(colorDao),
            Dispatchers.IO
        )
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testColorSelection(){
        runBlocking {
            viewModel.selectColor(color1)
            assert(viewModel.selectColorIndex == 0)
            assert(viewModel.colors.first().contains(color1))
        }
    }

}