package com.example.destinasi.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.destinasi.viewmodel.DetailViewModel
import com.example.destinasi.viewmodel.MainViewModel
import com.example.destinasi.ui.theme.screen.DetailScreen
import com.example.destinasi.ui.theme.screen.MainScreen
import com.example.destinasi.util.ViewModelFactory

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object Detail : Screen("detail_screen") {
        fun withArgs(destinasiId: Long?): String {
            return if (destinasiId != null) {
                "detail_screen?destinasiId=$destinasiId"
            } else {
                "detail_screen"
            }
        }
    }
}

const val DESTINASI_ID_ARG = "destinasiId"

@Composable

fun AppNavigation(navController: NavHostController) {
    val factory = ViewModelFactory(LocalContext.current.applicationContext)

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            val mainViewModel: MainViewModel = viewModel(factory = factory)
            MainScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(
            route = Screen.Detail.route + "?$DESTINASI_ID_ARG={$DESTINASI_ID_ARG}",
            arguments = listOf(
                navArgument(DESTINASI_ID_ARG) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val destinasiId = backStackEntry.arguments?.getLong(DESTINASI_ID_ARG)
            val detailViewModel: DetailViewModel = viewModel(factory = factory)
            DetailScreen(
                navController = navController,
                viewModel = detailViewModel,
                destinasiId = if (destinasiId == -1L) null else destinasiId
            )
        }
    }
}