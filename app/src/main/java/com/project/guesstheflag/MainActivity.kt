package com.project.guesstheflag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.guesstheflag.ui.theme.GuessTheFlagTheme
import com.project.guesstheflag.room.DataBase
import com.project.guesstheflag.screens.Menu
import com.project.guesstheflag.screens.GameScreen
import com.project.guesstheflag.screens.LeaderBoardScreen
import com.project.guesstheflag.screens.ExitScreen
import com.project.guesstheflag.screens.LeaderBoardViewModelFactory
import com.project.guesstheflag.screens.SaveScoreViewModelFactory
import com.project.guesstheflag.screens.SaveScoreViewModel
import com.project.guesstheflag.screens.LeaderBoardViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            "leaderboardDataBase"
        ).build()

        // Cria uma instância dos ViewModels
        val saveScoreViewModel = ViewModelProvider(this, SaveScoreViewModelFactory(db)).get(SaveScoreViewModel::class.java)
        val leaderBoardViewModel = ViewModelProvider(this, LeaderBoardViewModelFactory(db.LeaderBoardDTO())).get(LeaderBoardViewModel::class.java)

        setContent {
            GuessTheFlagTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Navigation.menu
                ) {
                    addNavigation(navController = navController, db = db)
                    composable(Navigation.menu) { Menu(navController = navController) }
                    composable(Navigation.game) { GameScreen(navController = navController) }
                    composable(Navigation.leaderBoard) { LeaderBoardScreen(navController, leaderBoardViewModel) }

                    composable(
                        Navigation.exit,
                        arguments = listOf(navArgument("points") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val points = backStackEntry.arguments?.getInt("points") ?: 0
                        ExitScreen(
                            navController = navController,
                            points = points,
                            saveScoreViewModel = saveScoreViewModel // Passa o ViewModel para a ExitScreen
                        )
                    }
                }
            }
        }
    }
}
