package com.example.myapplication.Screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun TelaObraVisitante(navController: NavController, title: String, description: String, imageBase64: String) {
    val obraTitle = "Minha Obra"
    val obraDescription = "Descrição da obra"
    val obraImageBase64 = "imagemCodificadaEmBase64"

    navController.navigate("tela_obra_visitante/$obraTitle/$obraDescription/$obraImageBase64")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Decodifica a imagem base64 para Bitmap
        val bitmap = decodeBase64ToBitmap(imageBase64)

        // Seção da imagem com a caneta sobreposta
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Ícone de caneta vermelha sobreposto na imagem
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar",
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.3f))
            )
        }

        // Seção de texto com a descrição da obra
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(color = Color.DarkGray)
            )

            // Ícone de caneta vermelha abaixo do texto
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar Texto",
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 8.dp)
            )
        }
    }
}


