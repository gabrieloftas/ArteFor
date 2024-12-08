package com.example.myapplication

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ObraScreen(navController: NavController, genero: String) {
    var menuExpanded by remember { mutableStateOf(false) }
    var obras by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var searchQuery by remember { mutableStateOf("") }
    var searchVisible by remember { mutableStateOf(false) }

    // Função para carregar obras do gênero especificado do Firebase
    fun loadObrasByGenero() {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras")
            .whereEqualTo("genero", genero)
            .get()
            .addOnSuccessListener { result ->
                val obrasList = mutableListOf<Map<String, Any>>()
                for (document in result) {
                    val obra = document.data.toMutableMap()
                    obra["id"] = document.id // Salva o ID do documento
                    obrasList.add(obra)
                }
                obras = obrasList
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar obras: $exception")
            }
    }

    // Carregar obras ao iniciar a tela
    LaunchedEffect(genero) {
        loadObrasByGenero()
    }

    // Filtra as obras com base no texto de pesquisa
    val filteredObras = if (searchQuery.isEmpty()) obras else {
        obras.filter { obra ->
            (obra["title"] as? String)?.contains(searchQuery, ignoreCase = true) == true
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Cabeçalho e Menu
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.Blue).padding(30.dp),
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
                modifier = Modifier
                    .size(60.dp)
                    .clickable { searchVisible = !searchVisible },
                tint = Color.White
            )
        }

        // Campo de busca
        if (searchVisible) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        // Exibir obras filtradas do gênero especificado em uma grade
        filteredObras.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { obra ->
                    // Extrair valores da obra
                    val obraId = obra["id"] as? String ?: ""
                    val obraTitle = obra["title"] as? String ?: "Título desconhecido"
                    val obraImageBase64 = obra["imageBase64"] as? String ?: ""

                    val bitmap = decodeBase64ToBitmap(obraImageBase64)

                    Box(
                        modifier = Modifier
                            .size(170.dp)
                            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                // Navega para a tela de detalhes da obra passando o obraId
                                navController.navigate("tela_obra_visitante/$obraId")
                            }
                    ) {
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = obraTitle,
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
                                text = obraTitle,
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

// Função para decodificar a imagem Base64 para Bitmap
fun decodeBase64ToBitmap(base64Str: String?): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        null
    }
}

@Composable
fun AbstrataScreen(navController: NavController) {
    ObraScreen(navController = navController, genero = "Abstrata")
}

@Composable
fun CubismoScreen(navController: NavController) {
    ObraScreen(navController = navController, genero = "Cubismo")
}

@Composable
fun ImpressionismoScreen(navController: NavController) {
    ObraScreen(navController = navController, genero = "Impressionismo")
}

@Composable
fun ModernismoScreen(navController: NavController) {
    ObraScreen(navController = navController, genero = "Modernismo")
}

@Composable
fun RealismoScreen(navController: NavController) {
    ObraScreen(navController = navController, genero = "Realismo")
}

@Composable
fun SurrealismoScreen(navController: NavController) {
    ObraScreen(navController = navController, genero = "Surrealismo")
}
@Composable
fun TelaObraVisitante(navController: NavController, obraId: String) {
    var obra by remember { mutableStateOf<Map<String, Any>?>(null) }

    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val minScale = 1f
    val maxScale = 2f

    // Função para buscar detalhes da obra pelo obraId
    fun loadObraDetails() {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras").document(obraId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    obra = document.data
                } else {
                    println("Obra não encontrada")
                }
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar detalhes da obra: $exception")
            }
    }

    // Carregar detalhes da obra ao iniciar a tela
    LaunchedEffect(obraId) {
        loadObraDetails()
    }

    // Layout da tela de detalhes
    obra?.let { obraData ->
        val title = obraData["title"] as? String ?: "Título desconhecido"
        val description = obraData["description"] as? String ?: "Descrição indisponível"
        val imageBase64 = obraData["imageBase64"] as? String ?: ""
        val bitmap = decodeBase64ToBitmap(imageBase64)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Exibição da imagem
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
                            .graphicsLayer(
                                scaleX = zoom,
                                scaleY = zoom,
                                translationX = offsetX,
                                translationY = offsetY,
                            )
                            .pointerInput(Unit){
                                detectTransformGestures(
                                    onGesture = { _, pan, gestureZoom, _ ->
                                        zoom = (zoom * gestureZoom).coerceIn(minScale, maxScale)
                                        if(zoom > 1) {
                                            offsetX += pan.x * zoom
                                            offsetY += pan.y * zoom
                                        }else{
                                            offsetX = 0f
                                            offsetY = 0f
                                        }
                                    }
                                )

                            }
                    )
                }
            }

            // Texto e descrição
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

            }

            // VLibras no WebView
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        // Configurações do WebView
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            allowContentAccess = true
                            allowFileAccess = true
                            javaScriptCanOpenWindowsAutomatically = true
                        }

                        webViewClient = object : WebViewClient() {
                            override fun onReceivedError(
                                view: WebView,
                                errorCode: Int,
                                description: String?,
                                failingUrl: String?
                            ) {
                                println("Erro no WebView: $description")
                            }

                            override fun onPageFinished(view: WebView, url: String?) {
                                super.onPageFinished(view, url)
                                println("Página carregada com sucesso: $url")
                            }
                        }

                        // HTML para VLibras
                        val vlibrasHtml = """
                            <!DOCTYPE html>
                            <html>
                            <head>
                                <meta charset="UTF-8">
                                <title>VLibras Demo</title>
                                <script src="https://vlibras.gov.br/app/vlibras-plugin.js"></script>
                            </head>
                            <body>
                                <div vw class="enabled">
                                    <div vw-access-button class="active"></div>
                                    <div vw-plugin-wrapper>
                                        <div class="vw-plugin-top-wrapper"></div>
                                    </div>
                                </div>
                                <p id="text">$description</p>

                                <script>
                                    new window.VLibras.Widget('https://vlibras.gov.br/app');
                                </script>
                            </body>
                            </html>
                        """.trimIndent()

                        // Carrega o HTML
                        loadDataWithBaseURL(null, vlibrasHtml, "text/html", "UTF-8", null)
                    }
                },
                update = { webView ->
                    // Atualiza o conteúdo, se necessário
                    webView.reload()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Ajusta para ocupar o espaço restante
            )
        }
    }
}


