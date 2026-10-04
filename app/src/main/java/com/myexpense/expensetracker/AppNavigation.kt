package com.myexpense.expensetracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.myexpense.expensetracker.data.ExpenseDatabase
import com.myexpense.expensetracker.repository.ExpenseRepository
import com.myexpense.expensetracker.viewmodel.ExpenseDetailsViewModel
import com.myexpense.expensetracker.viewmodel.ExpenseDetailsViewModelFactory

@Composable
fun AppNavigation(
    darkTheme: Boolean = false,
    onDarkThemeChanged: (Boolean) -> Unit = {}
) {

    val navController = rememberNavController()

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    val showBottomBar =
        currentRoute == "home" ||
                currentRoute == "expenses" ||
                currentRoute == "statistics"

    Scaffold(

        bottomBar = {

            if (showBottomBar) {

                NavigationBar(
                    containerColor = if (darkTheme) {
                        Color(0xFF181B24)
                    } else {
                        Color.White
                    }
                ) {

                    // HOME
                    NavigationBarItem(
                        selected = currentRoute == "home",

                        onClick = {
                            navController.navigate("home") {

                                popUpTo("home") {
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
                        }
                    )

                    // EXPENSES
                    NavigationBarItem(
                        selected = currentRoute == "expenses",

                        onClick = {
                            navController.navigate("expenses") {

                                popUpTo("home") {
                                    inclusive = false
                                }

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
                        }
                    )

                    // STATISTICS
                    NavigationBarItem(
                        selected = currentRoute == "statistics",

                        onClick = {
                            navController.navigate("statistics") {

                                popUpTo("home") {
                                    inclusive = false
                                }

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
                        }
                    )
                }
            }
        }

    ) { innerPadding ->

        NavHost(

            navController = navController,

            startDestination =
                if (
                    FirebaseAuth
                        .getInstance()
                        .currentUser != null
                ) {
                    "home"
                } else {
                    "login"
                },

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // =========================================================
            // LOGIN
            // =========================================================

            composable("login") {

                val authViewModel =
                    remember {
                        AuthViewModel()
                    }

                LoginScreen(

                    viewModel = authViewModel,

                    onLoginSuccess = {

                        navController.navigate("home") {

                            popUpTo("login") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    },

                    onRegisterClick = {

                        navController.navigate("register")
                    }
                )
            }

            // =========================================================
            // REGISTER
            // =========================================================

            composable("register") {

                val authViewModel =
                    remember {
                        AuthViewModel()
                    }

                RegisterScreen(

                    viewModel = authViewModel,

                    onRegisterSuccess = {

                        navController.navigate("home") {

                            popUpTo("register") {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    },

                    onLoginClick = {

                        navController.popBackStack()
                    }
                )
            }

            // =========================================================
            // HOME
            // =========================================================

            composable("home") {

                HomeScreen(

                    onAddExpenseClick = {

                        navController.navigate(
                            "add_expense"
                        )
                    },

                    onExpenseClick = { expenseId ->

                        navController.navigate(
                            "details/$expenseId"
                        )
                    },

                    onViewAllExpensesClick = {

                        navController.navigate(
                            "expenses"
                        ) {
                            launchSingleTop = true
                        }
                    },

                    onNotificationClick = {

                        navController.navigate(
                            "notifications"
                        )
                    },

                    onProfileClick = {

                        navController.navigate(
                            "profile"
                        )
                    }
                )
            }

            // =========================================================
            // EXPENSES
            // =========================================================

            composable("expenses") {

                ExpensesScreen(

                    onExpenseClick = { expenseId ->

                        navController.navigate(
                            "details/$expenseId"
                        )
                    }
                )
            }

            // =========================================================
            // STATISTICS
            // =========================================================

            composable("statistics") {

                StatisticsScreen()
            }

            // =========================================================
            // ADD EXPENSE
            // =========================================================

            composable("add_expense") {

                AddExpenseScreen(

                    onBackClick = {

                        navController.popBackStack()
                    }
                )
            }

            // =========================================================
            // NOTIFICATIONS
            // =========================================================

            composable("notifications") {

                NotificationsScreen(

                    onBackClick = {

                        navController.popBackStack()
                    }
                )
            }

            // =========================================================
            // PROFILE
            // =========================================================

            composable("profile") {

                val email =
                    FirebaseAuth
                        .getInstance()
                        .currentUser
                        ?.email
                        ?: "No email available"

                ProfileScreen(

                    email = email,

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onSettingsClick = {

                        navController.navigate(
                            "settings"
                        )
                    },

                    onLogoutClick = {

                        FirebaseAuth
                            .getInstance()
                            .signOut()

                        navController.navigate(
                            "login"
                        ) {

                            popUpTo(0) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            // =========================================================
            // SETTINGS
            // =========================================================

            composable("settings") {

                SettingsScreen(

                    darkMode = darkTheme,

                    onDarkModeChanged = { enabled ->

                        onDarkThemeChanged(enabled)
                    },

                    onBackClick = {

                        navController.popBackStack()
                    },

                    onNotificationsClick = {

                        navController.navigate(
                            "notifications"
                        )
                    },

                    onPrivacyClick = {

                        // SettingsScreen handles
                        // the Privacy sub-page itself.
                    },

                    onSecurityClick = {

                        // SettingsScreen handles
                        // the Security sub-page itself.
                    },

                    onAppSettingsClick = {

                        // SettingsScreen handles
                        // the App Settings sub-page itself.
                    }
                )
            }

            // =========================================================
            // EXPENSE DETAILS
            // =========================================================

            composable(

                route = "details/{expenseId}",

                arguments = listOf(

                    navArgument("expenseId") {

                        type = NavType.IntType
                    }
                )

            ) { backStackEntry ->

                val expenseId =
                    backStackEntry
                        .arguments
                        ?.getInt("expenseId")
                        ?: -1

                val context =
                    LocalContext.current

                val database =
                    remember {

                        ExpenseDatabase
                            .getDatabase(context)
                    }

                val repository =
                    remember {

                        ExpenseRepository(
                            database.expenseDao()
                        )
                    }

                val factory =
                    remember {

                        ExpenseDetailsViewModelFactory(
                            repository
                        )
                    }

                val detailsViewModel:
                        ExpenseDetailsViewModel =
                    viewModel(
                        factory = factory
                    )

                val expense by
                detailsViewModel.expense
                    .collectAsStateWithLifecycle()

                LaunchedEffect(expenseId) {

                    detailsViewModel
                        .loadExpense(expenseId)
                }

                expense?.let { currentExpense ->

                    ExpenseDetailsScreen(

                        title =
                            currentExpense.title,

                        amount =
                            currentExpense.amount,

                        category =
                            currentExpense.category,

                        description =
                            currentExpense.description,

                        date =
                            currentExpense.date,

                        onBackClick = {

                            navController
                                .popBackStack()
                        },

                        onEditClick = {

                            navController.navigate(
                                "edit/$expenseId"
                            )
                        },

                        onDeleteClick = {

                            detailsViewModel
                                .deleteExpense(
                                    currentExpense
                                ) {

                                    navController
                                        .popBackStack()
                                }
                        }
                    )
                }
            }

            // =========================================================
            // EDIT EXPENSE
            // =========================================================

            composable(

                route = "edit/{expenseId}",

                arguments = listOf(

                    navArgument("expenseId") {

                        type = NavType.IntType
                    }
                )

            ) { backStackEntry ->

                val expenseId =
                    backStackEntry
                        .arguments
                        ?.getInt("expenseId")
                        ?: -1

                val context =
                    LocalContext.current

                val database =
                    remember {

                        ExpenseDatabase
                            .getDatabase(context)
                    }

                val repository =
                    remember {

                        ExpenseRepository(
                            database.expenseDao()
                        )
                    }

                val factory =
                    remember {

                        ExpenseDetailsViewModelFactory(
                            repository
                        )
                    }

                val detailsViewModel:
                        ExpenseDetailsViewModel =
                    viewModel(
                        factory = factory
                    )

                val expense by
                detailsViewModel.expense
                    .collectAsStateWithLifecycle()

                LaunchedEffect(expenseId) {

                    detailsViewModel
                        .loadExpense(expenseId)
                }

                expense?.let { currentExpense ->

                    EditExpenseScreen(

                        initialTitle =
                            currentExpense.title,

                        initialAmount =
                            currentExpense.amount,

                        initialCategory =
                            currentExpense.category,

                        initialDescription =
                            currentExpense.description,

                        initialDate =
                            currentExpense.date,

                        onUpdateClick = {
                                title,
                                amount,
                                category,
                                description,
                                date ->

                            detailsViewModel
                                .updateExpense(

                                    expense =
                                        currentExpense,

                                    title =
                                        title,

                                    amount =
                                        amount,

                                    category =
                                        category,

                                    description =
                                        description,

                                    date =
                                        date

                                ) {

                                    navController
                                        .popBackStack()
                                }
                        },

                        onBackClick = {

                            navController
                                .popBackStack()
                        }
                    )
                }
            }
        }
    }
}