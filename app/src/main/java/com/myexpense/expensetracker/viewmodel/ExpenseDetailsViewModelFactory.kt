package com.myexpense.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myexpense.expensetracker.repository.ExpenseRepository

class ExpenseDetailsViewModelFactory(
    private val repository: ExpenseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ExpenseDetailsViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")
            return ExpenseDetailsViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}