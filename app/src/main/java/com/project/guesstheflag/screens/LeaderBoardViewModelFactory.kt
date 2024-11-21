package com.project.guesstheflag.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.guesstheflag.room.LeaderBoardDTO
import com.project.guesstheflag.screens.LeaderBoardViewModel

class LeaderBoardViewModelFactory(private val dao: LeaderBoardDTO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderBoardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderBoardViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
