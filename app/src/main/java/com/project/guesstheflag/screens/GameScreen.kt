@file:OptIn(ExperimentalAnimationApi::class)

package com.project.guesstheflag.screens

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.guesstheflag.R
import kotlinx.coroutines.delay
import java.io.InputStreamReader

// Data class for questions
data class Questions(
    val questionText: String,
    val imageId: String,
    val answersOptions: List<String>,
    val rightAnswer: String
)

// Function to load questions from JSON file
fun loadQuestions(context: Context): List<Questions> {
    val inputStream = context.resources.openRawResource(R.raw.questions)
    val reader = InputStreamReader(inputStream)
    val questionType = object : TypeToken<List<Questions>>() {}.type
    return Gson().fromJson(reader, questionType)
}

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel = viewModel()) {
    val context = LocalContext.current
    val perguntas = remember { loadQuestions(context) }
    val shuffledQuestions = remember { perguntas.shuffled() }
    val question = shuffledQuestions[viewModel.actualQuestion.value]

    // Shuffle answer options once
    val shuffledAnswers = remember(question) { question.answersOptions.shuffled() }

    val elapsedTime by viewModel.elapsedTime
    val scoring by viewModel.scoring
    val rightSound = remember { MediaPlayer.create(context, R.raw.right) }
    val wrongSound = remember { MediaPlayer.create(context, R.raw.wrong) }

    // Start timer effect on first question
    LaunchedEffect(viewModel.actualQuestion.value) {
        val startTime = System.currentTimeMillis()
        while (true) {
            viewModel.updateElapsedTime(startTime) // Update time every 500ms
            delay(500)
        }
    }

    // Main UI Layout
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Timer display in top right corner
        TimerDisplay(elapsedTime)

        // Display question and answer options
        QuestionDisplay(
            question = question,
            shuffledAnswers = shuffledAnswers,
            onAnswerSelected = { selectedAnswer ->
                val isCorrect = selectedAnswer == question.rightAnswer
                if (isCorrect) {
                    viewModel.updateScore(calculateScore(elapsedTime, true))
                    rightSound.start()
                } else {
                    wrongSound.start()
                }
                if (viewModel.actualQuestion.value < shuffledQuestions.size - 1) {
                    viewModel.nextQuestion()
                } else {
                    navController.navigate("exit/$scoring")
                }
            }
        )

        // Display score at the bottom of the screen with space below
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            ScoreDisplay(scoring)
            Spacer(modifier = Modifier.height(60.dp)) // Space below the score
        }
    }
}

// Timer display component
@Composable
fun TimerDisplay(elapsedTime: Long) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .padding(15.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = "Time: ${elapsedTime / 1000}s",
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .background(
                    color = Color.Black.copy(alpha = 0.7f),
                    shape = CircleShape
                )
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

// Question and answer options display component
@Composable
fun QuestionDisplay(question: Questions, shuffledAnswers: List<String>, onAnswerSelected: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val imageResource = context.resources.getIdentifier(question.imageId, "drawable", context.packageName)

        // Display question image
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.size(300.dp).padding(20.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Display question text
        Text(
            text = question.questionText,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .background(Color(0xAA000000), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display shuffled answer options as buttons
        shuffledAnswers.forEach { option ->
            GradientButton(
                text = option,
                isCorrect = option == question.rightAnswer,
                onClick = { onAnswerSelected(option) }
            )
        }
    }
}

// Gradient button component with animation
@Composable
fun GradientButton(text: String, isCorrect: Boolean, onClick: () -> Unit) {
    var buttonColor by remember { mutableStateOf(0) } // 0: Default, 1: Correct, 2: Incorrect
    val gradient = listOf(Color(0xFF6200EE), Color(0xFF3700B3)) // Default gradient
    val correctColor = Color.Green // Correct answer color
    val wrongColor = Color.Red // Incorrect answer color

    // Determine gradient based on answer
    val animatedGradient = when (buttonColor) {
        1 -> listOf(Color(0xFF006400), correctColor) // Green for correct
        2 -> listOf(Color(0xFF8B0000), wrongColor) // Red for wrong
        else -> gradient // Default gradient
    }

    // Animation for gradient color change
    val animatedColor by animateColorAsState(
        targetValue = if (buttonColor == 0) Color.Transparent else animatedGradient.first(),
        animationSpec = tween(durationMillis = 300) // Quick transition (300ms)
    )

    Box(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp)
            .padding(vertical = 8.dp)
            .background(Brush.linearGradient(animatedGradient), shape = MaterialTheme.shapes.medium)
            .clickable {
                onClick() // Call onClick when button is clicked
                buttonColor = if (isCorrect) 1 else 2 // Set button color based on correctness
            },
        contentAlignment = Alignment.Center
    ) {
        // Button text
        Text(
            text = text,
            style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )
    }

    // Reset to default gradient after a short delay
    LaunchedEffect(buttonColor) {
        if (buttonColor != 0) {
            delay(300) // Wait 300ms
            buttonColor = 0 // Reset gradient
        }
    }
}

// Score display component with animation
@Composable
fun ScoreDisplay(scoring: Int) {
    AnimatedContent(targetState = scoring) { animatedScore ->
        Text(
            text = "Score: $animatedScore",
            color = Color.White,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .background(Color(0xAA000000), shape = RoundedCornerShape(16.dp))
                .padding(10.dp)
        )
    }
}

// Function to calculate score based on response time and correctness
fun calculateScore(responseTime: Long, correctAnswer: Boolean): Int {
    if (!correctAnswer) return 0
    return when {
        responseTime < 5000 -> 20 // High score for quick responses
        responseTime < 10000 -> 5  // Moderate score
        else -> 1 // Small score for slow responses
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGame() {
    GameScreen(navController = rememberNavController())
}
