package com.example.may_tinh_v4.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.may_tinh_v4.navigation.Screen

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Chọn Loại Máy Tính",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        CalculatorTypeButton(
            text = "Máy Tính với Buttons",
            onClick = { navController.navigate(Screen.BUTTON_CALCULATOR) }
        )
        
        CalculatorTypeButton(
            text = "Máy Tính với Spinner",
            onClick = { navController.navigate(Screen.SPINNER_CALCULATOR) }
        )
        
        CalculatorTypeButton(
            text = "Máy Tính với ListView",
            onClick = { navController.navigate(Screen.LISTVIEW_CALCULATOR) }
        )
        
        CalculatorTypeButton(
            text = "Máy Tính với Radio Group",
            onClick = { navController.navigate(Screen.RADIO_CALCULATOR) }
        )
    }
}

@Composable
fun CalculatorTypeButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}
