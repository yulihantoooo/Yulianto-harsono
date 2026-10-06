package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppTopBar
import com.example.ui.components.CoconutTreeSimulationCanvas
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathNavy
import com.example.ui.theme.MathTealPrimary
import com.example.viewmodel.MainViewModel
import java.util.Locale
import kotlin.math.abs
import kotlin.math.tan

@Composable
fun SimulasiScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onGoToQuiz: () -> Unit
) {
    val distance by viewModel.distanceToTree.collectAsState()
    val observerHeight by viewModel.observerHeight.collectAsState()
    val elevationAngle by viewModel.elevationAngle.collectAsState()

    val calculatedHeight = viewModel.calculateTreeHeight()
    val tanValue = tan(Math.toRadians(elevationAngle.toDouble()))
    val triangleOpposite = distance.toDouble() * tanValue

    // Preset challenges
    var challengeTarget by remember { mutableStateOf<Float?>(null) }
    var challengeFeedback by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Simulasi Menghitung Pohon Kelapa",
                subtitle = "Eksplorasi Klinometer • Yulianto Harsono",
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
            // Interactive 2D Simulation Canvas
            item {
                CoconutTreeSimulationCanvas(
                    distanceMeters = distance,
                    observerHeightMeters = observerHeight,
                    elevationAngleDegrees = elevationAngle,
                    calculatedTreeHeight = calculatedHeight
                )
            }

            // Realtime Result Summary Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF0F2C47)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, MathCyanAccent)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "HASIL PERHITUNGAN TRIGONOMETRI",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MathCyanAccent
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MathAmberHighlight.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "Rumus: T = h + d·tan(θ)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MathAmberHighlight,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "Tinggi Total Pohon Kelapa:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                                Text(
                                    text = String.format(Locale.US, "%.2f meter", calculatedHeight),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    color = Color.White
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = String.format(Locale.US, "tan(%.0f°) = %.4f", elevationAngle, tanValue),
                                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                                    color = MathCyanAccent
                                )
                                Text(
                                    text = String.format(Locale.US, "y = %.2f m", triangleOpposite),
                                    style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                                    color = Color(0xFFFF70A6)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = String.format(
                                Locale.US,
                                "Penjabaran: %.1f m (pengamat) + (%.1f m × %.4f) = %.2f m",
                                observerHeight,
                                distance,
                                tanValue,
                                calculatedHeight
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
                            color = Color(0xFFCBD5E1)
                        )
                    }
                }
            }

            // Interactive Sliders Control Panel
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
                        Text(
                            text = "Panel Kontrol Parameter Simulasi",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Geser slider di bawah ini untuk melihat perubahan geometris secara real-time",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Slider 1: Sudut Elevasi (Theta)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "1. Sudut Elevasi (θ)",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MathAmberHighlight
                            )
                            Text(
                                text = "${elevationAngle.toInt()}°",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = MathAmberHighlight
                            )
                        }
                        Slider(
                            value = elevationAngle,
                            onValueChange = {
                                viewModel.updateElevationAngle(it)
                                challengeFeedback = null
                            },
                            valueRange = 15f..75f,
                            steps = 59,
                            colors = SliderDefaults.colors(
                                thumbColor = MathAmberHighlight,
                                activeTrackColor = MathAmberHighlight,
                                inactiveTrackColor = Color(0xFF334155)
                            ),
                            modifier = Modifier.testTag("slider_elevation_angle")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Slider 2: Jarak Pengamat ke Pangkal Pohon (d)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "2. Jarak ke Pohon (d)",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MathCyanAccent
                            )
                            Text(
                                text = String.format(Locale.US, "%.1f m", distance),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = MathCyanAccent
                            )
                        }
                        Slider(
                            value = distance,
                            onValueChange = {
                                viewModel.updateDistance(it)
                                challengeFeedback = null
                            },
                            valueRange = 5f..35f,
                            steps = 59,
                            colors = SliderDefaults.colors(
                                thumbColor = MathCyanAccent,
                                activeTrackColor = MathCyanAccent,
                                inactiveTrackColor = Color(0xFF334155)
                            ),
                            modifier = Modifier.testTag("slider_distance")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Slider 3: Tinggi Pengamat / Klinometer (h)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "3. Tinggi Pengamat (t)",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MathEmeraldGreen
                            )
                            Text(
                                text = String.format(Locale.US, "%.2f m", observerHeight),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                ),
                                color = MathEmeraldGreen
                            )
                        }
                        Slider(
                            value = observerHeight,
                            onValueChange = {
                                viewModel.updateObserverHeight(it)
                                challengeFeedback = null
                            },
                            valueRange = 1.2f..2.0f,
                            steps = 15,
                            colors = SliderDefaults.colors(
                                thumbColor = MathEmeraldGreen,
                                activeTrackColor = MathEmeraldGreen,
                                inactiveTrackColor = Color(0xFF334155)
                            ),
                            modifier = Modifier.testTag("slider_observer_height")
                        )
                    }
                }
            }

            // Quick Preset Buttons
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF132238)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1F3350))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Preset Sudut Istimewa Lapangan:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF94A3B8)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    viewModel.updateElevationAngle(30f)
                                    viewModel.updateDistance(15f)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("preset_30_deg")
                            ) {
                                Text("30° (1/√3)")
                            }
                            OutlinedButton(
                                onClick = {
                                    viewModel.updateElevationAngle(45f)
                                    viewModel.updateDistance(15f)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("preset_45_deg")
                            ) {
                                Text("45° (tan=1)")
                            }
                            OutlinedButton(
                                onClick = {
                                    viewModel.updateElevationAngle(60f)
                                    viewModel.updateDistance(10f)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("preset_60_deg")
                            ) {
                                Text("60° (√3)")
                            }
                        }
                    }
                }
            }

            // Mini Game Challenge: Target Tree Height
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C1905)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MathAmberHighlight.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = MathAmberHighlight,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Misi Tantangan Interaktif",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MathAmberHighlight
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        val target = challengeTarget ?: 16.6f
                        Text(
                            text = "Tantangan: Sesuaikan slider hingga Tinggi Pohon Kelapa mencapai sekitar ${String.format(Locale.US, "%.1f", target)} meter (toleransi ±0,3 m)!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFFEF08A)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val diff = abs(calculatedHeight - target)
                                    if (diff <= 0.35) {
                                        challengeFeedback = "Hebat! Kamu berhasil menyetel parameter dengan akurat (Selisih hanya ${String.format(Locale.US, "%.2f", diff)} m)!"
                                    } else {
                                        challengeFeedback = "Belum tepat, tinggi saat ini ${String.format(Locale.US, "%.2f", calculatedHeight)} m (Target: ${String.format(Locale.US, "%.1f", target)} m). Coba ubah jarak atau sudut!"
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MathAmberHighlight, contentColor = Color.Black),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_verify_challenge")
                            ) {
                                Text("Periksa Jawaban", fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = {
                                    val newTargets = listOf(16.6f, 11.6f, 18.9f, 21.6f, 25.0f)
                                    challengeTarget = newTargets.random()
                                    challengeFeedback = null
                                },
                                modifier = Modifier.testTag("btn_random_challenge")
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Acak Tantangan")
                            }
                        }

                        if (challengeFeedback != null) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (challengeFeedback!!.startsWith("Hebat")) Color(0xFF064E3B) else Color(0xFF451A03),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = challengeFeedback!!,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color.White,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Next Action CTA (To Quiz)
            item {
                Button(
                    onClick = onGoToQuiz,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MathCyanAccent,
                        contentColor = MathNavy
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_simulasi_to_quiz")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Lanjut Kerjakan 10 Soal HOTS",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
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
