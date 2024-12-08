package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.Screens.Cubismo
import com.example.myapplication.Screens.TelaAdmin
import com.example.myapplication.Screens.TelaArte
import com.example.myapplication.Screens.TelaCategoriaADM
import com.example.myapplication.Screens.TelaObra
import com.example.myapplication.Screens.TelaObraVisitante
import com.example.myapplication.Screens.configuracoes

import com.example.myapplication.Screens.telaDeficiencia


@Composable
fun Navigation(applyFilter: (String) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.telaInicial.route) {
        composable(route = Screen.telaInicial.route) {
            telaInicial(navController = navController)
        }
        composable(route = Screen.telaDeficiencia.route) {
            telaDeficiencia(navController = navController)
        }
        composable(route = Screen.Login.route) {
            Login(navController = navController)
        }
        composable(route = Screen.TelaCategoria.route) {
            TelaCategoria(navController = navController)
        }
        composable(route = Screen.TelaCategoriaADM.route) {
            TelaCategoriaADM(navController = navController)
        }
        composable(route = Screen.TelaArte.route) {
            TelaArte(navController = navController)
        }
        composable(route = Screen.TelaAdmin.route) {
            TelaAdmin(navController = navController)
        }
        composable(route = Screen.TelaObra.route) {
            TelaObra(navController = navController)
        }
        composable(
            route = Screen.Screen1.TelaObraVisitante,
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType },
                navArgument("imageBase64") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "Título desconhecido"
            val description = backStackEntry.arguments?.getString("description") ?: "Descrição indisponível"
            val imageBase64 = backStackEntry.arguments?.getString("imageBase64") ?: ""

            TelaObraVisitante(
                navController = navController,
                title = title,
                description = description,
                imageBase64 = imageBase64
            )
        }
        composable(route = Screen.telaconfiguracoes.route) {
            configuracoes(navController = navController, applyFilter = applyFilter)
        }
        composable(route = Screen.Abstrata.route){
           AbstrataScreen(navController = navController)
        }
        composable(route = Screen.Cubismo.route){
            CubismoScreen(navController = navController)
        }
        composable(route = Screen.Impressionismo.route){
            ImpressionismoScreen(navController = navController)
        }
        composable(route = Screen.Modernismo.route){
            ModernismoScreen(navController = navController)
        }
        composable(route = Screen.Realismo.route){
            RealismoScreen(navController = navController)
        }
        composable(route = Screen.Surrealismo.route){
            SurrealismoScreen(navController = navController)
        }
        composable(route = Screen.TelaCRUDobras.route){
            ObraCrudScreen(navController = navController)
        }
        composable(
            route = "obra_screen/{genero}",
            arguments = listOf(
                navArgument("genero") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genero = backStackEntry.arguments?.getString("genero") ?: "Abstrata"
            ObraScreen(navController = navController, genero = genero)
        }

        composable(
            route = "tela_obra_visitante/{obraId}",
            arguments = listOf(
                navArgument("obraId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val obraId = backStackEntry.arguments?.getString("obraId") ?: ""
            TelaObraVisitante(navController = navController, obraId = obraId)
        }
        composable(
            route = "obra_screen_add/{genero}",
            arguments = listOf(
                navArgument("genero") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genero = backStackEntry.arguments?.getString("genero") ?: "Abstrata"
            ObraScreenWithAdd(navController = navController, genero = genero)
        }
        composable(route = Screen.AbstrataADM.route){
            AbstrataScreenAdm(navController = navController)
        }
        composable(route = Screen.CubismoADM.route){
            CubismoScreenAdm(navController = navController)
        }
        composable(route = Screen.ImpressionismoADM.route){
            ImpressionismoScreenAdm(navController = navController)
        }
        composable(route = Screen.ModernismoADM.route){
            ModernismoScreenAdm(navController = navController)
        }
        composable(route = Screen.RealismoADM.route){
            RealismoScreenAdm(navController = navController)
        }
        composable(route = Screen.SurrealismoADM.route){
            SurrealismoScreenAdm(navController = navController)
        }
    }
}





@Composable
fun telaInicial(navController: NavController){
    val customColor = Color(0xFF007BFF)
    Box(modifier = Modifier.fillMaxSize().background(customColor),



        ) {
        Row(
            modifier = Modifier.align(Alignment.TopEnd),


            ) {

            Column(
                modifier = Modifier.padding(24.dp).background(Color.White, shape = RoundedCornerShape(12.dp)).height(33.dp)




            ) {



                Administrador(navController)
            }
        }
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Column(
                modifier = Modifier.padding(24.dp).background(Color.White, shape = RoundedCornerShape(12.dp)).width(200.dp)


            ) {


                Visitante(navController);

            }

        }
    }


}
    @Composable
    fun Administrador(navController: NavController) {
        TextButton(
            onClick = { navController.navigate(Screen.Login.route) }

        ) {
            Text("Administrador", color = Color.Black)
        }
    }

@Composable
fun Visitante(navController : NavController) {
    TextButton(modifier = Modifier.width(200.dp),
        onClick = { navController.navigate(Screen.telaDeficiencia.route) }
    ) {
        Text(
            "Visitante", color = Color.Black, fontSize = 30.sp)
    }
}

@Composable
fun telaDeficiencia2(navController : NavController) {
    Text("TelaDeficiencia")


}