@Composable
fun AbstrataScreenAdm(navController: NavController) {
    ObraScreenWithAdd(navController = navController, genero = "Abstrata")
}

@Composable
fun CubismoScreenAdm(navController: NavController) {
    ObraScreenWithAdd(navController = navController, genero = "Cubismo")
}

@Composable
fun ImpressionismoScreenAdm(navController: NavController) {
    ObraScreenWithAdd(navController = navController, genero = "Impressionismo")
}

@Composable
fun ModernismoScreenAdm(navController: NavController) {
    ObraScreenWithAdd(navController = navController, genero = "Modernismo")
}

@Composable
fun RealismoScreenAdm(navController: NavController) {
    ObraScreenWithAdd(navController = navController, genero = "Realismo")
}

@Composable
fun SurrealismoScreenAdm(navController: NavController) {
    ObraScreenWithAdd(navController = navController, genero = "Surrealismo")
}
@Composable
fun ObraScreenWithAdd(navController: NavController, genero: String) {
    var menuExpanded by remember { mutableStateOf(false) }
    var obras by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var searchQuery by remember { mutableStateOf("") }
    var searchVisible by remember { mutableStateOf(false) }

    // Função para carregar obras do gênero especificado do Firebase
    fun loadObrasByGenero() {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras")
            .whereEqualTo("genero", genero)
            .get()
            .addOnSuccessListener { result ->
                val obrasList = mutableListOf<Map<String, Any>>()
                for (document in result) {
                    val obra = document.data.toMutableMap()
                    obra["id"] = document.id // Salva o ID do documento
                    obrasList.add(obra)
                }
                obras = obrasList
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar obras: $exception")
            }
    }

    // Carregar obras ao iniciar a tela
    LaunchedEffect(genero) {
        loadObrasByGenero()
    }

    // Filtra as obras com base no texto de pesquisa
    val filteredObras = if (searchQuery.isEmpty()) obras else {
        obras.filter { obra ->
            (obra["title"] as? String)?.contains(searchQuery, ignoreCase = true) == true
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Cabeçalho e Menu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(30.dp),
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
                modifier = Modifier
                    .size(60.dp)
                    .clickable { searchVisible = !searchVisible },
                tint = Color.White
            )
        }

        // Campo de busca
        if (searchVisible) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        // Botão "ADD" de largura total
        Button(
            onClick = { navController.navigate(Screen.TelaCRUDobras.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "+ ADD", fontSize = 20.sp)
        }

        // Lista de obras com scroll vertical
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredObras) { obra ->
                val obraId = obra["id"] as? String ?: ""
                val obraTitle = obra["title"] as? String ?: "Título desconhecido"
                val obraImageBase64 = obra["imageBase64"] as? String ?: ""

                val bitmap = decodeBase64ToBitmap(obraImageBase64)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            // Navega para a tela de detalhes da obra passando o obraId
                            navController.navigate("tela_obra_visitante/$obraId")
                        }
                ) {
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = obraTitle,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = obraTitle,
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




