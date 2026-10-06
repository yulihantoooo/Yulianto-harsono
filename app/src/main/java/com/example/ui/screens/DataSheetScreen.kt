package com.example.ui.screens

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuizSubmission
import com.example.ui.components.AppTopBar
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCoralRed
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathNavy
import com.example.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DataSheetScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val submissions by viewModel.submissions.collectAsState()
    val context = LocalContext.current
    var showClearConfirm by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Data Sheet Nilai Siswa",
                subtitle = "Rekapitulasi Evaluasi • Yulianto Harsono",
                onBackClick = onBack,
                actions = {
                    if (submissions.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                shareSubmissionsAsCsv(context, submissions)
                            },
                            modifier = Modifier.testTag("btn_share_datasheet")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Ekspor / Bagikan Data Sheet",
                                tint = MathCyanAccent
                            )
                        }
                    }
                }
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
            // Data Sheet Header Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF132B45)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1F3F66))
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
                                text = "LEMBAR DATA PENILAIAN (DATA SHEET)",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MathCyanAccent
                            )
                            Surface(
                                shape = CircleShape,
                                color = MathAmberHighlight.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "${submissions.size} Rekaman",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MathAmberHighlight,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Guru Pengampu: YULIANTO HARSONO",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Mata Pelajaran Matematika SMA Kelas X - Materi Trigonometri",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { shareSubmissionsAsCsv(context, submissions) },
                                enabled = submissions.isNotEmpty(),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_export_csv")
                            ) {
                                Icon(Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ekspor CSV")
                            }

                            OutlinedButton(
                                onClick = { showClearConfirm = true },
                                enabled = submissions.isNotEmpty(),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MathCoralRed),
                                modifier = Modifier.testTag("btn_clear_datasheet")
                            ) {
                                Icon(Icons.Default.DeleteSweep, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // Visual Dashboard Charts for 10 HOTS Progress & Performance
            if (submissions.isNotEmpty()) {
                item {
                    com.example.ui.components.TrigonometriPerformanceDashboard(
                        submissions = submissions
                    )
                }
            }

            // Table Header Simulation
            if (submissions.isNotEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF0F223D),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A5F)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "No / Siswa",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.weight(1.5f)
                            )
                            Text(
                                text = "Kelas / Absen",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.weight(1.2f)
                            )
                            Text(
                                text = "Nilai",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.weight(0.8f)
                            )
                            Text(
                                text = "Aksi",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF94A3B8),
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                    }
                }
            }

            // List of submissions
            if (submissions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MathCardBackground),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.TableChart,
                                contentDescription = null,
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Belum Ada Data Rekaman Kuis",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Kerjakan 10 Soal HOTS pada menu kuis, maka hasil pengerjaan siswa akan otomatis terekam secara persisten di data sheet ini.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(submissions) { item ->
                    SubmissionRowCard(
                        submission = item,
                        onDelete = { viewModel.deleteSubmission(item.id) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Hapus Semua Data Sheet?") },
            text = { Text("Seluruh riwayat nilai kuis yang tersimpan akan dihapus permanen dari basis data.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearAllSubmissions()
                        showClearConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MathCoralRed)
                ) {
                    Text("Hapus Semua")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun SubmissionRowCard(
    submission: QuizSubmission,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(submission.completedAt))
    val isPassed = submission.score >= 75

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MathCardBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Student Name & Date
            Column(modifier = Modifier.weight(1.5f)) {
                Text(
                    text = submission.studentName,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    maxLines = 1
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8)
                )
            }

            // Class & Absent No
            Column(modifier = Modifier.weight(1.2f)) {
                Text(
                    text = submission.studentClass,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MathCyanAccent
                )
                Text(
                    text = "No. Absen: ${submission.studentAbsentNumber}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFCBD5E1)
                )
            }

            // Score Badge
            Column(
                modifier = Modifier.weight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isPassed) MathEmeraldGreen.copy(alpha = 0.2f) else MathCoralRed.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isPassed) MathEmeraldGreen else MathCoralRed
                    )
                ) {
                    Text(
                        text = "${submission.score}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = if (isPassed) MathEmeraldGreen else MathCoralRed,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "${submission.correctAnswersCount}/10 Benar",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8)
                )
            }

            // Delete action
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(32.dp)
                    .testTag("btn_delete_sub_${submission.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus Rekaman",
                    tint = Color(0xFFEF4444).copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

private fun shareSubmissionsAsCsv(context: Context, submissions: List<QuizSubmission>) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val csvBuilder = StringBuilder()
    csvBuilder.append("ID,Nama Siswa,Kelas,No Absen,Skor,Benar,Total Soal,Guru Pengampu,Waktu Selesai\n")

    submissions.forEach { s ->
        val dateStr = dateFormat.format(Date(s.completedAt))
        csvBuilder.append("${s.id},\"${s.studentName}\",\"${s.studentClass}\",\"${s.studentAbsentNumber}\",${s.score},${s.correctAnswersCount},${s.totalQuestions},\"${s.teacherName}\",\"$dateStr\"\n")
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Data Sheet Nilai Trigonometri - Yulianto Harsono")
        putExtra(Intent.EXTRA_TEXT, csvBuilder.toString())
    }
    context.startActivity(Intent.createChooser(intent, "Ekspor Data Sheet Nilai"))
}
