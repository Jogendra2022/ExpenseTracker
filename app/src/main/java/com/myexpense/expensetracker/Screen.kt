package com.myexpense.expensetracker

sealed class Screen(
    val route: String
) {

    data object Login : Screen("login")

    data object Register : Screen("register")

    data object Home : Screen("home")

    data object Expenses : Screen("expenses")

    data object Statistics : Screen("statistics")

    data object Profile : Screen("profile")

    data object AddExpense : Screen("add_expense")

    data object ExpenseDetails :
        Screen("expense_details/{expenseId}")

    data object EditExpense :
        Screen("edit_expense/{expenseId}")
}