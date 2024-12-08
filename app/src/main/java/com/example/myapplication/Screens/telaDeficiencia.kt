package com.example.myapplication.Screens

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.Screen


@Composable
fun telaDeficiencia(navController : NavController) {
    ParteRoxa(navController)


}
@Composable
fun ParteRoxa(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {


        val customColor = Color(0xFF007BFF)
        Row(
            modifier = Modifier
                .background(customColor)
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = com.example.myapplication.R.drawable.uniforlogo),
                contentDescription = stringResource(id = R.string.abstrata_content_description),
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row() {
            RadioButtonM3(navController)

        }

    }
}

@Composable
fun RadioButtonM3(navController: NavController){
    val options = listOf("Daltonismo","Visão","Surdez")
    var selectedOption by remember { mutableStateOf(options[0]) }
    val customColor = Color(0xFF007BFF)
    Column(
        modifier = Modifier.fillMaxSize()


    ){
        options.forEach{option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .selectable(selected = selectedOption == option,
                        onClick = { selectedOption = option })
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                if(option == "Surdez"){
                    RadioButton(selected = selectedOption == option, onClick = { selectedOption = option },
                        colors = RadioButtonColors( selectedColor = Color(0xFF007BFF),unselectedColor = Color(0xFF007BFF), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.access_icon),
                        contentDescription = "Ícone Surdez",
                        modifier = Modifier.size(80.dp)
                    )
                }
                else{
                RadioButton(selected = selectedOption == option, onClick = { selectedOption = option },
                    colors = RadioButtonColors( selectedColor = Color(0xFF007BFF),unselectedColor = Color(0xFF007BFF), disabledSelectedColor = Color.White, disabledUnselectedColor = Color.White)
                )
                Text(text = option,fontSize = 30.sp,
                    )
            }
                }

        }
        Button(onClick = { navController.navigate(Screen.TelaCategoria.route) },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(Color(0xFF007BFF)))
        {
            Text(text = "Confirmar")

        }
    }
}

