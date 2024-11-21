package com.project.guesstheflag.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.guesstheflag.room.LeaderBoardModel

@Dao
interface LeaderBoardDTO {
    @Query("SELECT * FROM leaderboard ORDER BY points DESC")
    fun getScores(): LiveData<List<LeaderBoardModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(leaderBoardModel: LeaderBoardModel)
}
