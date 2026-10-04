package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FF))
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp,
                bottom = 24.dp
            )
    ) {

        // ---------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription =
                        "Back",

                    tint =
                        Color(0xFF111827)
                )
            }

            Spacer(
                Modifier.size(4.dp)
            )

            Column {

                Text(
                    text =
                        "Notifications",

                    style =
                        MaterialTheme.typography
                            .headlineSmall,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color(0xFF111827)
                )

                Text(
                    text =
                        "Stay updated with your expenses",

                    style =
                        MaterialTheme.typography
                            .bodySmall,

                    color =
                        Color(0xFF6B7280)
                )
            }
        }


        Spacer(
            Modifier.height(24.dp)
        )


        // ---------------------------------------------------------
        // EMPTY NOTIFICATION STATE
        // ---------------------------------------------------------

        Card(

            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(22.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Color.White
                )
        ) {

            Column(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(32.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Box(

                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            Color(0xFFEDE9FE)
                        ),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Notifications,

                        contentDescription =
                            null,

                        tint =
                            Color(0xFF4F46E5),

                        modifier =
                            Modifier.size(36.dp)
                    )
                }

                Spacer(
                    Modifier.height(18.dp)
                )

                Text(
                    text =
                        "No new notifications",

                    style =
                        MaterialTheme.typography
                            .titleLarge,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color(0xFF111827)
                )

                Spacer(
                    Modifier.height(8.dp)
                )

                Text(
                    text =
                        "You're all caught up. New expense updates will appear here.",

                    style =
                        MaterialTheme.typography
                            .bodyMedium,

                    color =
                        Color(0xFF6B7280)
                )
            }
        }
    }
}