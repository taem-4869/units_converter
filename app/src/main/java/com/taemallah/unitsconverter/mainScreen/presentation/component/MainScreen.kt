package com.taemallah.unitsconverter.mainScreen.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taemallah.unitsconverter.Const
import com.taemallah.unitsconverter.R
import com.taemallah.unitsconverter.mainScreen.domain.modules.UnitCategory
import com.taemallah.unitsconverter.mainScreen.presentation.UnitEvent
import com.taemallah.unitsconverter.mainScreen.presentation.UnitState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(state: UnitState, onEvent: (UnitEvent)-> Unit) {
    Scaffold {padding->
        Column(modifier=Modifier) {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.welcome_to_units_converter))
                },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            listOf(Color.Transparent, MaterialTheme.colorScheme.primary)
                        ),
                        RoundedCornerShape(10)
                    )
                    .shadow(
                        10.dp,
                        RoundedCornerShape(10),
                        ambientColor = MaterialTheme.colorScheme.primary,
                        spotColor = MaterialTheme.colorScheme.primary,
                    )
            )
            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .padding(horizontal = Const.MAIN_SCREEN_PADDING)
                    .padding(bottom = Const.MAIN_SCREEN_PADDING)
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SingleChoiceSegmentedButtonRow {
                    UnitCategory.entries.forEach{category ->
                        SegmentedButton(
                            selected =category.name==state.selectedCategory.name,
                            onClick = { onEvent(UnitEvent.SetUnitCategory(category)) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index =UnitCategory.entries.indexOf(category),
                                count =UnitCategory.entries.size
                            )
                        ) {
                            Text(text = stringResource(id = category.categoryName))
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    UnitSelectionDropDownMenus(state = state, onEvent = onEvent)
                    UnitConvertingTextField(state = state, onEvent = onEvent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitSelectionDropDownMenus(state: UnitState, onEvent: (UnitEvent)-> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        var isExpandedFromMenu by remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuBox(
            expanded =isExpandedFromMenu,
            onExpandedChange ={
                isExpandedFromMenu=it
            },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = stringResource(id = state.converter.getFrom().unitName),
                onValueChange = {},
                label = { Text(text = stringResource(R.string.From))},
                readOnly = true,
                singleLine = true,
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedFromMenu)},
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .clip(RoundedCornerShape(10))
            )
            ExposedDropdownMenu(
                expanded = isExpandedFromMenu,
                onDismissRequest = { isExpandedFromMenu=!isExpandedFromMenu },
                modifier= Modifier.clip(RoundedCornerShape(10))) {
                state.selectedCategory.getUnits().forEach{unit->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = unit.unitName)) },
                        onClick = {
                            onEvent(UnitEvent.SetFromUnit(unit))
                            isExpandedFromMenu=false
                        }
                    )
                }
            }
        }
        var reverseAnim by remember {
            mutableStateOf(false)
        }
        val rotation = remember{ derivedStateOf { if (reverseAnim) 540f else 0f } }
        val anim by animateFloatAsState(targetValue = rotation.value, label = "", animationSpec = tween(1000))
        Icon(
            painter = painterResource(id = R.drawable.convert),
            contentDescription = stringResource(R.string.convert),
            modifier = Modifier
                .size(30.dp)
                .rotate(anim)
                .clickable {
                    reverseAnim=!reverseAnim
                    onEvent(UnitEvent.SwapConvertUnits)
                }
        )
        var isExpandedToMenu by remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuBox(
            expanded =isExpandedToMenu,
            onExpandedChange ={
                isExpandedToMenu=it
            },
            modifier = Modifier.weight(1f)
        ) {
            OutlinedTextField(
                value = stringResource(id = state.converter.getTo().unitName),
                onValueChange = {},
                label = { Text(text = stringResource(R.string.to))},
                readOnly = true,
                singleLine = true,
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandedToMenu)},
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor()
                    .clip(RoundedCornerShape(10))
            )
            ExposedDropdownMenu(
                expanded = isExpandedToMenu,
                onDismissRequest = { isExpandedToMenu=false },
                modifier= Modifier.clip(RoundedCornerShape(10))) {
                state.selectedCategory.getUnits().forEach{unit->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = unit.unitName)) },
                        onClick = {
                            onEvent(UnitEvent.SetToUnit(unit))
                            isExpandedToMenu=false
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConvertingTextField(state: UnitState, onEvent: (UnitEvent)-> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        OutlinedTextField(
            value = state.enteredValue,
            onValueChange = {onEvent(UnitEvent.SetEnteredValue(it))},
            label = { Text(text = state.converter.getFrom().unitSymbol)},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .weight(1f)
        )

        var reverseAnim by remember {
            mutableStateOf(false)
        }
        val rotation = remember{ derivedStateOf { if (reverseAnim) 540f else 0f } }
        val anim by animateFloatAsState(targetValue = rotation.value, label = "", animationSpec = tween(1000))
        Icon(
            painter = painterResource(id = R.drawable.convert),
            contentDescription = stringResource(R.string.convert),
            modifier = Modifier
                .size(30.dp)
                .rotate(anim)
                .clickable {
                    reverseAnim = !reverseAnim
                    onEvent(UnitEvent.SwapConvertValues)
                }
        )

        OutlinedTextField(
            value = if (state.enteredValue.isBlank()) "" else state.converter.getTo().unitValue.toString(),
            onValueChange = {},
            label = { Text(text = state.converter.getTo().unitSymbol)},
            readOnly = true,
            singleLine = true,
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .weight(1f)
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen(state = UnitState(), onEvent = {})
}