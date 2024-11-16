package com.project.guesstheflag.screens

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

// ViewModel to manage the game state (score, elapsed time, and current question)
class GameViewModel : ViewModel() {

    // State variable to store the player's score
    private val _scoring = mutableStateOf(0)
    val scoring: State<Int> get() = _scoring // Exposes the score

    // State variable to store the index of the current question
    private val _actualQuestion = mutableStateOf(0)
    val actualQuestion: State<Int> get() = _actualQuestion // Exposes the current question

    // State variable to store the elapsed time since the start of the question
    private val _elapsedTime = mutableStateOf(0L)
    val elapsedTime: State<Long> get() = _elapsedTime // Exposes the elapsed time

    // Function to update the score based on the points
    fun updateScore(points: Int) {
        _scoring.value += points // Adds the points to the current score
    }

    // Function to move to the next question
    fun nextQuestion() {
        _actualQuestion.value += 1 // Increments the current question index
    }

    // Function to update the elapsed time based on the start time
    fun updateElapsedTime(startTime: Long) {
        _elapsedTime.value = System.currentTimeMillis() - startTime // Calculates the elapsed time
    }
}