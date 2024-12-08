package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Impressionismo(navController: NavController){
    var menuExpanded by remember { mutableStateOf(false) }
    var obras by remember { mutableStateOf(listOf<Map<String, Any>>()) }

    // Função para carregar obras do gênero "Abstrata" do Firestore
    fun loadObrasAbstrata() {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras")
            .whereEqualTo("genero", "Abstrata")
            .get()
            .addOnSuccessListener { result ->
                val obrasList = mutableListOf<Map<String, Any>>()
                for (document in result) {
                    obrasList.add(document.data)
                }
                obras = obrasList
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar obras: $exception")
            }
    }

    // Carregar obras ao iniciar a tela
    LaunchedEffect(Unit) {
        loadObrasAbstrata()
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val customColor = Color(0xFF007BFF)
        Row(
            modifier = Modifier.fillMaxWidth().background(customColor).padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                Icons.Rounded.Menu,
                contentDescription = stringResource(id = R.string.Menu),
                modifier = Modifier
                    .size(60.dp)
                    .clickable { menuExpanded = !menuExpanded },
                tint = Color.White
            )

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
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

        // Exibir obras do gênero "Abstrata" em uma grade
        obras.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { obra ->
                    val imageBase64 = obra["imageBase64"] as? String
                    val bitmap = decodeBase64ToBitmap(imageBase64)

                    Box(
                        modifier = Modifier
                            .size(170.dp)
                            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                // Navega para a tela de detalhes da obra
                                navController.navigate(Screen.Screen1)
                            }
                    ) {
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = obra["title"] as? String,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(Color.Black.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = obra["title"] as? String ?: "Título desconhecido",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

