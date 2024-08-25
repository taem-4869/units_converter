package com.taemallah.unitsconverter.mainScreen.domain.modules

import androidx.annotation.StringRes
import com.taemallah.unitsconverter.R

enum class UnitCategory (@StringRes val categoryName: Int){
    Length(R.string.length),
    Weight(R.string.weight),
    Temperature(R.string.temperatore);
    fun getUnits(): List<MyUnit>{
        return when(this){
            Length -> LengthUnit.entries
            Weight -> WeightUnit.entries
            Temperature -> TemperatureUnit.entries
        }
    }
    fun getDefaultFrom(): MyUnit{
        return when(this){
            Length -> LengthUnit.Kilometer
            Weight -> WeightUnit.Kilogram
            Temperature -> TemperatureUnit.Fahrenheit
        }
    }
    fun getDefaultTo(): MyUnit{
        return when(this){
            Length -> LengthUnit.Meter
            Weight -> WeightUnit.Gram
            Temperature -> TemperatureUnit.Celsius
        }
    }

    fun isValidUnit(unit: MyUnit): Boolean{
        return when(this){
            Length -> unit is LengthUnit
            Weight -> unit is WeightUnit
            Temperature -> unit is TemperatureUnit
        }
    }
}