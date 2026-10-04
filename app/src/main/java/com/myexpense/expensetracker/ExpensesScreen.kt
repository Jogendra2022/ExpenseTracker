package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myexpense.expensetracker.data.ExpenseDatabase
import com.myexpense.expensetracker.data.ExpenseEntity
import com.myexpense.expensetracker.repository.ExpenseRepository
import com.myexpense.expensetracker.viewmodel.ExpensesViewModel
import com.myexpense.expensetracker.viewmodel.ExpensesViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpensesScreen(
    onExpenseClick: (Int) -> Unit
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
        ExpensesViewModelFactory(repository)
    }

    val viewModel: ExpensesViewModel = viewModel(
        factory = factory
    )

    val expenses by viewModel.expenses
        .collectAsStateWithLifecycle()

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    val categories = listOf(
        "All",
        "Food",
        "Travel",
        "Shopping",
        "Bills"
    )

    val filteredExpenses = expenses.filter { expense ->

        val searchMatches =
            expense.title.contains(
                searchText,
                ignoreCase = true
            ) ||
                    expense.category.contains(
                        searchText,
                        ignoreCase = true
                    )

        val categoryMatches =
            selectedCategory == "All" ||
                    expense.category.equals(
                        selectedCategory,
                        ignoreCase = true
                    )

        searchMatches && categoryMatches
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
                top = 18.dp
            )
    ) {

        // ---------------------------------------------
        // HEADER
        // ---------------------------------------------

        Text(
            text = "All Expenses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ---------------------------------------------
        // SEARCH
        // ---------------------------------------------

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF6B7280)
                )
            },
            placeholder = {
                Text(
                    text = "Search expenses"
                )
            }
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        // ---------------------------------------------
        // CATEGORY FILTERS
        // ---------------------------------------------

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    categories.forEach { category ->

                        FilterChip(
                            selected =
                                selectedCategory == category,

                            onClick = {
                                selectedCategory = category
                            },

                            label = {
                                Text(category)
                            },

                            shape = RoundedCornerShape(12.dp),

                            colors =
                                FilterChipDefaults.filterChipColors(
                                    selectedContainerColor =
                                        Color(0xFF4F46E5),

                                    selectedLabelColor =
                                        Color.White,

                                    containerColor =
                                        Color.White,

                                    labelColor =
                                        Color(0xFF4B5563)
                                )
                        )
                    }
                }
            }

            // -----------------------------------------
            // EXPENSES
            // -----------------------------------------

            if (filteredExpenses.isEmpty()) {

                item {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text =
                                    if (
                                        searchText.isBlank() &&
                                        selectedCategory == "All"
                                    ) {
                                        "No expenses yet"
                                    } else {
                                        "No matching expenses"
                                    },
                                style =
                                    MaterialTheme.typography.titleMedium,
                                fontWeight =
                                    FontWeight.SemiBold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text =
                                    "Your expenses will appear here.",
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
                    items = filteredExpenses,
                    key = {
                        it.id
                    }
                ) { expense ->

                    ExpenseCard(
                        expense = expense,
                        onClick = {
                            onExpenseClick(
                                expense.id
                            )
                        }
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }
        }
    }
}

@Composable
private fun ExpenseCard(
    expense: ExpenseEntity,
    onClick: () -> Unit
) {

    val dateFormatter = remember {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        )
    }

    val category =
        expense.category.trim().lowercase()

    val icon =
        when {
            category.contains("food") ||
                    category.contains("lunch") ||
                    category.contains("restaurant") ->
                Icons.Default.Restaurant

            category.contains("travel") ||
                    category.contains("bus") ||
                    category.contains("transport") ->
                Icons.Default.DirectionsBus

            category.contains("shopping") ->
                Icons.Default.ShoppingBag

            category.contains("bill") ->
                Icons.Default.Receipt

            else ->
                Icons.Default.MoreHoriz
        }

    val iconBackground =
        when {
            category.contains("food") ->
                Color(0xFFFFE4E6)

            category.contains("travel") ||
                    category.contains("bus") ->
                Color(0xFFEDE9FE)

            category.contains("shopping") ->
                Color(0xFFFFEDD5)

            category.contains("bill") ->
                Color(0xFFDCFCE7)

            else ->
                Color(0xFFE0E7FF)
        }

    val iconColor =
        when {
            category.contains("food") ->
                Color(0xFFE11D48)

            category.contains("travel") ||
                    category.contains("bus") ->
                Color(0xFF7C3AED)

            category.contains("shopping") ->
                Color(0xFFEA580C)

            category.contains("bill") ->
                Color(0xFF16A34A)

            else ->
                Color(0xFF4F46E5)
        }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // -----------------------------------------
            // CATEGORY ICON
            // -----------------------------------------

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = expense.category,
                    tint = iconColor,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            // -----------------------------------------
            // EXPENSE INFORMATION
            // -----------------------------------------

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = expense.title,
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight =
                        FontWeight.SemiBold,
                    color =
                        Color(0xFF111827)
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = expense.category,
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        Color(0xFF6B7280)
                )

                Text(
                    text = dateFormatter.format(
                        Date(expense.date)
                    ),
                    style =
                        MaterialTheme.typography.labelSmall,
                    color =
                        Color(0xFF9CA3AF)
                )
            }

            // -----------------------------------------
            // AMOUNT
            // -----------------------------------------

            Text(
                text = "₹%.2f".format(
                    expense.amount
                ),
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight =
                    FontWeight.Bold,
                color =
                    Color(0xFF111827)
            )
        }
    }
}