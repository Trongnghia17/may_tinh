package com.example.may_tinh_v4

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CalculationHistory(
    val firstNumber: Double,
    val secondNumber: Double,
    val operation: String,
    val result: Double
)

@Composable
fun CalculatorScreen() {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<CalculationHistory>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Máy Tính",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // First number input
        OutlinedTextField(
            value = firstNumber,
            onValueChange = { firstNumber = it },
            label = { Text("Số thứ nhất") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Second number input
        OutlinedTextField(
            value = secondNumber,
            onValueChange = { secondNumber = it },
            label = { Text("Số thứ hai") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Operation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OperationButton(
                operation = "+",
                onClick = {
                    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                        try {
                            val num1 = firstNumber.toDouble()
                            val num2 = secondNumber.toDouble()
                            val calculationResult = num1 + num2
                            result = calculationResult.toString()
                            
                            // Add to history
                            history = history + CalculationHistory(
                                num1, num2, "+", calculationResult
                            )
                        } catch (e: NumberFormatException) {
                            result = "Lỗi"
                        }
                    }
                }
            )

            OperationButton(
                operation = "-",
                onClick = {
                    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                        try {
                            val num1 = firstNumber.toDouble()
                            val num2 = secondNumber.toDouble()
                            val calculationResult = num1 - num2
                            result = calculationResult.toString()
                            
                            // Add to history
                            history = history + CalculationHistory(
                                num1, num2, "-", calculationResult
                            )
                        } catch (e: NumberFormatException) {
                            result = "Lỗi"
                        }
                    }
                }
            )

            OperationButton(
                operation = "×",
                onClick = {
                    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                        try {
                            val num1 = firstNumber.toDouble()
                            val num2 = secondNumber.toDouble()
                            val calculationResult = num1 * num2
                            result = calculationResult.toString()
                            
                            // Add to history
                            history = history + CalculationHistory(
                                num1, num2, "×", calculationResult
                            )
                        } catch (e: NumberFormatException) {
                            result = "Lỗi"
                        }
                    }
                }
            )

            OperationButton(
                operation = "÷",
                onClick = {
                    if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                        try {
                            val num1 = firstNumber.toDouble()
                            val num2 = secondNumber.toDouble()
                            if (num2 == 0.0) {
                                result = "Không thể chia cho 0"
                            } else {
                                val calculationResult = num1 / num2
                                result = calculationResult.toString()
                                
                                // Add to history
                                history = history + CalculationHistory(
                                    num1, num2, "÷", calculationResult
                                )
                            }
                        } catch (e: NumberFormatException) {
                            result = "Lỗi"
                        }
                    }
                }
            )
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

@Composable
fun OperationButton(operation: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(64.dp)
    ) {
        Text(text = operation, fontSize = 24.sp)
    }
}

@Composable
fun HistoryItem(item: CalculationHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.firstNumber} ${item.operation} ${item.secondNumber} = ${item.result}",
                fontSize = 16.sp
            )
        }
    }
}
