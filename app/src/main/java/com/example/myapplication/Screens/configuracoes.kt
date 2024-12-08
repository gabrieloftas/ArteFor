package com.example.myapplication.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun configuracoes(navController: NavHostController, applyFilter: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val customColor = Color(0xFF007BFF)
        Row(modifier = Modifier.fillMaxWidth().background(customColor).padding(60.dp)) {
            Text(text = "Configurações", color = Color.White, style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(40.dp)) {
            Text(text = ">Filtros", color = Color.Black, style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold))
        }

        var selectedFilter by remember { mutableStateOf("") }

        // Checkbox para Tritanopia
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = selectedFilter == "Tritanopia",
                onCheckedChange = {
                    selectedFilter = if (it) "Tritanopia" else ""
                    applyFilter(selectedFilter)
                }
            )
            Text(text = "Tritanopia", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.Bold)
        }

        // Checkbox para Deuteranopia
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = selectedFilter == "Deuteranopia",
                onCheckedChange = {
                    selectedFilter = if (it) "Deuteranopia" else ""
                    applyFilter(selectedFilter)
                }
            )
            Text(text = "Deuteranopia", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.Bold)
        }

        // Checkbox para Protanopia
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = selectedFilter == "Protanopia",
                onCheckedChange = {
                    selectedFilter = if (it) "Protanopia" else ""
                    applyFilter(selectedFilter)
                }
            )
            Text(text = "Protanopia", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.Bold)
        }

        // Opção para remover o filtro
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = selectedFilter == "Default",
                onCheckedChange = {
                    selectedFilter = if (it) "Default" else ""
                    applyFilter(selectedFilter)
                }
            )
            Text(text = "Remover Filtro", modifier = Modifier.padding(start = 8.dp), style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.Bold)
        }
    }
}
