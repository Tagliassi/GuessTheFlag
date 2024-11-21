package com.project.guesstheflag.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.guesstheflag.room.LeaderBoardDTO
import com.project.guesstheflag.room.LeaderBoardModel
import com.project.guesstheflag.viewmodel.LeaderBoardViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderBoardScreen(navController: NavController, leaderBoardViewModel: LeaderBoardViewModel) {
    val leaderboard by leaderBoardViewModel.leaderboard.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leaderboard", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🏆 Top 10 Players 🏆",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (leaderboard.isEmpty()) {
                        Text(
                            text = "No scores available yet.",
                            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyColumn {
                            items(leaderboard.take(10)) { player ->
                                LeaderBoardItem(player)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun LeaderBoardItem(player: LeaderBoardModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1976D2)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = player.name,
                style = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "${player.points} pts",
                style = TextStyle(fontSize = 18.sp, color = Color.White)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaderBoardScreen() {
    val mockLeaderboard = listOf(
        LeaderBoardModel(name = "Alice", points = 120),
        LeaderBoardModel(name = "Bob", points = 110),
        LeaderBoardModel(name = "Charlie", points = 100)
    )

    LeaderBoardScreen(
        navController = rememberNavController(),
        leaderBoardViewModel = object : LeaderBoardViewModel(MockDao(mockLeaderboard)) {
            override val leaderboard = MutableLiveData(mockLeaderboard)
        }
    )
}

class MockDao(private val mockData: List<LeaderBoardModel>) : LeaderBoardDTO {
    override fun insert(leaderboard: LeaderBoardModel) {
        // No-op for preview
    }

    override fun getScores(): LiveData<List<LeaderBoardModel>> = MutableLiveData(mockData)
}
