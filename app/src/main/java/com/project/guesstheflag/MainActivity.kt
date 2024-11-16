package com.example.guesstheflag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room.databaseBuilder
import com.example.guesstheflag.ui.theme.GuessTheFlagTheme
import com.project.guesstheflag.Navigation
import kotlinx.coroutines.launch
import com.project.guesstheflag.room.DataBase
import com.project.guesstheflag.room.LeaderBoardModel
import com.project.guesstheflag.screens.Menu
import com.project.guesstheflag.screens.GameScreen
// import com.project.guesstheflag.screens.LeaderBoardScreen
// import com.project.guesstheflag.screens.ExitScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = databaseBuilder(
             applicationContext,
             DataBase::class.java, "leaderboardDataBase"
        ).build()

        setContent {
            GuessTheFlagTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Navigation.menu
                ) {
                    composable(Navigation.menu) { Menu(navController = navController) }
                    composable(Navigation.game) { GameScreen(navController = navController) }
                    // composable(Navigation.leaderBoard) {
                    //     LeaderBoardScreen(navController = navController, db = db)
                    // }
                    // composable(
                    //     Navigation.exit,
                    //     arguments = listOf(navArgument("points") { type = NavType.IntType })
                    // ) { backStackEntry ->
                    //     val points = backStackEntry.arguments?.getInt("points") ?: 0
                    //     ExitScreen(
                    //         navController = navController,
                    //         points = points,
                    //         db = db
                    //     ) { name ->
                    //         lifecycleScope.launch {
                    //             val leaderboard = LeaderBoardModel(name = name, points = points)
                    //             db.LeaderBoardDTO().insert(leaderboard)
                    //         }
                    //     }
                    // }
                }
            }
        }
    }
}