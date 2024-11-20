
package com.project.guesstheflag

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.guesstheflag.screens.GameScreen
import com.project.guesstheflag.screens.LeaderBoardScreen
import com.project.guesstheflag.screens.Menu

object Navigation {
    const val menu = "menu"
    const val game = "game"
    const val leaderBoard = "leaderboard"
    const val exit = "exit/{points}"
}

fun NavGraphBuilder.addNavigation(navController: NavController) {
    composable(Navigation.menu) { Menu(navController) }
    composable(Navigation.game) { GameScreen(navController) }
    composable(Navigation.leaderBoard) { LeaderBoardScreen(navController) }
}
