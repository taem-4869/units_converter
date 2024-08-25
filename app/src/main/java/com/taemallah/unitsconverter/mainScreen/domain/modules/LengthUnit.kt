package com.taemallah.unitsconverter.mainScreen.domain.modules

import androidx.annotation.StringRes
import com.taemallah.unitsconverter.R

enum class LengthUnit(
    @StringRes override val unitName: Int,
    override val unitSymbol: String,
    override val multiplier: Double,
):MyUnit {
    Millimeter(R.string.millimeter,"mm",.001),
    Centimeter(R.string.centimeter,"cm",.01),
    Meter(R.string.meter,"m",1.0),
    Kilometer(R.string.kilometer,"km",1000.0),
    Inch(R.string.inch,"in",.0254),
    Feet(R.string.feet,"ft",.3048),
    Yard(R.string.yard,"yd",.9144),
    Mile(R.string.mile,"mi",1609.344);


    override var unitValue: Double = 0.0

    override fun fromUnit(unit: MyUnit): MyUnit {
        this.unitValue = unit.toBaseUnit().unitValue/multiplier
        return this
    }

    override fun toBaseUnit(): MyUnit {
        val result = Meter
        result.unitValue=unitValue*multiplier
        return result
    }

    override fun setUnitValue(unitValue : Double): MyUnit{
        this.unitValue=unitValue
        return this
    }

    override fun isSameUnit(unit: MyUnit): Boolean {
        return entries.contains(unit)
    }
}