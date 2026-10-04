package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseTrackerLogo(
    size: androidx.compose.ui.unit.Dp = 88.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.30f))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF5B5BF7),
                        Color(0xFF4338CA)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.AccountBalanceWallet,
            contentDescription = "ExpenseTracker Logo",
            tint = Color.White,
            modifier = Modifier.size(size * 0.58f)
        )

        Box(
            modifier = Modifier
                .size(size * 0.27f)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(Color(0xFFFFB52E)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "₹",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = androidx.compose.ui.unit.TextUnit.Unspecified
            )
        }
    }
}