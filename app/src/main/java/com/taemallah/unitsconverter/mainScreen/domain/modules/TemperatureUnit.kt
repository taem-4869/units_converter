package com.taemallah.unitsconverter.mainScreen.domain.modules

import android.util.Log
import androidx.annotation.StringRes
import com.taemallah.unitsconverter.R

enum class TemperatureUnit(
    @StringRes override val unitName: Int,
    override val unitSymbol: String,
    override val multiplier: Double,
):MyUnit {
    Celsius(R.string.celsius,"°C",0.0),
    Fahrenheit(R.string.fahrenheit,"°F",0.0),
    Kelvin(R.string.kelvin,"K",0.0),
    ;
    override var unitValue: Double = 0.0

    override fun fromUnit(unit: MyUnit): MyUnit {
        var base = unit.toBaseUnit()
        if (unit is TemperatureUnit){
            when(this){
                Celsius -> {
                    return base
                }
                Fahrenheit -> {
                    this.unitValue = (base.unitValue * 9/5) +32
                    return this
                }
                Kelvin -> {
                    this.unitValue = base.unitValue + 273.15
                    return this
                }
            }
        }else{
            Log.e("kid_e","the given value must be of type TemperatureUnit: given ${unit.unitSymbol}")
            return this
        }
    }

    override fun toBaseUnit(): MyUnit {
        when(this){
            Celsius -> {
                return this
            }
            Fahrenheit -> {
                val result = Celsius
                result.unitValue = (unitValue-32)*(5/9)
                return result
            }
            Kelvin -> {
                val result = Celsius
                result.unitValue = unitValue - 273.15
                return result
            }
        }
    }

    override fun setUnitValue(unitValue : Double): MyUnit{
        this.unitValue=unitValue
        return this
    }

    override fun isSameUnit(unit: MyUnit): Boolean {
        return LengthUnit.entries.contains(unit)
    }
}