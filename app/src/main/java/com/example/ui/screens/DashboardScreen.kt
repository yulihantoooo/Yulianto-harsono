package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import com.example.R
import com.example.model.UserSession
import com.example.ui.components.StudentHeaderBadge
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathNavy
import com.example.ui.theme.MathTealPrimary
import com.example.viewmodel.ScreenState

@Composable
fun DashboardScreen(
    session: UserSession,
    onNavigate: (ScreenState) -> Unit,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MathNavy)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Top
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MULTIMEDIA INTERAKTIF",
                            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.5.sp),
                            color = MathCyanAccent
                        )
                        Text(
                            text = "Trigonometri Kelas X",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }

                    Row {
                        IconButton(
                            onClick = { onNavigate(ScreenState.TENTANG_PENGEMBANG) },
                            modifier = Modifier.testTag("btn_about_developer")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Tentang Pengembang",
                                tint = MathCyanAccent
                            )
                        }
                        IconButton(
                            onClick = onLogout,
                            modifier = Modifier.testTag("btn_logout")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Keluar",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    }
                }
            }

            // Student Status Badge
            item {
                StudentHeaderBadge(session = session)
            }

            // Teacher Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF132A45)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A5F))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_yulianto_harsono),
                            contentDescription = "Foto Guru Yulianto Harsono",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(2.dp, MathAmberHighlight, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Guru Pengampu:",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8)
                            )
                            Text(
                                text = "YULIANTO HARSONO",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MathAmberHighlight
                            )
                            Text(
                                text = "Mata Pelajaran Matematika Wajib SMA KELAS X",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }

            // Main Menu Title
            item {
                Text(
                    text = "MENU UTAMA PEMBELAJARAN",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = MathCyanAccent
                )
            }

            // 1. Menu MATERI
            item {
                MenuFeatureCard(
                    title = "1. MATERI TRIGONOMETRI",
                    description = "Konsep dasar sin, cos, tan, sudut istimewa, sudut berelasi 4 kuadran, dan identitas trigonometri.",
                    badge = "Teori & Rumus",
                    badgeColor = MathCyanAccent,
                    icon = Icons.Default.MenuBook,
                    gradientColors = listOf(Color(0xFF0F3A57), Color(0xFF0D243B)),
                    onClick = { onNavigate(ScreenState.MATERI) },
                    testTag = "menu_btn_materi"
                )
            }

            // 2. Menu SIMULASI (Menghitung Tinggi Pohon Kelapa)
            item {
                MenuFeatureCard(
                    title = "2. SIMULASI KLINOMETER",
                    description = "Game simulasi interaktif menghitung tinggi pohon kelapa secara nyata menggunakan perbandingan tangen sudut elevasi.",
                    badge = "Simulasi Interaktif",
                    badgeColor = MathEmeraldGreen,
                    icon = Icons.Default.Nature,
                    gradientColors = listOf(Color(0xFF0D4733), Color(0xFF0A2B20)),
                    onClick = { onNavigate(ScreenState.SIMULASI) },
                    testTag = "menu_btn_simulasi"
                )
            }

            // 3. Menu SOAL (Pilihan Ganda HOTS)
            item {
                MenuFeatureCard(
                    title = "3. SOAL HOTS (10 SOAL)",
                    description = "Uji pemahaman dengan 10 butir soal pilihan ganda High Order Thinking Skills (HOTS) berorientasi AKM & Kontekstual.",
                    badge = "Evaluasi Kuis",
                    badgeColor = MathAmberHighlight,
                    icon = Icons.Default.Quiz,
                    gradientColors = listOf(Color(0xFF4C3005), Color(0xFF281902)),
                    onClick = { onNavigate(ScreenState.SOAL_QUIZ) },
                    testTag = "menu_btn_soal"
                )
            }

            // 4. Menu DATA SHEET (Rekaman Nilai & Jawaban)
            item {
                MenuFeatureCard(
                    title = "4. DATA SHEET NILAI",
                    description = "Rekaman hasil belajar, skor kuis, dan data jawaban siswa tersimpan rapi untuk analisis capaian belajar guru & siswa.",
                    badge = "Rekapitulasi Hasil",
                    badgeColor = Color(0xFFA855F7),
                    icon = Icons.Default.TableChart,
                    gradientColors = listOf(Color(0xFF331652), Color(0xFF1B0B2E)),
                    onClick = { onNavigate(ScreenState.DATA_SHEET) },
                    testTag = "menu_btn_data_sheet"
                )
            }

            // Footer Space
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun MenuFeatureCard(
    title: String,
    description: String,
    badge: String,
    badgeColor: Color,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, badgeColor.copy(alpha = 0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradientColors))
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon Container
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(badgeColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = badgeColor,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = badgeColor.copy(alpha = 0.25f)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = badgeColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCBD5E1),
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF94A3B8)
                )
            }
        }
    }
}
