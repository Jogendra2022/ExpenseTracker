package com.myexpense.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexpense.expensetracker.data.ExpenseEntity
import com.myexpense.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class StatisticsViewModel(
    repository: ExpenseRepository
) : ViewModel() {

    val expenses: StateFlow<List<ExpenseEntity>> =
        repository.getAllExpenses()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val totalExpenses: StateFlow<Double> =
        repository.getTotalExpenses()
            .map { total ->
                total ?: 0.0
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )

    fun getExpensesForMonth(
        expenses: List<ExpenseEntity>,
        year: Int,
        month: Int
    ): List<ExpenseEntity> {

        return expenses.filter { expense ->

            val calendar = Calendar.getInstance()

            calendar.timeInMillis = expense.date

            calendar.get(Calendar.YEAR) == year &&
                    calendar.get(Calendar.MONTH) == month
        }
    }
}