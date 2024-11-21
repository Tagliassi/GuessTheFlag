package com.project.guesstheflag.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LeaderBoardModel::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun LeaderBoardDTO(): LeaderBoardDTO
}
