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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import com.example.R
import com.example.ui.components.AppTopBar
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathNavy
import com.example.ui.theme.MathTealPrimary

@Composable
fun TentangPengembangScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Tentang Media & Pengembang",
                subtitle = "Multimedia Interaktif Trigonometri",
                onBackClick = onBack
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
            // Profile Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF132B45)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MathAmberHighlight)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_yulianto_harsono),
                            contentDescription = "Foto Guru Pengampu Yulianto Harsono",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(3.dp, MathAmberHighlight, CircleShape)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "YULIANTO HARSONO",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            ),
                            color = MathAmberHighlight,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Guru Pengampu / Pengembang Multimedia Pembelajaran",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFE2E8F0),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            shape = CircleShape,
                            color = MathCyanAccent.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Matematika SMA Kelas X (Fase E)",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MathCyanAccent,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Media Description Card
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
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoStories,
                                contentDescription = null,
                                tint = MathCyanAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Tujuan & Sasaran Pembelajaran",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Multimedia Interaktif Pembelajaran Trigonometri dirancang untuk membantu peserta didik SMA Kelas X memahami konsep abstrak trigonometri melalui visualisasi interaktif dan pemecahan masalah kontekstual.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFCBD5E1),
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val points = listOf(
                            "Memahami rasio trigonometri (sin, cos, tan) pada segitiga siku-siku.",
                            "Menentukan tinggi objek nyata (pohon kelapa) dengan klinometer digital.",
                            "Menyelesaikan 10 soal HOTS berorientasi asesmen kompetensi minimum.",
                            "Merekam dan memantau capaian belajar secara mandiri di data sheet."
                        )

                        points.forEach { point ->
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MathEmeraldGreen,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = point,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFE2E8F0)
                                )
                            }
                        }
                    }
                }
            }

            // Technical Specs Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1E33)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A5F))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Text(
                            text = "Fitur Sistem Pembelajaran",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MathCyanAccent
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Login Siswa: Nama, Kelas, dan Nomer Absen\n" +
                                    "• Materi Lengkap: 5 Bab Trigonometri SMA Kelas X\n" +
                                    "• Simulasi Klinometer: Menghitung Tinggi Pohon Kelapa\n" +
                                    "• 10 Soal Pilihan Ganda HOTS dengan Pembahasan Detail\n" +
                                    "• Lembar Data Sheet: Penyimpanan Data Lokal Room DB & Ekspor CSV",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
