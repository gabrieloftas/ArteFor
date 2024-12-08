package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun Modernismo(navController: NavController){
    var menuExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val customColor = Color(0xFF007BFF)
        Row(modifier = Modifier.fillMaxWidth().background(customColor).padding(30.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Icon(
                Icons.Rounded.Menu,
                contentDescription = stringResource(id = R.string.Menu),
                modifier = Modifier
                    .size(60.dp)
                    .clickable { menuExpanded = !menuExpanded }, // Expande o menu ao clicar
                tint = Color.White
            )

            // DropdownMenu ao clicar no ícone de menu
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false } // Fecha o menu ao clicar fora
            ) {
                DropdownMenuItem(
                    text = { Text("Configurações") },
                    onClick = {
                        navController.navigate(Screen.telaconfiguracoes.route)
                    }
                )
            }

            Icon(
                Icons.Rounded.Search,
                contentDescription = stringResource(id = R.string.Search),
                modifier = Modifier.size(60.dp), tint = Color.White
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
            val imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))



            Image(
                painter = painterResource(id = R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,

                modifier = imageModifier.size(140.dp)
                    .clickable { navController.navigate(Screen.Screen1) },
            )
            Image(
                painter = painterResource(id = R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,

                modifier = imageModifier.size(140.dp)
                    .clickable { navController.navigate(Screen.Screen1) },
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
            val imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)) // Borda arredondada
                .clip(RoundedCornerShape(16.dp))



            Image(

                painter = painterResource(id = R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,

                modifier = imageModifier.size(140.dp)
                    .clickable { navController.navigate(Screen.Screen1)},
            )
            Image(
                painter = painterResource(id = R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,

                modifier = imageModifier.size(140.dp)
                    .clickable { navController.navigate(Screen.Screen1) },
            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
            val imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp)) // Borda arredondada
                .clip(RoundedCornerShape(16.dp))


            Image(
                painter = painterResource(id = R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,

                modifier = imageModifier.size(140.dp)
                    .clickable { navController.navigate(Screen.Screen1) },
            )
            Image(
                painter = painterResource(id = R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,

                modifier = imageModifier.size(140.dp)
                    .clickable { navController.navigate(Screen.Screen1) },
            )
        }
    }
}

