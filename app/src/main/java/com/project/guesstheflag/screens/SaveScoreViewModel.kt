package com.project.guesstheflag.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.guesstheflag.room.DataBase
import com.project.guesstheflag.room.LeaderBoardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaveScoreViewModel(private val db: DataBase) : ViewModel() {
    fun saveScore(name: String, points: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.LeaderBoardDTO().insert(LeaderBoardModel(name = name, points = points))
            }
        }
    }
}
