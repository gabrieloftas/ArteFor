package com.example.myapplication

import androidx.navigation.NavController

sealed class Screen(val route: String) {
    object telaInicial : Screen("tela_inicial")
    object telaDeficiencia : Screen("detail_screen")
    object Login : Screen("login")
    object TelaCategoria : Screen("tela_categoria")
    object TelaCategoriaADM : Screen("tela_categoriaADM")
    object TelaArte : Screen("tela_arte")
    object TelaAdmin : Screen("tela_admin")
    object TelaObra : Screen("tela_obra")
    //object TelaObraVisitante : Screen("tela_obravisitante")
    object telaconfiguracoes : Screen("tela_conficuracoes")
    object Abstrata:Screen("Abstrata")
    object Cubismo:Screen("Cubismo")
    object Impressionismo:Screen("Impressionismo")
    object Modernismo:Screen("Modernismo")
    object Realismo:Screen("Realismo")
    object Surrealismo:Screen("Surrealismo")
    object TelaCRUDobras:Screen("TelaCRUDobras")
    object Screen1 {
        const val TelaObraVisitante = "tela_obra_visitante/{title}/{description}/{imageBase64}"
        // Defina outras rotas conforme necessário
    }
    object AbstrataADM:Screen("AbstrataADM")
    object CubismoADM:Screen("CubismoADM")
    object ImpressionismoADM:Screen("ImpressionismoADM")
    object ModernismoADM:Screen("ModernismoADM")
    object RealismoADM:Screen("RealismoADM")
    object SurrealismoADM:Screen("SurrealismoADM")



}