package com.myexpense.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexpense.expensetracker.data.ExpenseEntity
import com.myexpense.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpenseDetailsViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _expense = MutableStateFlow<ExpenseEntity?>(null)

    val expense: StateFlow<ExpenseEntity?> =
        _expense.asStateFlow()

    fun loadExpense(expenseId: Int) {
        viewModelScope.launch {
            _expense.value =
                repository.getExpenseById(expenseId)
        }
    }

    fun deleteExpense(
        expense: ExpenseEntity,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
            onSuccess()
        }
    }

    fun updateExpense(
        expense: ExpenseEntity,
        title: String,
        amount: String,
        category: String,
        description: String,
        date: Long,
        onSuccess: () -> Unit
    ) {
        val amountValue = amount.toDoubleOrNull()

        if (
            title.isBlank() ||
            amountValue == null ||
            category.isBlank()
        ) {
            return
        }

        val updatedExpense = expense.copy(
            title = title,
            amount = amountValue,
            category = category,
            description = description,
            date = date
        )

        viewModelScope.launch {
            repository.updateExpense(updatedExpense)
            onSuccess()
        }
    }
}