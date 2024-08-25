package com.taemallah.unitsconverter.mainScreen.presentation

import com.taemallah.unitsconverter.mainScreen.domain.modules.Converter
import com.taemallah.unitsconverter.mainScreen.domain.modules.LengthUnit
import com.taemallah.unitsconverter.mainScreen.domain.modules.MyUnit
import com.taemallah.unitsconverter.mainScreen.domain.modules.UnitCategory

data class UnitState (
    val converter: Converter = Converter(),
    val enteredValue: String = "",
    val selectedCategory: UnitCategory = UnitCategory.Length,
)