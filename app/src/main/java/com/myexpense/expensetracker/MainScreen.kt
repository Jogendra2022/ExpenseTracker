package com.myexpense.expensetracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainScreen(
    navController: NavController,
    content: @Composable () -> Unit
) {

    val backStackEntry by navController
        .currentBackStackEntryAsState()

    val currentRoute =
        backStackEntry?.destination?.route

    Scaffold(

        bottomBar = {

            NavigationBar(
                containerColor = Color.White
            ) {

                // ---------------- HOME ----------------

                NavigationBarItem(
                    selected =
                        currentRoute == Screen.Home.route,

                    onClick = {

                        navController.navigate(
                            Screen.Home.route
                        ) {

                            popUpTo(
                                Screen.Home.route
                            ) {
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    },

                    icon = {

                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },

                    label = {
                        Text("Home")
                    },

                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFF4F46E5),

                            selectedTextColor =
                                Color(0xFF4F46E5),

                            unselectedIconColor =
                                Color(0xFF9CA3AF),

                            unselectedTextColor =
                                Color(0xFF9CA3AF),

                            indicatorColor =
                                Color(0xFFEDE9FE)
                        )
                )

                // ---------------- EXPENSES ----------------

                NavigationBarItem(
                    selected =
                        currentRoute == Screen.Expenses.route,

                    onClick = {

                        navController.navigate(
                            Screen.Expenses.route
                        ) {

                            launchSingleTop = true
                        }
                    },

                    icon = {

                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Expenses"
                        )
                    },

                    label = {
                        Text("Expenses")
                    },

                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFF4F46E5),

                            selectedTextColor =
                                Color(0xFF4F46E5),

                            unselectedIconColor =
                                Color(0xFF9CA3AF),

                            unselectedTextColor =
                                Color(0xFF9CA3AF),

                            indicatorColor =
                                Color(0xFFEDE9FE)
                        )
                )

                // ---------------- STATISTICS ----------------

                NavigationBarItem(
                    selected =
                        currentRoute == Screen.Statistics.route,

                    onClick = {

                        navController.navigate(
                            Screen.Statistics.route
                        ) {

                            launchSingleTop = true
                        }
                    },

                    icon = {

                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "Statistics"
                        )
                    },

                    label = {
                        Text("Statistics")
                    },

                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFF4F46E5),

                            selectedTextColor =
                                Color(0xFF4F46E5),

                            unselectedIconColor =
                                Color(0xFF9CA3AF),

                            unselectedTextColor =
                                Color(0xFF9CA3AF),

                            indicatorColor =
                                Color(0xFFEDE9FE)
                        )
                )

                // ---------------- PROFILE ----------------

                NavigationBarItem(
                    selected =
                        currentRoute == Screen.Profile.route,

                    onClick = {

                        navController.navigate(
                            Screen.Profile.route
                        ) {

                            launchSingleTop = true
                        }
                    },

                    icon = {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },

                    label = {
                        Text("Profile")
                    },

                    colors =
                        NavigationBarItemDefaults.colors(
                            selectedIconColor =
                                Color(0xFF4F46E5),

                            selectedTextColor =
                                Color(0xFF4F46E5),

                            unselectedIconColor =
                                Color(0xFF9CA3AF),

                            unselectedTextColor =
                                Color(0xFF9CA3AF),

                            indicatorColor =
                                Color(0xFFEDE9FE)
                        )
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            content()
        }
    }
}