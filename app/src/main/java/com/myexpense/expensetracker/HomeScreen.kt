package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myexpense.expensetracker.data.ExpenseDatabase
import com.myexpense.expensetracker.repository.ExpenseRepository
import com.myexpense.expensetracker.viewmodel.HomeViewModel
import com.myexpense.expensetracker.viewmodel.HomeViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (Int) -> Unit,
    onViewAllExpensesClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val database = remember {
        ExpenseDatabase.getDatabase(context)
    }

    val repository = remember {
        ExpenseRepository(database.expenseDao())
    }

    val factory = remember {
        HomeViewModelFactory(repository)
    }

    val viewModel: HomeViewModel =
        viewModel(factory = factory)

    val expenses by viewModel.expenses
        .collectAsStateWithLifecycle()

    val totalExpenses by viewModel.totalExpenses
        .collectAsStateWithLifecycle()

    val calendar = remember {
        Calendar.getInstance()
    }

    val currentMonthExpenses = expenses.filter { expense ->

        val expenseCalendar =
            Calendar.getInstance().apply {
                timeInMillis = expense.date
            }

        expenseCalendar.get(Calendar.YEAR) ==
                calendar.get(Calendar.YEAR) &&
                expenseCalendar.get(Calendar.MONTH) ==
                calendar.get(Calendar.MONTH)
    }

    val currentMonthTotal =
        currentMonthExpenses.sumOf { it.amount }

    val recentExpenses =
        expenses.take(5)

    val currentMonthName =
        SimpleDateFormat(
            "MMMM yyyy",
            Locale.getDefault()
        ).format(Date())

    val dateFormatter =
        remember {
            SimpleDateFormat(
                "dd MMM",
                Locale.getDefault()
            )
        }

    ScaffoldContent(
        totalExpenses = totalExpenses,
        expensesCount = expenses.size,
        currentMonthTotal = currentMonthTotal,
        currentMonthExpensesCount = currentMonthExpenses.size,
        currentMonthName = currentMonthName,
        recentExpenses = recentExpenses,
        dateFormatter = dateFormatter,
        onAddExpenseClick = onAddExpenseClick,
        onExpenseClick = onExpenseClick,
        onViewAllExpensesClick = onViewAllExpensesClick,
        onNotificationClick = onNotificationClick,
        onProfileClick = onProfileClick
    )
}

