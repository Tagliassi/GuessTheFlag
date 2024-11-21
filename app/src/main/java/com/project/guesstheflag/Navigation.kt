package com.project.guesstheflag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.guesstheflag.room.DataBase
import com.project.guesstheflag.room.LeaderBoardModel
import com.project.guesstheflag.screens.*
import com.project.guesstheflag.screens.LeaderBoardViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

object Navigation {
    const val menu = "menu"
    const val game = "game"
    const val leaderBoard = "leaderboard"
    const val exit = "exit/{points}"  // Definindo o parâmetro {points} para ser passado
}



fun NavGraphBuilder.addNavigation(navController: NavController, db: DataBase) {
    // Navegação para a tela de Menu
    composable(Navigation.menu) { Menu(navController) }

    // Navegação para a tela de Jogo
    composable(Navigation.game) { GameScreen(navController) }

    // Navegação para a tela de LeaderBoard
    composable(Navigation.leaderBoard) {
        LeaderBoardScreen(
            navController = navController,
            leaderBoardViewModel = LeaderBoardViewModel(dao = db.LeaderBoardDTO()) // Usando o ViewModel diretamente
        )
    }

    // Navegação para a tela de Exit, com o parâmetro `points`
    composable(Navigation.exit) { backStackEntry ->
        val points = backStackEntry.arguments?.getString("points")?.toIntOrNull() ?: 0

        // Passando o ViewModel para a ExitScreen
        ExitScreen(
            navController = navController,
            points = points,
            saveScoreViewModel = viewModel() // Injetando manualmente
        )
    }
}



