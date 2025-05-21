package com.example.may_tinh_v4.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.may_tinh_v4.data.CalculationHistory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioCalculatorScreen(navController: NavController = androidx.navigation.compose.rememberNavController()) {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<CalculationHistory>()) }

    val operations = listOf("+", "-", "×", "÷")
    var selectedOperation by remember { mutableStateOf(operations[0]) }



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
        
        // Radio Group for Operations
        Text(
            text = "Chọn Phép Tính:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                operations.forEach { operation ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .selectable(
                                selected = (operation == selectedOperation),
                                onClick = { selectedOperation = operation },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (operation == selectedOperation),
                            onClick = null // Click is handled by selectable modifier
                        )
                        Text(
                            text = "Phép tính: $operation",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
        
        // Calculate Button
        Button(
            onClick = {
                if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                    try {
                        val num1 = firstNumber.toDouble()
                        val num2 = secondNumber.toDouble()
                        var calculationResult = 0.0
                        
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
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = result,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // History section
        Text(
            text = "Lịch sử tính toán:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
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
