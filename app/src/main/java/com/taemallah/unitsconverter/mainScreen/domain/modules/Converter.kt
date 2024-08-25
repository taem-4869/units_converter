package com.taemallah.unitsconverter.mainScreen.domain.modules

import android.util.Log

class Converter {

    private var _category: UnitCategory = UnitCategory.Length

    private var _from : MyUnit

    private var _to : MyUnit

    init {
        _from = _category.getDefaultFrom()
        _to = _category.getDefaultTo()
    }

    fun setCategory(category: UnitCategory, enteredValue: Double = 0.0): Converter {
        _category = category
        _from = category.getDefaultFrom().setUnitValue(enteredValue)
        _to = category.getDefaultTo().fromUnit(_from)
        return this
    }

    fun setFromUnit(from: MyUnit, enteredValue: Double): Converter {
        if (!_category.isValidUnit(from)) {
            Log.e("kid_e","not valid to input, given ${from.unitSymbol} required ${_category.name}")
            return this
        }
        Log.e("kid_e","switching ${_from.unitSymbol} to ${from.unitSymbol}")
        from.setUnitValue(enteredValue)
        this._from = from
        _to = _to.fromUnit(this._from)
        return this
    }

    fun setToUnit(to:MyUnit): Converter {
        if (!_category.isValidUnit(to)) {
            Log.e("kid_e","not valid to input, given ${to.unitSymbol} required ${_category.name}")
            return this
        }
        this._to = to.fromUnit(_from)
        return this
    }

    fun setEnteredValue(enteredValue: Double): Converter {
        _from.setUnitValue(enteredValue)
        _to = _to.fromUnit(_from)
        return this
    }

    fun getFrom(): MyUnit {
        return _from
    }

    fun getTo(): MyUnit {
        return _to
    }
}