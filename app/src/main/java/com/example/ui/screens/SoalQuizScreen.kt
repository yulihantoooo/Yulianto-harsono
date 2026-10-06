package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Question
import com.example.ui.components.AppTopBar
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathNavy
import com.example.ui.theme.MathTealPrimary
import com.example.viewmodel.MainViewModel

@Composable
fun SoalQuizScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswers by viewModel.selectedAnswers.collectAsState()
    val session by viewModel.userSession.collectAsState()
    val questions = viewModel.questions

    val currentQuestion = questions[currentIndex]
    val currentSelectedOption = selectedAnswers[currentIndex]

    var showSubmitConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Soal HOTS Trigonometri",
                subtitle = "Nomor ${currentIndex + 1} dari ${questions.size} • Yulianto Harsono",
                onBackClick = onBack
            )
        },
        containerColor = MathNavy
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Horizontal Question Number Navigator
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F223D))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(questions) { idx, _ ->
                    val isAnswered = selectedAnswers.containsKey(idx)
                    val isCurrent = idx == currentIndex

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isCurrent -> MathCyanAccent
                                    isAnswered -> MathTealPrimary.copy(alpha = 0.4f)
                                    else -> Color(0xFF1E293B)
                                }
                            )
                            .border(
                                width = if (isCurrent) 2.dp else 1.dp,
                                color = if (isCurrent) Color.White else if (isAnswered) MathCyanAccent else Color(0xFF334155),
                                shape = CircleShape
                            )
                            .clickable { viewModel.jumpToQuestion(idx) }
                            .testTag("nav_question_chip_$idx"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${idx + 1}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = when {
                                isCurrent -> MathNavy
                                isAnswered -> Color.White
                                else -> Color(0xFF94A3B8)
                            }
                        )
                    }
                }
            }

            // Question Body and Options
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // HOTS Category Badge & Question Header
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF132B45)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1D426B))
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
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MathAmberHighlight.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "HOTS: ${currentQuestion.hotsCategory}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MathAmberHighlight,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Text(
                                    text = "Bobot: 10 Poin",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF94A3B8)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = currentQuestion.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Context Story
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF0C1A2E),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF193254)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = currentQuestion.contextStory,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFE2E8F0),
                                    lineHeight = 22.sp,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }

                            // Gambar Ilustrasi Matematis untuk Soal Nomor 1-5
                            if (currentQuestion.id in 1..5) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Gambar Diagram & Sketsa Geometris:",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MathAmberHighlight
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                com.example.ui.components.QuestionIllustration(
                                    questionId = currentQuestion.id,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Question Core Sentence
                            Text(
                                text = currentQuestion.questionText,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MathCyanAccent
                            )
                        }
                    }
                }

                // Multiple Choice Options (A, B, C, D, E)
                item {
                    Text(
                        text = "Pilih salah satu jawaban yang paling tepat:",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                }

                itemsIndexed(currentQuestion.options) { optIdx, optionText ->
                    val isSelected = currentSelectedOption == optIdx

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectAnswer(currentIndex, optIdx) }
                            .testTag("option_${currentIndex}_$optIdx"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFF0F3A57) else MathCardBackground
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) MathCyanAccent else Color(0xFF334155)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) MathCyanAccent else Color(0xFF0F172A))
                                    .border(1.dp, if (isSelected) MathCyanAccent else Color(0xFF475569), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                val letter = ('A' + optIdx).toString()
                                Text(
                                    text = letter,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isSelected) MathNavy else Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                ),
                                color = if (isSelected) Color.White else Color(0xFFCBD5E1),
                                modifier = Modifier.weight(1f)
                            )

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MathCyanAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            // Bottom Action Navigation Bar (Prev, Next, Submit)
            Surface(
                color = Color(0xFF0F223D),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Prev Button
                    OutlinedButton(
                        onClick = { viewModel.prevQuestion() },
                        enabled = currentIndex > 0,
                        modifier = Modifier.testTag("btn_prev_question")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sebelumnya")
                    }

                    // Answered count indicator
                    Text(
                        text = "${selectedAnswers.size}/${questions.size} Terjawab",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = if (selectedAnswers.size == questions.size) MathEmeraldGreen else Color(0xFF94A3B8)
                    )

                    // Next or Finish Button
                    if (currentIndex < questions.size - 1) {
                        Button(
                            onClick = { viewModel.nextQuestion() },
                            colors = ButtonDefaults.buttonColors(containerColor = MathCyanAccent, contentColor = MathNavy),
                            modifier = Modifier.testTag("btn_next_question")
                        ) {
                            Text("Selanjutnya")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = { showSubmitConfirmation = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MathAmberHighlight, contentColor = Color.Black),
                            modifier = Modifier.testTag("btn_finish_quiz")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Selesai & Rekam", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitConfirmation) {
        AlertDialog(
            onDismissRequest = { showSubmitConfirmation = false },
            title = {
                Text("Kirim & Rekam Jawaban?")
            },
            text = {
                val answeredCount = selectedAnswers.size
                val totalCount = questions.size
                val unansweredCount = totalCount - answeredCount
                Column {
                    Text("Siswa: ${session.studentName} (${session.studentClass})")
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Total terjawab: $answeredCount dari $totalCount soal.")
                    if (unansweredCount > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Peringatan: Masih ada $unansweredCount soal belum dijawab!",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Hasil nilai dan rincian jawaban akan terekam ke Data Sheet evaluasi Bapak Yulianto Harsono.")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirmation = false
                        viewModel.submitQuiz()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MathCyanAccent, contentColor = MathNavy),
                    modifier = Modifier.testTag("btn_confirm_submit_quiz")
                ) {
                    Text("Ya, Rekam Sekarang")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitConfirmation = false }) {
                    Text("Periksa Kembali")
                }
            }
        )
    }
}
