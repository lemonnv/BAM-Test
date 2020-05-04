package com.vincent.bamtest.data.db.room

import androidx.room.*

@Entity(tableName = "repository")
data class Repository(
    @PrimaryKey @ColumnInfo(name = "id") var id: Long
)

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repository")
    fun getRepositories(): List<Repository>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addRepository(repository: Repository)

    @Delete
    fun deleteRepository(repository: Repository)

}