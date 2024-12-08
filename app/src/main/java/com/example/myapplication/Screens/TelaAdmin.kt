package com.example.myapplication.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen


@Composable
fun TelaAdmin(navController: NavController) {
    var menuExpanded by remember { mutableStateOf(false) } // Estado do DropdownMenu

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        // Top bar com DropdownMenu no primeiro ícone
        val customColor = Color(0xFF007BFF)
        Row(

            modifier = Modifier
                .fillMaxWidth()
                .background(customColor)
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
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
            }

            // Ícone de pesquisa
            Icon(
                Icons.Rounded.Search,
                contentDescription = stringResource(id = R.string.Search),
                modifier = Modifier.size(60.dp),
                tint = Color.White
            )
        }

        // Restante do layout (fileiras de imagens)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))


            // Imagem com caneta vermelha no centro
            ImageWithIcon(navController = navController,imageModifier = imageModifier)

            // Outra imagem com caneta vermelha no centro
            ImageWithIcon(navController = navController,imageModifier = imageModifier)
        }

        // Outra fileira de imagens
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageWithIcon(navController = navController,
                imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
            )
            ImageWithIcon(navController = navController,
                imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ImageWithIcon(navController = navController,
                imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
            )
            ImageWithIcon(navController = navController,
                imageModifier = Modifier
                .size(170.dp)
                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun ImageWithIcon(imageModifier: Modifier,navController: NavController) {
    Box(
        modifier = imageModifier
            .clickable { navController.navigate(Screen.TelaCRUDobras.route) }
    ) {
        // Exibe a imagem
        Image(
            painter = painterResource(id = R.drawable.telapreta),
            contentDescription = stringResource(id = R.string.abstrata_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Ícone da caneta no centro
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar",
            tint = Color.Red,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center) // Centraliza a caneta na imagem
        )
    }
}