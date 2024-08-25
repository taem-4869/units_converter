package com.taemallah.unitsconverter.mainScreen.presentation

import com.taemallah.unitsconverter.mainScreen.domain.modules.MyUnit
import com.taemallah.unitsconverter.mainScreen.domain.modules.UnitCategory

sealed class UnitEvent {
    data class SetEnteredValue(val value: String): UnitEvent()
    data class SetFromUnit(val unit: MyUnit, val enteredValue: Double? = null): UnitEvent()
    data class SetToUnit(val unit: MyUnit): UnitEvent()
    data class SetUnitCategory(val category: UnitCategory): UnitEvent()
    data object SwapConvertUnits: UnitEvent()
    data object SwapConvertValues: UnitEvent()
}