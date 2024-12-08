package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.firestore.FirebaseFirestore


fun addObra(title: String, author: String, year: String, description: String, imageBase64: String) {
    val db = FirebaseFirestore.getInstance()
    val obra = hashMapOf(
        "title" to title,
        "author" to author,
        "year" to year,
        "description" to description,
        "imageBase64" to imageBase64 // Novo campo para a imagem
    )

    db.collection("obras")
        .add(obra)
        .addOnSuccessListener { documentReference ->
            println("Obra adicionada com sucesso com ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Erro ao adicionar obra: $e")
        }
}



fun getObras(callback: (List<Map<String, Any>>) -> Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection("obras")
        .get()
        .addOnSuccessListener { result ->
            val obras = mutableListOf<Map<String, Any>>()
            for (document in result) {
                obras.add(document.data)
            }
            callback(obras)
        }
        .addOnFailureListener { exception ->
            println("Erro ao buscar obras: $exception")
        }
}


fun updateObra(obraId: String, title: String, author: String, year: Int, description: String, imageBase64: String) {
    val db = FirebaseFirestore.getInstance()
    val updatedData = mapOf(
        "title" to title,
        "author" to author,
        "year" to year,
        "description" to description,
        "imageBase64" to imageBase64 // Campo para atualizar a imagem
    )

    db.collection("obras").document(obraId)
        .set(updatedData)
        .addOnSuccessListener {
            println("Obra atualizada com sucesso!")
        }
        .addOnFailureListener { e ->
            println("Erro ao atualizar obra: $e")
        }
}

@Composable
fun AddObraScreen() {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageBase64 by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Adicionar Nova Obra", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        TextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
        TextField(value = author, onValueChange = { author = it }, label = { Text("Autor") })
        TextField(value = year, onValueChange = { year = it }, label = { Text("Ano") })
        TextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") })

        // Campo para inserir a imagem em Base64
        TextField(
            value = imageBase64,
            onValueChange = { imageBase64 = it },
            label = { Text("Imagem (Base64)") },
            placeholder = { Text("Cole a imagem codificada em Base64") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            addObra(title, author, year, description, imageBase64)
            // Limpar os campos
            title = ""; author = ""; year = ""; description = ""; imageBase64 = ""
        }) {
            Text("Adicionar Obra")
        }
    }
}

fun deleteObra(obraId: String) {
    val db = FirebaseFirestore.getInstance()

    db.collection("obras").document(obraId)
        .delete()
        .addOnSuccessListener {
            println("Obra excluída com sucesso!")
        }
        .addOnFailureListener { e ->
            println("Erro ao excluir obra: $e")
        }
}

fun decodeBase64ToBitmap(base64Str: String): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        null
    }
}