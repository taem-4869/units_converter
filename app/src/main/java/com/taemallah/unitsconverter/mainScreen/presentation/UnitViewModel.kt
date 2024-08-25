package com.taemallah.unitsconverter.mainScreen.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taemallah.unitsconverter.mainScreen.domain.modules.Converter
import com.taemallah.unitsconverter.mainScreen.domain.modules.LengthUnit
import com.taemallah.unitsconverter.mainScreen.domain.modules.UnitCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnitViewModel @Inject constructor() : ViewModel() {

    private val _selectedCategory = MutableStateFlow(UnitCategory.Length)
    private val _enteredValue = MutableStateFlow("")
    private val _state = MutableStateFlow(UnitState())

    val state = combine(_selectedCategory,_enteredValue,_state){selectedCategory,enteredValue,state->
        state.copy(
            enteredValue = enteredValue,
            selectedCategory = selectedCategory
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),UnitState())

    init {
        viewModelScope.launch {
            _enteredValue.collect { value->
                _state.update {
                    it.copy(
                        converter = it.converter.setEnteredValue(toDouble(value))
                    )
                }
            }
        }
        viewModelScope.launch {
            _selectedCategory.collect{category->
                _state.update { it.copy(
                    converter = it.converter.setCategory(category, toDouble(_enteredValue.value))
                ) }
            }
        }
    }

    fun onEvent(event: UnitEvent){
        when(event){
            is UnitEvent.SetEnteredValue -> {
                _enteredValue.update {
                    validateEnteredValue(event.value)
                }
            }
            is UnitEvent.SetFromUnit -> {
                _state.update {
                    it.copy(
                        converter = it.converter.setFromUnit(event.unit,
                            event.enteredValue ?: toDouble(_enteredValue.value)
                        )
                    )
                }
            }
            is UnitEvent.SetToUnit -> {
                Log.e("kid_e","switch unit request ${state.value.converter.getTo().unitSymbol} to ${event.unit.unitSymbol}")
                _state.update {
                    it.copy(converter = it.converter.setToUnit(event.unit))
                }
            }
            is UnitEvent.SetUnitCategory -> {
                _selectedCategory.update {
                    event.category
                }
            }

            is UnitEvent.SwapConvertUnits -> {
                val fromSaver = state.value.converter.getFrom()
                onEvent(UnitEvent.SetFromUnit(state.value.converter.getTo(),fromSaver.unitValue))
                onEvent(UnitEvent.SetToUnit(fromSaver))
            }

            is UnitEvent.SwapConvertValues -> {
                val newEnteredValue = state.value.converter.getTo().unitValue.toString()
                onEvent(UnitEvent.SetEnteredValue(newEnteredValue))
            }
        }
    }

    private fun toDouble(value: String):Double{
        if (value.isBlank()){
            return 0.0
        }
        return value.toDouble()
    }

    private fun validateEnteredValue(value: String):String{
        if (value.isBlank())return ""
        try {
            val toFloat = value.toDouble()
            return value
        }catch(e : Exception) {
            return validateEnteredValue(value.dropLast(1))
        }
    }

}