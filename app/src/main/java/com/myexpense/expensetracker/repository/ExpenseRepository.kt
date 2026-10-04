package com.myexpense.expensetracker.repository

import com.myexpense.expensetracker.data.ExpenseDao
import com.myexpense.expensetracker.data.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    suspend fun addExpense(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }

    fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return expenseDao.getAllExpenses()
    }

    fun getTotalExpenses(): Flow<Double?> {
        return expenseDao.getTotalExpenses()
    }

    suspend fun getExpenseById(expenseId: Int): ExpenseEntity? {
        return expenseDao.getExpenseById(expenseId)
    }

    suspend fun updateExpense(expense: ExpenseEntity) {
        expenseDao.updateExpense(expense)
    }
}