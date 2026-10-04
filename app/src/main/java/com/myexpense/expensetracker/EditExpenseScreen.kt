package com.myexpense.expensetracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(
    initialTitle: String,
    initialAmount: Double,
    initialCategory: String,
    initialDescription: String,
    initialDate: Long,
    onUpdateClick: (
        String,
        String,
        String,
        String,
        Long
    ) -> Unit,
    onBackClick: () -> Unit
) {

    var title by remember {
        mutableStateOf(initialTitle)
    }

    var amount by remember {
        mutableStateOf(initialAmount.toString())
    }

    var category by remember {
        mutableStateOf(initialCategory)
    }

    var description by remember {
        mutableStateOf(initialDescription)
    }

    var selectedDate by remember {
        mutableLongStateOf(initialDate)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showError by remember {
        mutableStateOf(false)
    }

    val formattedDate = remember(selectedDate) {
        SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(
            Date(selectedDate)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF8F9FF)
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp,
                bottom = 24.dp
            )
    ) {

        // HEADER

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF111827)
                )
            }

            Spacer(
                modifier = Modifier.size(4.dp)
            )

            Text(
                text = "Edit Expense",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // INFORMATION CARD

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEDE9FE)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Update your expense",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF312E81)
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Change the details and save your updates.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF5B5A8A)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // TITLE

        Text(
            text = "Expense Title",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                showError = false
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // AMOUNT

        Text(
            text = "Amount",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
                showError = false
            },
            modifier = Modifier.fillMaxWidth(),
            prefix = {
                Text("₹ ")
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // CATEGORY

        Text(
            text = "Category",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
                showError = false
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // DESCRIPTION

        Text(
            text = "Description",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 4,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // DATE

        Text(
            text = "Expense Date",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF374151)
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {

                IconButton(
                    onClick = {
                        showDatePicker = true
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Change Date",
                        tint = Color(0xFF4F46E5)
                    )
                }
            },
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = {
                showDatePicker = true
            }
        ) {

            Text(
                text = "Change date",
                color = Color(0xFF4F46E5)
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // ERROR

        if (showError) {

            Text(
                text = "Please enter a valid title, amount and category.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }

        // UPDATE BUTTON

        Button(
            onClick = {

                if (
                    title.isBlank() ||
                    amount.toDoubleOrNull() == null ||
                    category.isBlank()
                ) {

                    showError = true

                } else {

                    showError = false

                    onUpdateClick(
                        title,
                        amount,
                        category,
                        description,
                        selectedDate
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4F46E5)
            )
        ) {

            Text(
                text = "Update Expense",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Cancel",
                color = Color(0xFF6B7280)
            )
        }
    }

    // DATE PICKER

    if (showDatePicker) {

        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = selectedDate
            )

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState
                            .selectedDateMillis
                            ?.let { date ->

                                selectedDate = date
                            }

                        showDatePicker = false
                    }
                ) {

                    Text("OK")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {

                    Text("Cancel")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }
}