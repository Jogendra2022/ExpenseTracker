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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myexpense.expensetracker.data.ExpenseDatabase
import com.myexpense.expensetracker.repository.ExpenseRepository
import com.myexpense.expensetracker.viewmodel.StatisticsViewModel
import com.myexpense.expensetracker.viewmodel.StatisticsViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen() {

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val database = remember {
        ExpenseDatabase.getDatabase(context)
    }

    val repository = remember {
        ExpenseRepository(database.expenseDao())
    }

    val factory = remember {
        StatisticsViewModelFactory(repository)
    }

    val viewModel: StatisticsViewModel = viewModel(
        factory = factory
    )

    val expenses by viewModel.expenses
        .collectAsStateWithLifecycle()

    val totalExpenses by viewModel.totalExpenses
        .collectAsStateWithLifecycle()

    val currentMonthExpenses = remember(expenses) {

        val calendar = Calendar.getInstance()

        val currentYear =
            calendar.get(Calendar.YEAR)

        val currentMonth =
            calendar.get(Calendar.MONTH)

        expenses.filter { expense ->

            val expenseCalendar =
                Calendar.getInstance().apply {
                    timeInMillis = expense.date
                }

            expenseCalendar.get(Calendar.YEAR) == currentYear &&
                    expenseCalendar.get(Calendar.MONTH) == currentMonth
        }
    }

    val currentMonthTotal =
        currentMonthExpenses.sumOf {
            it.amount
        }

    val averageExpense =
        if (expenses.isNotEmpty()) {
            totalExpenses / expenses.size
        } else {
            0.0
        }

    val categoryTotals =
        expenses
            .groupBy {
                it.category.ifBlank {
                    "Other"
                }
            }
            .mapValues { entry ->
                entry.value.sumOf {
                    it.amount
                }
            }
            .toList()
            .sortedByDescending {
                it.second
            }

    val dateFormatter = remember {

        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF8F9FF)
            )
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 20.dp
            )
    ) {

        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Understand your spending habits",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn(
            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            item {

                // TOTAL CARD

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4F46E5)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(22.dp)
                    ) {

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(
                                        Color(0x33FFFFFF),
                                        RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

                            Text(
                                text = "Total Spending",
                                style =
                                    MaterialTheme.typography.titleMedium,
                                color = Color(0xFFE0E7FF),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        Text(
                            text =
                                "₹%.2f".format(totalExpenses),
                            style =
                                MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "${expenses.size} total expenses",
                            style =
                                MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFE0E7FF)
                        )
                    }
                }
            }

            item {

                // SUMMARY ROW

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    SummaryCard(
                        modifier = Modifier.weight(1f),
                        title = "This Month",
                        value =
                            "₹%.2f".format(
                                currentMonthTotal
                            ),
                        icon =
                            Icons.Default.BarChart
                    )

                    SummaryCard(
                        modifier = Modifier.weight(1f),
                        title = "Average",
                        value =
                            "₹%.2f".format(
                                averageExpense
                            ),
                        icon =
                            Icons.Default.ReceiptLong
                    )
                }
            }

            item {

                Text(
                    text = "Category Breakdown",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
            }

            if (categoryTotals.isEmpty()) {

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text =
                                    "No expense data yet",
                                style =
                                    MaterialTheme.typography.bodyLarge
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(
                                text =
                                    "Add expenses to see your spending breakdown.",
                                style =
                                    MaterialTheme.typography.bodySmall,
                                color =
                                    Color(0xFF6B7280)
                            )
                        }
                    }
                }

            } else {

                items(
                    items = categoryTotals
                ) { categoryItem ->

                    val category =
                        categoryItem.first

                    val categoryAmount =
                        categoryItem.second

                    val percentage =
                        if (totalExpenses > 0) {
                            (
                                    categoryAmount /
                                            totalExpenses
                                    ) * 100
                        } else {
                            0.0
                        }

                    CategoryCard(
                        category = category,
                        amount = categoryAmount,
                        percentage = percentage
                    )
                }
            }

            item {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Recent Activity",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
            }

            if (expenses.isEmpty()) {

                item {

                    Text(
                        text =
                            "No expenses recorded yet.",
                        color =
                            Color(0xFF6B7280)
                    )
                }

            } else {

                items(
                    items = expenses.take(5),
                    key = {
                        it.id
                    }
                ) { expense ->

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(16.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            horizontalArrangement =
                                Arrangement.SpaceBetween,
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Column(
                                modifier =
                                    Modifier.weight(1f)
                            ) {

                                Text(
                                    text =
                                        expense.title,
                                    style =
                                        MaterialTheme.typography.titleMedium,
                                    fontWeight =
                                        FontWeight.SemiBold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(3.dp)
                                )

                                Text(
                                    text =
                                        "${expense.category} • ${
                                            dateFormatter.format(
                                                Date(expense.date)
                                            )
                                        }",
                                    style =
                                        MaterialTheme.typography.bodySmall,
                                    color =
                                        Color(0xFF6B7280)
                                )
                            }

                            Text(
                                text =
                                    "₹%.2f".format(
                                        expense.amount
                                    ),
                                style =
                                    MaterialTheme.typography.titleMedium,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF4F46E5)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(
    modifier: Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF4F46E5),
                modifier = Modifier.size(22.dp)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = title,
                style =
                    MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = value,
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }
    }
}

@Composable
private fun CategoryCard(
    category: String,
    amount: Double,
    percentage: Double
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = category,
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111827)
                )

                Text(
                    text =
                        "₹%.2f".format(amount),
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4F46E5)
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(
                        Color(0xFFE5E7EB),
                        RoundedCornerShape(50)
                    )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(
                            (percentage / 100.0)
                                .coerceIn(0.0, 1.0)
                                .toFloat()
                        )
                        .height(8.dp)
                        .background(
                            Color(0xFF4F46E5),
                            RoundedCornerShape(50)
                        )
                )
            }

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text =
                    "%.1f%% of total spending"
                        .format(percentage),
                style =
                    MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}