package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizSubmissionDao {
    @Query("SELECT * FROM quiz_submissions ORDER BY completedAt DESC")
    fun getAllSubmissions(): Flow<List<QuizSubmission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: QuizSubmission): Long

    @Query("DELETE FROM quiz_submissions WHERE id = :id")
    suspend fun deleteSubmissionById(id: Long)

    @Query("DELETE FROM quiz_submissions")
    suspend fun clearAllSubmissions()
}
