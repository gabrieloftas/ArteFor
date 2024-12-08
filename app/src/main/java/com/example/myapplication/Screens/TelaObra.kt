package com.example.myapplication.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R


@Composable
fun TelaObra(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Seção da imagem com a caneta sobreposta
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            Image(
                painter = painterResource(id = com.example.myapplication.R.drawable.abstrata),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Ícone de caneta vermelha sobreposto na imagem
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar",
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd) // Alinha no canto superior direito da imagem
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.3f)) // Fundo semitransparente para destacar
            )
        }

        // Seção de texto com a caneta abaixo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vlibras ou Descrição",
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )

            // Ícone de caneta vermelha abaixo do texto
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar Texto",
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 8.dp)
                    .align(alignment = Alignment.End)// Espaçamento entre o texto e a caneta
            )
        }
    }
}