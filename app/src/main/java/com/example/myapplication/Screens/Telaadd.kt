package com.example.myapplication.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun Telaadd(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
    ) {
        // Seção da imagem com a caneta sobreposta

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Color.Black)
            ) {

            Icon(
                Icons.Rounded.Add,
                contentDescription = stringResource(id = R.string.Menu),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(75.dp)
                    .size(250.dp)
                    .clickable {  /*açao*/ }, // Expande o menu ao clicar
                tint = Color.Red

            )
        }
        // Seção de texto com a caneta abaixo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val Titulo = remember { mutableStateOf("") }
            OutlinedTextField(
                value = Titulo.value,
                onValueChange = { Titulo.value = it },
                label = { Text("Editar Titulo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp)
            )

            val Artista = remember { mutableStateOf("") }
            OutlinedTextField(
                value = Artista.value,
                onValueChange = { Artista.value = it },
                label = { Text("Editar Artista") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp)
            )

            val Descrição = remember { mutableStateOf("") }
            OutlinedTextField(
                value = Descrição.value,
                onValueChange = { Descrição.value = it },
                label = { Text("Editar descrição") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(10.dp)
            )

            // Ícone de caneta vermelha abaixo do texto
            //Icon(
            //    imageVector = Icons.Default.Edit,
            //    contentDescription = "Editar Texto",
            //    tint = Color.Red,
            //    modifier = Modifier
            //        .size(40.dp)
            //        .padding(top = 8.dp)
            //        .align(alignment = Alignment.End)// Espaçamento entre o texto e a caneta
            //)
        }


        // Ícone de caneta vermelha sobreposto na imagem
        //Icon(
        //    imageVector = Icons.Default.Edit,
        //    contentDescription = "Editar",
        //    tint = Color.Red,
        //    modifier = Modifier
        //        .size(40.dp)
        //       .align(Alignment.TopEnd) // Alinha no canto superior direito da imagem
        //    .padding(16.dp)
        //   .background(Color.White.copy(alpha = 0.3f)) // Fundo semitransparente para destacar
        //)


    }

    }