@Composable
private fun ScaffoldContent(
    totalExpenses: Double,
    expensesCount: Int,
    currentMonthTotal: Double,
    currentMonthExpensesCount: Int,
    currentMonthName: String,
    recentExpenses: List<com.myexpense.expensetracker.data.ExpenseEntity>,
    dateFormatter: SimpleDateFormat,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (Int) -> Unit,
    onViewAllExpensesClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    androidx.compose.material3.Scaffold(
        containerColor = Color(0xFFF8F9FF)
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(18.dp)
        ) {

            // ---------------------------------------------------------
            // TOP HEADER
            // ---------------------------------------------------------

            item {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Good Morning 👋",
                            style =
                                MaterialTheme.typography.bodyMedium,
                            color =
                                Color(0xFF6B7280)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )

                        Text(
                            text = "Expense Tracker",
                            style =
                                MaterialTheme.typography.headlineSmall,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(0xFF111827)
                        )
                    }

                    IconButton(
                        onClick =
                            onNotificationClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Outlined
                                    .NotificationsNone,
                            contentDescription =
                                "Notifications",
                            tint =
                                Color(0xFF374151),
                            modifier =
                                Modifier.size(26.dp)
                        )
                    }

                    IconButton(
                        onClick =
                            onProfileClick
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.AccountCircle,
                            contentDescription =
                                "Profile",
                            tint =
                                Color(0xFF4F46E5),
                            modifier =
                                Modifier.size(32.dp)
                        )
                    }
                }
            }

            // ---------------------------------------------------------
            // TOTAL SPENDING CARD
            // ---------------------------------------------------------

            item {

                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onViewAllExpensesClick()
                            },
                    shape =
                        RoundedCornerShape(24.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color(0xFF4F46E5)
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(22.dp)
                    ) {

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Column(
                                modifier =
                                    Modifier.weight(1f)
                            ) {

                                Text(
                                    text =
                                        "Total Spending",
                                    style =
                                        MaterialTheme.typography
                                            .titleMedium,
                                    fontWeight =
                                        FontWeight.SemiBold,
                                    color =
                                        Color(0xFFE0E7FF)
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(5.dp)
                                )

                                Text(
                                    text =
                                        "All your expenses",
                                    style =
                                        MaterialTheme.typography
                                            .bodySmall,
                                    color =
                                        Color(0xFFC7D2FE)
                                )
                            }

                            Box(
                                modifier =
                                    Modifier
                                        .size(48.dp)
                                        .clip(
                                            CircleShape
                                        )
                                        .background(
                                            Color(0x26FFFFFF)
                                        ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text = "₹",
                                    color =
                                        Color.White,
                                    style =
                                        MaterialTheme.typography
                                            .titleLarge,
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }

                        Spacer(
                            modifier =
                                Modifier.height(16.dp)
                        )

                        Text(
                            text =
                                "₹%.2f".format(
                                    totalExpenses
                                ),
                            style =
                                MaterialTheme.typography
                                    .headlineLarge,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color.White
                        )

                        Spacer(
                            modifier =
                                Modifier.height(7.dp)
                        )

                        Text(
                            text =
                                "$expensesCount expenses recorded",
                            style =
                                MaterialTheme.typography
                                    .bodySmall,
                            color =
                                Color(0xFFC7D2FE)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(18.dp)
                        )

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Text(
                                text =
                                    currentMonthName,
                                style =
                                    MaterialTheme.typography
                                        .bodyMedium,
                                fontWeight =
                                    FontWeight.Medium,
                                color =
                                    Color(0xFFE0E7FF)
                            )

                            Spacer(
                                modifier =
                                    Modifier.weight(1f)
                            )

                            Box(
                                modifier =
                                    Modifier
                                        .size(38.dp)
                                        .clip(
                                            CircleShape
                                        )
                                        .background(
                                            Color.White
                                        )
                                        .clickable {
                                            onViewAllExpensesClick()
                                        },
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ArrowForward,
                                    contentDescription =
                                        "View Expenses",
                                    tint =
                                        Color(0xFF4F46E5),
                                    modifier =
                                        Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            // ---------------------------------------------------------
            // SUMMARY TITLE
            // ---------------------------------------------------------

            item {

                Text(
                    text = "Overview",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        Color(0xFF111827)
                )
            }

            // ---------------------------------------------------------
            // THIS MONTH + EXPENSES
            // ---------------------------------------------------------

            item {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    // THIS MONTH

                    Card(
                        modifier =
                            Modifier.weight(1f),
                        shape =
                            RoundedCornerShape(20.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(17.dp)
                        ) {

                            Box(
                                modifier =
                                    Modifier
                                        .size(42.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                13.dp
                                            )
                                        )
                                        .background(
                                            Color(0xFFEDE9FE)
                                        ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .CalendarMonth,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFF4F46E5),
                                    modifier =
                                        Modifier.size(22.dp)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Text(
                                text =
                                    "This Month",
                                style =
                                    MaterialTheme.typography
                                        .bodyMedium,
                                color =
                                    Color(0xFF6B7280)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )

                            Text(
                                text =
                                    "₹%.2f".format(
                                        currentMonthTotal
                                    ),
                                style =
                                    MaterialTheme.typography
                                        .titleLarge,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF111827)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )

                            Text(
                                text =
                                    "$currentMonthExpensesCount expenses",
                                style =
                                    MaterialTheme.typography
                                        .bodySmall,
                                color =
                                    Color(0xFF9CA3AF)
                            )
                        }
                    }

                    // EXPENSES

                    Card(
                        modifier =
                            Modifier.weight(1f),
                        shape =
                            RoundedCornerShape(20.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(17.dp)
                        ) {

                            Box(
                                modifier =
                                    Modifier
                                        .size(42.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                13.dp
                                            )
                                        )
                                        .background(
                                            Color(0xFFE0E7FF)
                                        ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ReceiptLong,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFF4F46E5),
                                    modifier =
                                        Modifier.size(22.dp)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(12.dp)
                            )

                            Text(
                                text =
                                    "Expenses",
                                style =
                                    MaterialTheme.typography
                                        .bodyMedium,
                                color =
                                    Color(0xFF6B7280)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )

                            Text(
                                text =
                                    expensesCount.toString(),
                                style =
                                    MaterialTheme.typography
                                        .titleLarge,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF111827)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )

                            Text(
                                text =
                                    "Total transactions",
                                style =
                                    MaterialTheme.typography
                                        .bodySmall,
                                color =
                                    Color(0xFF9CA3AF)
                            )
                        }
                    }
                }
            }

            // ---------------------------------------------------------
            // ADD EXPENSE BUTTON
            // ---------------------------------------------------------

            item {

                Button(
                    onClick =
                        onAddExpenseClick,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                    shape =
                        RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFF4F46E5),
                            contentColor =
                                Color.White
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Add,
                        contentDescription =
                            null,
                        tint =
                            Color.White,
                        modifier =
                            Modifier.size(21.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.size(8.dp)
                    )

                    Text(
                        text =
                            "Add New Expense",
                        color =
                            Color.White,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }

            // ---------------------------------------------------------
            // RECENT EXPENSES HEADER
            // ---------------------------------------------------------

            item {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(
                            text =
                                "Recent Expenses",
                            style =
                                MaterialTheme.typography
                                    .titleLarge,
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color(0xFF111827)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )

                        Text(
                            text =
                                "Your latest transactions",
                            style =
                                MaterialTheme.typography
                                    .bodySmall,
                            color =
                                Color(0xFF6B7280)
                        )
                    }

                    if (recentExpenses.isNotEmpty()) {

                        Text(
                            text =
                                "View All",
                            color =
                                Color(0xFF4F46E5),
                            fontWeight =
                                FontWeight.SemiBold,
                            modifier =
                                Modifier
                                    .clickable {
                                        onViewAllExpensesClick()
                                    }
                                    .padding(6.dp)
                        )
                    }
                }
            }

            // ---------------------------------------------------------
            // RECENT EXPENSES LIST
            // ---------------------------------------------------------

            if (recentExpenses.isEmpty()) {

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(20.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier =
                                    Modifier
                                        .size(58.dp)
                                        .clip(
                                            CircleShape
                                        )
                                        .background(
                                            Color(0xFFEDE9FE)
                                        ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ReceiptLong,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFF4F46E5),
                                    modifier =
                                        Modifier.size(28.dp)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )

                            Text(
                                text =
                                    "No expenses yet",
                                style =
                                    MaterialTheme.typography
                                        .titleMedium,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF111827)
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(5.dp)
                            )

                            Text(
                                text =
                                    "Add your first expense to start tracking.",
                                style =
                                    MaterialTheme.typography
                                        .bodySmall,
                                color =
                                    Color(0xFF6B7280)
                            )
                        }
                    }
                }

            } else {

                items(
                    items =
                        recentExpenses,
                    key = {
                        it.id
                    }
                ) { expense ->

                    Card(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onExpenseClick(
                                        expense.id
                                    )
                                },
                        shape =
                            RoundedCornerShape(18.dp),
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
                                    .padding(15.dp),
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Box(
                                modifier =
                                    Modifier
                                        .size(46.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                14.dp
                                            )
                                        )
                                        .background(
                                            Color(0xFFEDE9FE)
                                        ),
                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default
                                            .ReceiptLong,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFF4F46E5),
                                    modifier =
                                        Modifier.size(23.dp)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.size(12.dp)
                            )

                            Column(
                                modifier =
                                    Modifier.weight(1f)
                            ) {

                                Text(
                                    text =
                                        expense.title,
                                    style =
                                        MaterialTheme.typography
                                            .titleMedium,
                                    fontWeight =
                                        FontWeight.SemiBold,
                                    color =
                                        Color(0xFF111827)
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(3.dp)
                                )

                                Text(
                                    text =
                                        "${expense.category} • ${
                                            dateFormatter.format(
                                                Date(
                                                    expense.date
                                                )
                                            )
                                        }",
                                    style =
                                        MaterialTheme.typography
                                            .bodySmall,
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
                                    MaterialTheme.typography
                                        .titleMedium,
                                fontWeight =
                                    FontWeight.Bold,
                                color =
                                    Color(0xFF111827)
                            )
                        }
                    }
                }
            }

            item {

                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
            }
        }
    }
}