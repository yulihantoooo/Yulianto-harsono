package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCoralRed
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathNavy
import com.example.viewmodel.MainViewModel

@Composable
fun QuizResultScreen(
    viewModel: MainViewModel,
    onBackToHome: () -> Unit,
    onGoToDataSheet: () -> Unit,
    onRetryQuiz: () -> Unit
) {
    val score by viewModel.latestScore.collectAsState()
    val correctCount by viewModel.latestCorrectCount.collectAsState()
    val totalCount = viewModel.questions.size
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val session by viewModel.userSession.collectAsState()
    val questions = viewModel.questions

    val isPassed = score >= 75
    val submissions by viewModel.submissions.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Hasil Evaluasi Kuis",
                subtitle = "Terekam di Data Sheet • Yulianto Harsono",
                onBackClick = onBackToHome
            )
        },
        containerColor = MathNavy
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Big Score Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPassed) Color(0xFF0F3A2B) else Color(0xFF3B1515)
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (isPassed) MathEmeraldGreen else MathCoralRed
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = (if (isPassed) MathEmeraldGreen else MathCoralRed).copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (isPassed) "LULUS KOMPETENSI (KKM ≥ 75)" else "PERLU REMEDIAL & PENGAYAAN",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (isPassed) MathEmeraldGreen else MathCoralRed,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "$score",
                            style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = Color.White
                        )

                        Text(
                            text = "Skor Akhir dari 100",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFCBD5E1)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Benar: $correctCount soal",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MathEmeraldGreen
                            )
                            Text(
                                text = "•",
                                color = Color(0xFF64748B)
                            )
                            Text(
                                text = "Salah: ${totalCount - correctCount} soal",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MathCoralRed
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Tercatat untuk siswa: ${session.studentName} (${session.studentClass} / Absen ${session.studentAbsentNumber})",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            // Quick Actions (Data Sheet & Retry)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onGoToDataSheet,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MathCyanAccent,
                            contentColor = MathNavy
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_result_to_datasheet")
                    ) {
                        Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Lihat Data Sheet", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onRetryQuiz,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_result_retry")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ulangi Kuis")
                    }
                }
            }

            // Visual Progress & Performance Dashboard Across 10 HOTs Questions
            if (submissions.isNotEmpty()) {
                item {
                    com.example.ui.components.TrigonometriPerformanceDashboard(
                        submissions = submissions
                    )
                }
            }

            // Section Title: Detailed Review
            item {
                Text(
                    text = "PEMBAHASAN LENGKAP 10 SOAL",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MathCyanAccent
                )
            }

            // 10 Detailed Questions Review
            itemsIndexed(questions) { index, question ->
                val chosenIndex = selectedAnswers[index]
                val isCorrect = chosenIndex == question.correctIndex

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MathCardBackground),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isCorrect) MathEmeraldGreen.copy(alpha = 0.5f) else MathCoralRed.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Soal ${index + 1}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )

                            Surface(
                                shape = CircleShape,
                                color = if (isCorrect) MathEmeraldGreen.copy(alpha = 0.2f) else MathCoralRed.copy(alpha = 0.2f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                        contentDescription = null,
                                        tint = if (isCorrect) MathEmeraldGreen else MathCoralRed,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isCorrect) "BENAR (+10)" else "SALAH (0)",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isCorrect) MathEmeraldGreen else MathCoralRed
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = question.questionText,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = Color(0xFFE2E8F0)
                        )

                        // Gambar Ilustrasi Matematis untuk Soal Nomor 1-5 di Pembahasan
                        if (question.id in 1..5) {
                            Spacer(modifier = Modifier.height(10.dp))
                            com.example.ui.components.QuestionIllustration(
                                questionId = question.id,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Student answer
                        val chosenText = if (chosenIndex != null && chosenIndex in question.options.indices) {
                            question.options[chosenIndex]
                        } else {
                            "Tidak dijawab"
                        }
                        Text(
                            text = "Jawaban Kamu: $chosenText",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = if (isCorrect) MathEmeraldGreen else MathCoralRed
                        )

                        // Correct answer
                        if (!isCorrect) {
                            Text(
                                text = "Kunci Jawaban Benar: ${question.options[question.correctIndex]}",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MathCyanAccent
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Explanation
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF0F1A2A),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Pembahasan Konsep:",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MathAmberHighlight
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = question.explanation,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFCBD5E1),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            // Back Home Button
            item {
                Button(
                    onClick = onBackToHome,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF334155),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_result_back_home")
                ) {
                    Icon(Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Kembali ke Menu Utama")
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
