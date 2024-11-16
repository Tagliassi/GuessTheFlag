package com.project.guesstheflag.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.guesstheflag.R
import com.project.guesstheflag.Navigation

@Composable
fun Menu(navController: NavController) {
    var isDialogOpen by remember { mutableStateOf(false) }

    // Adiciona animação para os elementos
    var scale by remember { mutableStateOf(1f) }
    val animatedScale = animateFloatAsState(
        targetValue = scale,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fundo
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Título com fundo destacado
            Box(
                modifier = Modifier
                    .padding(top = 80.dp)
                    .scale(animatedScale.value)
                    .background(
                        color = Color(0xAA000000), // Fundo semitransparente
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp) // Espaçamento interno no fundo
            ) {
                Text(
                    text = "🎮 Guess The Flag 🎮",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Image(
                painter = painterResource(id = R.drawable.vetor),
                contentDescription = "Center Image",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(1.0f),
                contentScale = ContentScale.Fit
            )

            // Botões
            Column(
                modifier = Modifier.padding(bottom = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botão Start Game
                MenuButton(
                    text = "Start Game",
                    color = Color(0xFF00796B),
                    onClick = { navController.navigate(Navigation.game) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botão Leaderboard
                MenuButton(
                    text = "Leaderboard",
                    color = Color(0xFF1976D2),
                    onClick = { navController.navigate(Navigation.leaderBoard) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botão Rules
                MenuButton(
                    text = "Rules",
                    color = Color(0xFFFF5722),
                    onClick = { isDialogOpen = true }
                )
            }
        }

        // Caixa de diálogo para regras
        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                title = {
                    Text(
                        text = "Game Rules",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                },
                text = {
                    Text(
                        text = "1. Respond as quickly as possible\n" +
                                "2. If you got it right or wrong, the game advances\n" +
                                "3. The scoring works like this:\n" +
                                "- Answered within 5s = 20 points\n" +
                                "- Took more than 5s = 5 points\n" +
                                "- More than 10s = 1 points",
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    TextButton(onClick = { isDialogOpen = false }) {
                        Text("Close", color = Color.Red)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }
}

@Composable
fun MenuButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(60.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    Menu(navController = rememberNavController())
}
