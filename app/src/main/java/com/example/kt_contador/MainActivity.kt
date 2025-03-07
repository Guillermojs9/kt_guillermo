package com.example.kt_contador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.kt_contador.ui.theme.Kt_contadorTheme
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App(){
    Kt_contadorTheme{
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation()
        }
    }
}

@Composable
fun FilledButtonExample(texto: String, onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(texto)
    }
}

@Composable
fun Greeting(numero: Int, modifier: Modifier = Modifier) {
    Text(
        text = "$numero",
        modifier = modifier
    )
}

@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                Column {
                    var count: Int by remember { mutableStateOf(10) }
                    Greeting(
                        numero = count,
                        modifier = Modifier.padding(innerPadding)
                    )
                    FilledButtonExample(texto = "increase") { count++ }
                    FilledButtonExample (texto = "Ir a otra pantalla"){ navController.navigate("number/$count") }
                    FilledButtonExample (texto = "Ir a MoviesScreen"){ navController.navigate("movies") }
                    FilledButtonExample (texto = "Ir a GraficoScreen"){ navController.navigate("grafico") }
                }
            }
        }
    }
}

@Composable
fun NumberScreen(navController: NavController){
    val number = navController.currentBackStackEntry?.arguments?.getInt("number") ?: 0
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Column {
                    Text(text = "NumberScreen, $number")
                    FilledButtonExample(texto = "Volver a home") { navController.navigate("home");
                    }
                }
            }
    }
}

@Composable
fun GraficoScreen(){
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            columnSeries { series(5, 6, 5, 2, 11, 8, 5, 2, 15, 11, 8, 13, 12, 10, 2, 7) }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CartesianChartHost(
            rememberCartesianChart(
                rememberColumnCartesianLayer(),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
            ),
            modelProducer,
        )
        }
}

@Composable
fun MoviesScreen(){
    val viewModel: MoviesViewModel = viewModel();
    val movieResponse by viewModel.movies.observeAsState()
    val movies = movieResponse?.results;
    LaunchedEffect(Unit) {
        viewModel.fetchMovies()
    }
    Column{
        if (movies != null) {
            if(movies.isEmpty()){
                Text("No han cargado las películas");
            }else{
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(movies) { result ->
                        MovieItem(result)
                    }
                }
            }
        }else{
            Text("Cargando películas...")
        }
    }
}

@Composable
fun MovieItem(movie: Result){
        Column ( modifier = Modifier.fillMaxWidth()
        ){
            AsyncImage(model = "https://image.tmdb.org/t/p/w500${movie.posterPath}", contentDescription = "Descripción")
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = movie.title,style = MaterialTheme.typography.titleMedium, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.overview)
            Spacer(modifier = Modifier.height(35.dp))
        }

}



@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("movies") {
            MoviesScreen()
        }
        composable("grafico") {
            GraficoScreen()
        }
        composable("number/{number}", arguments = listOf(navArgument("number"){
            type = NavType.IntType
        })) {
            NumberScreen(navController = navController)
        }
    }
}