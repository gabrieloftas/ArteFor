package com.example.myapplication


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.Screen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun Login(navController: NavController) {
    var fb = Firebase.firestore

    Box(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally


        ) {
            Image(
                painter = painterResource(id = com.example.myapplication.R.drawable.icon_unifor),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(300.dp).padding(80.dp)



                )


            val loginText = remember { mutableStateOf("") }
            OutlinedTextField(
                value = loginText.value,
                onValueChange = { loginText.value = it },
                label = { Text("Login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(30.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor =  White,
                    focusedContainerColor = White// Define o fundo como branco
                        )
            )


            val passwordText = remember { mutableStateOf("") }
            OutlinedTextField(
                value = passwordText.value,
                onValueChange = { passwordText.value = it },
                label = { Text("Senha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(30.dp)
            )


            Button(


                onClick = {
                    val fb = Firebase.firestore
                    fb.collection("Registro")
                        .document("fZQn5dm1ZsnrMGhVDJol")
                        .get().addOnSuccessListener {
                            document ->
                            var emailverif = document.get("usuario").toString()
                            var senhaverif = document.get("senha").toString()

                            if (emailverif == loginText.value && senhaverif == passwordText.value ) {
                                navController.navigate(Screen.TelaCategoriaADM.route)
                            }
                            }


                },


                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)
                )

            ) {
                Text(text = "Acessar")

            }
        }
    }
}