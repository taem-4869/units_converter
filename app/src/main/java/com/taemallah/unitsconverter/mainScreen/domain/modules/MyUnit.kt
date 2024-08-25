package com.taemallah.unitsconverter.mainScreen.domain.modules

import androidx.annotation.StringRes

interface MyUnit {
    @get:StringRes
    val unitName: Int
    val unitSymbol: String
    var unitValue: Double
    val multiplier: Double
    fun fromUnit(unit: MyUnit):MyUnit
    fun toBaseUnit():MyUnit
    fun setUnitValue(unitValue: Double):MyUnit
    fun isSameUnit(unit: MyUnit): Boolean
}