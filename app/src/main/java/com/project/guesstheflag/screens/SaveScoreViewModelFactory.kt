package com.project.guesstheflag.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.guesstheflag.room.DataBase
import com.project.guesstheflag.screens.SaveScoreViewModel

class SaveScoreViewModelFactory(private val db: DataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaveScoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SaveScoreViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
