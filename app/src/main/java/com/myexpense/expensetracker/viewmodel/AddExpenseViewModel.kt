package com.myexpense.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexpense.expensetracker.data.ExpenseEntity
import com.myexpense.expensetracker.repository.ExpenseRepository
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    fun saveExpense(
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

        val expense = ExpenseEntity(
            title = title,
            amount = amountValue,
            category = category,
            description = description,
            date = date
        )

        viewModelScope.launch {
            repository.addExpense(expense)
            onSuccess()
        }
    }
}