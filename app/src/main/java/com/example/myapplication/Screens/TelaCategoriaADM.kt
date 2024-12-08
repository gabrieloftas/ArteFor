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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.R.string.Search
import com.example.myapplication.Screen

@Composable
fun TelaCategoriaADM(navController: NavController) {
    var menuExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
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
            }


            Icon(
                Icons.Rounded.Search,
                contentDescription = stringResource(id = Search),
                modifier = Modifier.size(60.dp),
                tint = Color.White
            )
        }


        val categories = listOf(
            Triple(R.drawable.abstrata, "Abstrata"){navController.navigate(Screen.AbstrataADM.route)},
            Triple(R.drawable.moderna, "Modernismo"){navController.navigate(Screen.ModernismoADM.route)},
            Triple(R.drawable.cubismo, "Cubismo"){navController.navigate(Screen.CubismoADM.route)},
            Triple(R.drawable.surrealismo, "Surrealismo"){navController.navigate(Screen.SurrealismoADM.route)},
            Triple(R.drawable.expressionismo, "Realismo"){navController.navigate(Screen.RealismoADM.route)},
            Triple(R.drawable.impressionismo, "Impressionismo"){navController.navigate(Screen.ImpressionismoADM.route)}
        )


        categories.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { (imageRes, name,navegation) ->
                    Box(
                        modifier = Modifier
                            .size(170.dp)
                            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { navegation() }
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = stringResource(id = R.string.abstrata_content_description),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                                .clickable { navegation() }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(androidx.compose.ui.Alignment.BottomCenter)
                                .background(Color.Black.copy(alpha = 0.6f))
                        ) {
                            Text(
                                text = name,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(androidx.compose.ui.Alignment.Center)
                            )

                        }
                    }
                }
            }
        }
    }
}