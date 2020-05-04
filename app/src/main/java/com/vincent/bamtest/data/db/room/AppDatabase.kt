package com.vincent.bamtest.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Repository::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}