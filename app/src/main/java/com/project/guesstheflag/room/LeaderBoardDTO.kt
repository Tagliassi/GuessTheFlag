package com.project.guesstheflag.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LeaderBoardDTO {

    @Insert
    suspend fun insert(leaderboard: LeaderBoardModel)

    @Query("SELECT * FROM leaderboard ORDER BY points DESC")
    suspend fun getScores(): List<LeaderBoardModel>
}