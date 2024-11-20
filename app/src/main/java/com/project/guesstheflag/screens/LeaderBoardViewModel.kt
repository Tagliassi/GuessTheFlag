package com.project.guesstheflag.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.guesstheflag.room.LeaderBoardDTO
import com.project.guesstheflag.room.LeaderBoardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class LeaderBoardViewModel(private val dao: LeaderBoardDTO) : ViewModel() {
    private val _leaderboard = MutableStateFlow<List<LeaderBoardModel>>(emptyList())
    open val leaderboard: StateFlow<List<LeaderBoardModel>> get() = _leaderboard

    init {
        loadScores()
    }

    private fun loadScores() {
        viewModelScope.launch {
            _leaderboard.value = dao.getScores()
        }
    }
}
