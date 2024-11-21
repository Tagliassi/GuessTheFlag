package com.project.guesstheflag.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.project.guesstheflag.Navigation
import com.project.guesstheflag.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitScreen(
    navController: NavController,
    points: Int,
    saveScoreViewModel: SaveScoreViewModel // Recebe o ViewModel como parâmetro
) {
    var playerName by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exit Game", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            // Fundo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Conteúdo principal
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Título com fundo destacado
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xAA000000), // Fundo semitransparente
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp) // Espaçamento interno
                    ) {
                        Text(
                            text = "🎮 Thanks for playing! 🎮",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pontuação
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xAA000000), // Fundo semitransparente
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(10.dp) // Espaçamento interno
                    ) {
                        Text(
                            text = "Your Score: $points",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    OutlinedTextField(
                        value = playerName,
                        onValueChange = { playerName = it },
                        label = {
                            Text("Enter your name")
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.7f) // Mesma largura dos botões
                            .height(60.dp), // Mesma altura dos botões
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center // Centraliza o texto
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White, // Fundo branco
                            focusedBorderColor = Color(0xFF00796B), // Cor da borda ao focar
                            unfocusedBorderColor = Color.Gray, // Cor da borda sem foco
                            cursorColor = Color.Black, // Cor do cursor
                            focusedLabelColor = Color.White, // Cor da label quando focado
                            unfocusedLabelColor = Color.Gray // Cor da label quando não focado
                        ),
                        shape = CircleShape // Borda arredondada
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Botão Salvar e Retornar
                    Button(
                        onClick = {
                            if (playerName.isNotEmpty()) {
                                coroutineScope.launch {
                                    saveScoreViewModel.saveScore(playerName, points)
                                    navController.navigate(Navigation.menu)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(60.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Save and Return",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Botão Sair Sem Salvar
                    Button(
                        onClick = { navController.navigate(Navigation.menu) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(60.dp)
                    ) {
                        Text(
                            text = "Exit Without Saving",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    )
}


