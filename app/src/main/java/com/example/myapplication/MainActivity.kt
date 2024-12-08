package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtém a rootView da atividade
        val rootView = findViewById<View>(android.R.id.content)

        setContent {
            MyApplicationTheme {
                val applyFilter: (String) -> Unit = { selectedFilter ->
                    applyDaltonismFilterIfSelected(rootView, selectedFilter)
                }
                Navigation(applyFilter = applyFilter)
            }
        }
    }
}
