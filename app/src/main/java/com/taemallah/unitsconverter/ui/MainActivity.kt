package com.taemallah.unitsconverter.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taemallah.unitsconverter.mainScreen.presentation.UnitViewModel
import com.taemallah.unitsconverter.mainScreen.presentation.component.MainScreen
import com.taemallah.unitsconverter.ui.theme.UnitsConverterTheme

class MainActivity : ComponentActivity() {

    private val viewModel: UnitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitsConverterTheme {
                MainScreen(state = viewModel.state.collectAsState().value, onEvent = viewModel::onEvent)
            }
        }
    }
}