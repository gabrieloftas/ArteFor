package com.example.myapplication
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.io.InputStream
import android.util.Log

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ObraCrudScreen(navController: NavController) {
    var obras by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var showAddDialog by remember { mutableStateOf(false) }

    // Variáveis para armazenar os valores de entrada no diálogo de adição/edição
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageBase64 by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }

    // Variáveis para o modo de edição
    var isEditMode by remember { mutableStateOf(false) }
    var obraIdToEdit by remember { mutableStateOf("") }

    // Função para redefinir o modo de edição e limpar os campos
    fun resetEditMode() {
        isEditMode = false
        obraIdToEdit = ""
        title = ""
        author = ""
        year = ""
        description = ""
        imageBase64 = ""
        genero = ""
    }

    // Função para carregar as obras do Firestore
    fun loadObras() {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras")
            .get()
            .addOnSuccessListener { result ->
                val obrasList = mutableListOf<Map<String, Any>>()
                for (document in result) {
                    val obraData = document.data.toMutableMap()
                    obraData["id"] = document.id
                    obrasList.add(obraData)
                }
                obras = obrasList
            }
            .addOnFailureListener { exception ->
                println("Erro ao buscar obras: $exception")
            }
    }

    // Função para excluir uma obra do Firestore
    fun deleteObra(obraId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras").document(obraId)
            .delete()
            .addOnSuccessListener {
                println("Obra excluída com sucesso!")
                loadObras() // Atualiza a lista após exclusão
            }
            .addOnFailureListener { e ->
                println("Erro ao excluir obra: $e")
            }
    }

    // Carregar as obras ao inicializar a tela
    LaunchedEffect(Unit) {
        loadObras()
    }

    // Função para adicionar ou atualizar uma obra no Firestore
    fun addOrUpdateObra() {
        Log.d("ObraCrudScreen", "Tentando adicionar/atualizar obra com imageBase64: $imageBase64") // Adiciona log para verificar imageBase64
        val db = FirebaseFirestore.getInstance()
        val obraData = hashMapOf(
            "title" to title,
            "author" to author,
            "year" to year,
            "description" to description,
            "imageBase64" to imageBase64,
            "genero" to genero
        )

        if (isEditMode && obraIdToEdit.isNotBlank()) {
            // Atualiza a obra existente
            db.collection("obras").document(obraIdToEdit)
                .set(obraData)
                .addOnSuccessListener {
                    println("Obra atualizada com sucesso!")
                    loadObras()
                    resetEditMode()
                }
                .addOnFailureListener { e ->
                    println("Erro ao atualizar obra: $e")
                }
        } else {
            // Adiciona uma nova obra
            db.collection("obras")
                .add(obraData)
                .addOnSuccessListener { documentReference ->
                    println("Obra adicionada com sucesso com ID: ${documentReference.id}")
                    loadObras()
                    resetEditMode()
                }
                .addOnFailureListener { e ->
                    println("Erro ao adicionar obra: $e")
                }
        }
    }

    // Função para converter URI de imagem para String Base64
    fun encodeImageToBase64(uri: Uri?) {
        uri?.let {
            try {
                val inputStream: InputStream? = navController.context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                Log.d("ObraCrudScreen", "Imagem codificada em Base64 com sucesso.") // Log para confirmar codificação
            } catch (e: Exception) {
                Log.e("ObraCrudScreen", "Erro ao codificar imagem para Base64", e)
            }
        }
    }

    // ActivityResultLauncher para selecionar a imagem
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            encodeImageToBase64(uri)
        }
    )

    // Layout principal com scroll
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("CRUD de Obras", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Adicionar rolagem à lista de obras
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Botão de Adicionar
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(0xFF37474F), shape = RoundedCornerShape(8.dp))
                        .clickable {
                            resetEditMode() // Limpa o formulário para adição
                            showAddDialog = true
                        }
                        .padding(16.dp)
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Exibir cada obra em uma coluna vertical
            items(obras) { obra ->
                val obraId = obra["id"] as? String ?: ""
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                ) {
                    obra["imageBase64"]?.let { base64Str ->
                        val bitmap = decodeBase64ToBitmap(base64Str as String)
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Imagem da Obra",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(bottom = 8.dp)
                            )
                        }
                    }
                    Text("Título: ${obra["title"]}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Autor: ${obra["author"]}", fontSize = 14.sp)
                    Text("Ano: ${obra["year"]}", fontSize = 14.sp)
                    Text("Descrição: ${obra["description"]}", fontSize = 12.sp, maxLines = 2)
                    Text("Genero: ${obra["genero"]}", fontSize = 12.sp, maxLines = 2)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = {
                            // Preenche os campos com os dados atuais para edição
                            isEditMode = true
                            obraIdToEdit = obraId
                            title = obra["title"] as String
                            author = obra["author"] as String
                            year = obra["year"] as String
                            description = obra["description"] as String
                            imageBase64 = obra["imageBase64"] as String
                            genero = obra["genero"] as String
                            showAddDialog = true
                        }) {
                            Text("Editar", color = Color(0xFF1976D2))
                        }
                        TextButton(onClick = { deleteObra(obraId) }) {
                            Text("Excluir", color = Color(0xFFD32F2F))
                        }
                    }
                }
            }
        }
    }

    // Diálogo para adicionar ou editar uma obra
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                resetEditMode()
                showAddDialog = false
            },
            title = { Text(text = if (isEditMode) "Editar Obra" else "Adicionar Nova Obra") },
            text = {
                Column {
                    TextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
                    TextField(value = author, onValueChange = { author = it }, label = { Text("Autor") })
                    TextField(value = year, onValueChange = { year = it }, label = { Text("Ano") })
                    TextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") })
                    Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text("Selecionar Imagem")
                    }
                    TextField(value = genero, onValueChange = { genero = it }, label = { Text("Genero") })
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        addOrUpdateObra()
                        showAddDialog = false
                    }
                ) {
                    Text(if (isEditMode) "Atualizar" else "Adicionar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    resetEditMode()
                    showAddDialog = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

