package com.taemallah.unitsconverter.mainScreen.domain.modules

import androidx.annotation.StringRes
import com.taemallah.unitsconverter.R

enum class WeightUnit(
    @StringRes override val unitName: Int,
    override val unitSymbol: String,
    override val multiplier: Double,
):MyUnit {
    Milligram(R.string.milligram,"mg",.001),
    Gram(R.string.gram,"g",1.0),
    Kilogram(R.string.kilo_gram,"kg",1000.0),
    Ounce(R.string.ounce,"oz", 28.349524),
    Pound(R.string.pound,"lb", 453.59238),
    Stone(R.string.stone,"st", 6350.295),
    ;
    override var unitValue: Double = 0.0

    override fun fromUnit(unit: MyUnit): MyUnit {
        this.unitValue = unit.toBaseUnit().unitValue/multiplier
        return this
    }

    override fun toBaseUnit(): MyUnit {
        val result = Gram
        result.unitValue=unitValue*multiplier
        return result
    }

    override fun setUnitValue(unitValue : Double): MyUnit{
        this.unitValue=unitValue
        return this
    }

    override fun isSameUnit(unit: MyUnit): Boolean {
        return LengthUnit.entries.contains(unit)
    }
}