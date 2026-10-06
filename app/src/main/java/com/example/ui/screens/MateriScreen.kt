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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MaterialSection
import com.example.ui.components.AppTopBar
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathNavy
import com.example.ui.theme.MathTealPrimary
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@Composable
fun MateriScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onGoToSimulasi: () -> Unit
) {
    val selectedIndex by viewModel.selectedMaterialIndex.collectAsState()
    val materials = viewModel.materials
    val currentMaterial = materials[selectedIndex]

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Materi Trigonometri",
                subtitle = "SMA KELAS X • Yulianto Harsono",
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
            // Horizontal Tab Row for quick navigation
            ScrollableTabRow(
                selectedTabIndex = selectedIndex,
                containerColor = Color(0xFF0F223D),
                contentColor = MathCyanAccent,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                        color = MathCyanAccent,
                        height = 3.dp
                    )
                }
            ) {
                materials.forEachIndexed { index, material ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { viewModel.selectMaterial(index) },
                        text = {
                            Text(
                                text = "Bab ${index + 1}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (selectedIndex == index) MathCyanAccent else Color(0xFF94A3B8)
                            )
                        },
                        modifier = Modifier.testTag("tab_materi_$index")
                    )
                }
            }

            // Material Detail Content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Card
                item {
                    MaterialHeaderCard(material = currentMaterial)
                }

                // Theory / Concept Description
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MathCardBackground
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = MathCyanAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Uraian Konsep & Teori",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = currentMaterial.content,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFE2E8F0),
                                lineHeight = 24.sp
                            )
                        }
                    }
                }

                // Formulas Card
                if (currentMaterial.formulas.isNotEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF0C243B)
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MathTealPrimary.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Functions,
                                        contentDescription = null,
                                        tint = MathCyanAccent,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Rumus & Identitas Pokok",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MathCyanAccent
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                currentMaterial.formulas.forEach { (label, formula) ->
                                    FormulaRowItem(label = label, formula = formula)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }

                // Contextual Examples
                if (currentMaterial.examples.isNotEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF26200A)
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MathAmberHighlight.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = MathAmberHighlight,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Contoh Pembahasan Terbimbing",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MathAmberHighlight
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                currentMaterial.examples.forEach { (soal, jawaban) ->
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFF191404),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B2E0A)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                text = soal,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                                color = Color.White
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = jawaban,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFFFDE68A),
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Interactive CTA banner to simulation
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGoToSimulasi() }
                            .testTag("btn_materi_cta_simulasi"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MathCyanAccent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF0E4334), Color(0xFF092920))
                                    )
                                )
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Uji Coba Langsung di Simulasi!",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Praktikkan rumus tangen pada pohon kelapa interaktif.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFA7F3D0)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = MathCyanAccent
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun MaterialHeaderCard(material: MaterialSection) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF132B45)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1D426B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MathCyanAccent.copy(alpha = 0.2f)
            ) {
                Text(
                    text = material.badge,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MathCyanAccent,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = material.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Text(
                text = material.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF94A3B8)
            )
        }
    }
}

@Composable
fun FormulaRowItem(label: String, formula: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF08192A),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1A3552)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                ),
                color = MathAmberHighlight
            )
            Text(
                text = formula,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = Color.White
            )
        }
    }
}
