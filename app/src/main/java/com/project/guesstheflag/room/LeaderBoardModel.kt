package com.project.guesstheflag.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leaderboard")
data class LeaderBoardModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val points: Int
)