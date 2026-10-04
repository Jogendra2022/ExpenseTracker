package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {

    LaunchedEffect(Unit) {
        delay(1400)
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ExpenseTrackerLogo(
            size = 104.dp
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = "ExpenseTracker",
            color = Color(0xFF111827),
            fontWeight = FontWeight.Bold,
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Track  •  Save  •  Achieve",
            color = Color(0xFF6B7280),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .width(38.dp)
                .height(38.dp),
            color = Color(0xFF4F46E5),
            strokeWidth = 3.dp
        )
    }
}