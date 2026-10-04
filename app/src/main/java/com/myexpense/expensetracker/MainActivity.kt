package com.myexpense.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.myexpense.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ExpenseTrackerApp()
        }
    }
}

@Composable
private fun ExpenseTrackerApp() {

    var darkTheme by rememberSaveable {
        mutableStateOf(false)
    }

    ExpenseTrackerTheme(
        darkTheme = darkTheme
    ) {

        AppNavigation(
            darkTheme = darkTheme,
            onDarkThemeChanged = { enabled ->
                darkTheme = enabled
            }
        )
    }
}