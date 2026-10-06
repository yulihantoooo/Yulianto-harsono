package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_submissions")
data class QuizSubmission(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val studentName: String,
    val studentClass: String,
    val studentAbsentNumber: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswersCount: Int,
    val detailedAnswersJson: String, // JSON mapping questionIndex -> userSelectedOption & isCorrect
    val teacherName: String = "YULIANTO HARSONO",
    val completedAt: Long = System.currentTimeMillis()
)
