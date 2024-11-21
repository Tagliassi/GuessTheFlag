package com.project.guesstheflag.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.guesstheflag.room.LeaderBoardDTO
import com.project.guesstheflag.room.LeaderBoardModel

open class LeaderBoardViewModel(private val dao: LeaderBoardDTO) : ViewModel() {
    private val _leaderboard = MutableLiveData<List<LeaderBoardModel>>()
    open val leaderboard: LiveData<List<LeaderBoardModel>> get() = _leaderboard

    init {
        loadScores()
    }

    private fun loadScores() {
        // Observa o LiveData do DAO e atualiza _leaderboard quando o valor mudar
        dao.getScores().observeForever { scores ->
            _leaderboard.value = scores
        }
    }
}
