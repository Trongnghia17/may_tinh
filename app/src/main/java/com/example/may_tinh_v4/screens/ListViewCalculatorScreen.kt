package com.example.may_tinh_v4.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.may_tinh_v4.data.CalculationHistory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListViewCalculatorScreen(navController: NavController = androidx.navigation.compose.rememberNavController()) {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<CalculationHistory>()) }

    val operations = listOf("+", "-", "×", "÷")
    var selectedOperationIndex by remember { mutableStateOf(-1) }

    val numberOptions = (-10..10).map { it.toString() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Máy Tính (ListView)") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // First number dropdown
            NumberDropdown(
                label = "Số thứ nhất",
                selectedValue = firstNumber,
                onValueChange = { firstNumber = it },
                options = numberOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Second number dropdown
            NumberDropdown(
                label = "Số thứ hai",
                selectedValue = secondNumber,
                onValueChange = { secondNumber = it },
                options = numberOptions,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Operations ListView
            Text(
                text = "Chọn Phép Tính:",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                LazyColumn(modifier = Modifier.padding(8.dp)) {
                    items(operations) { operation ->
                        val isSelected = operations.indexOf(operation) == selectedOperationIndex

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedOperationIndex = operations.indexOf(operation)
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Phép tính: $operation",
                                    fontSize = 18.sp,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Calculate Button
            Button(
                onClick = {
                    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && selectedOperationIndex != -1) {
                        try {
                            val num1 = firstNumber.toDouble()
                            val num2 = secondNumber.toDouble()
                            var calculationResult = 0.0
                            val selectedOperation = operations[selectedOperationIndex]

                            when (selectedOperation) {
                                "+" -> calculationResult = num1 + num2
                                "-" -> calculationResult = num1 - num2
                                "×" -> calculationResult = num1 * num2
                                "÷" -> {
                                    if (num2 == 0.0) {
                                        result = "Không thể chia cho 0"
                                        return@Button
                                    } else {
                                        calculationResult = num1 / num2
                                    }
                                }
                            }

                            result = calculationResult.toString()

                            // Add to history
                            history = history + CalculationHistory(
                                num1, num2, selectedOperation, calculationResult
                            )
                        } catch (e: NumberFormatException) {
                            result = "Lỗi"
                        }
                    } else if (selectedOperationIndex == -1) {
                        result = "Vui lòng chọn phép tính"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Tính", fontSize = 18.sp)
            }

            // Result display
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Kết quả:",
                        fontSize = 18.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Text(
                        text = result,
                        fontSize = 24.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // History section
            Text(
                text = "Lịch sử tính toán:",
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                items(history.reversed()) { item ->
                    HistoryItem(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberDropdown(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onValueChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
