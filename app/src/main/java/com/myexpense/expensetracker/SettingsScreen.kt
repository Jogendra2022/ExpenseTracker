package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onSecurityClick: () -> Unit,
    onAppSettingsClick: () -> Unit
) {

    var selectedPage by remember {
        mutableStateOf<String?>(null)
    }

    // ---------------------------------------------------------
    // SUB PAGE
    // ---------------------------------------------------------

    if (selectedPage != null) {

        SettingsSubPage(
            page = selectedPage!!,
            onBackClick = {
                selectedPage = null
            }
        )

        return
    }

    // ---------------------------------------------------------
    // MAIN SETTINGS
    // ---------------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {

        // HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            ),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            // -------------------------------------------------
            // PREFERENCES
            // -------------------------------------------------

            item {

                Text(
                    text = "PREFERENCES",

                    style =
                        MaterialTheme.typography.labelLarge,

                    color =
                        MaterialTheme.colorScheme.primary,

                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 4.dp
                    )
                )
            }

            // DARK MODE
            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surface
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.DarkMode,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme.colorScheme.primary,

                            modifier =
                                Modifier.size(28.dp)
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    horizontal = 16.dp
                                )
                        ) {

                            Text(
                                text = "Dark Mode",

                                style =
                                    MaterialTheme.typography.titleMedium,

                                fontWeight =
                                    FontWeight.SemiBold,

                                color =
                                    MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )

                            Text(
                                text =
                                    if (darkMode) {
                                        "Dark appearance is enabled"
                                    } else {
                                        "Use a darker appearance"
                                    },

                                style =
                                    MaterialTheme.typography.bodyMedium,

                                color =
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = darkMode,

                            onCheckedChange = { enabled ->

                                onDarkModeChanged(
                                    enabled
                                )
                            }
                        )
                    }
                }
            }

            // -------------------------------------------------
            // APP SETTINGS
            // -------------------------------------------------

            item {

                Text(
                    text = "APP SETTINGS",

                    style =
                        MaterialTheme.typography.labelLarge,

                    color =
                        MaterialTheme.colorScheme.primary,

                    modifier = Modifier.padding(
                        top = 12.dp,
                        bottom = 4.dp
                    )
                )
            }

            // NOTIFICATIONS
            item {

                SettingsOption(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle =
                        "Manage notification preferences",

                    onClick = {

                        onNotificationsClick()
                    }
                )
            }

            // PRIVACY
            item {

                SettingsOption(
                    icon = Icons.Default.PrivacyTip,
                    title = "Privacy",
                    subtitle =
                        "Learn how your data is handled",

                    onClick = {

                        selectedPage = "Privacy"
                    }
                )
            }

            // SECURITY
            item {

                SettingsOption(
                    icon = Icons.Default.Shield,
                    title = "Security",
                    subtitle =
                        "Manage account security",

                    onClick = {

                        selectedPage = "Security"
                    }
                )
            }

            // APP SETTINGS
            item {

                SettingsOption(
                    icon = Icons.Default.Settings,
                    title = "App Settings",
                    subtitle =
                        "General application preferences",

                    onClick = {

                        selectedPage = "App Settings"
                    }
                )
            }

            // -------------------------------------------------
            // ABOUT
            // -------------------------------------------------

            item {

                Text(
                    text = "ABOUT",

                    style =
                        MaterialTheme.typography.labelLarge,

                    color =
                        MaterialTheme.colorScheme.primary,

                    modifier = Modifier.padding(
                        top = 12.dp,
                        bottom = 4.dp
                    )
                )
            }

            item {

                SettingsOption(
                    icon = Icons.Default.Info,
                    title = "ExpenseTracker",
                    subtitle = "Version 1.0",
                    onClick = {}
                )
            }
        }
    }
}

// =============================================================
// SETTINGS OPTION
// =============================================================

@Composable
private fun SettingsOption(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape =
            RoundedCornerShape(18.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,

                contentDescription = null,

                tint =
                    MaterialTheme.colorScheme.primary,

                modifier =
                    Modifier.size(26.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = 16.dp
                    )
            ) {

                Text(
                    text = title,

                    style =
                        MaterialTheme.typography.titleMedium,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )

                Text(
                    text = subtitle,

                    style =
                        MaterialTheme.typography.bodyMedium,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "›",

                style =
                    MaterialTheme.typography.headlineMedium,

                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =============================================================
// SETTINGS SUB PAGE
// =============================================================

@Composable
private fun SettingsSubPage(
    page: String,
    onBackClick: () -> Unit
) {

    val title: String
    val icon: ImageVector
    val description: String

    when (page) {

        "Privacy" -> {

            title = "Privacy"

            icon =
                Icons.Default.PrivacyTip

            description =
                "Your expense information is stored in the app database. Firebase Authentication is used for your account."
        }

        "Security" -> {

            title = "Security"

            icon =
                Icons.Default.Lock

            description =
                "Your account is protected using Firebase Authentication. Keep your password private and use a strong password."
        }

        "App Settings" -> {

            title = "App Settings"

            icon =
                Icons.Default.Settings

            description =
                "General preferences for the ExpenseTracker application."
        }

        else -> {

            title = "Notifications"

            icon =
                Icons.Default.Notifications

            description =
                "Notification preferences for your ExpenseTracker account."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(16.dp)
    ) {

        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),

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
                        MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = title,

                style =
                    MaterialTheme.typography.headlineSmall,

                fontWeight =
                    FontWeight.Bold,

                color =
                    MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        Card(
            modifier =
                Modifier.fillMaxWidth(),

            shape =
                RoundedCornerShape(20.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surface
                ),

            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
        ) {

            Column(
                modifier =
                    Modifier.padding(24.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = icon,

                    contentDescription = null,

                    tint =
                        MaterialTheme.colorScheme.primary,

                    modifier =
                        Modifier.size(54.dp)
                )

                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )

                Text(
                    text = title,

                    style =
                        MaterialTheme.typography.titleLarge,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text = description,

                    style =
                        MaterialTheme.typography.bodyLarge,

                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}