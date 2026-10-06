package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuizSubmission
import com.example.model.QuizRepository
import com.example.ui.theme.MathAmberHighlight
import com.example.ui.theme.MathCardBackground
import com.example.ui.theme.MathCoralRed
import com.example.ui.theme.MathCyanAccent
import com.example.ui.theme.MathEmeraldGreen
import com.example.ui.theme.MathTealPrimary
import org.json.JSONArray
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

/**
 * Visual Analytics Dashboard for Trigonometry HOTS Performance
 * Includes:
 * 1. Per-Question Accuracy Bar Chart (Soal 1-10)
 * 2. Donut Distribution Chart (Mastery, Remedi, Needs Improvement)
 * 3. Learning Progress Trendline (History of attempts)
 */
@Composable
fun TrigonometriPerformanceDashboard(
    submissions: List<QuizSubmission>,
    modifier: Modifier = Modifier
) {
    if (submissions.isEmpty()) return

    // Analyze per-question correctness across submissions
    val totalAttempts = submissions.size
    val questionCorrectCounts = IntArray(10) { 0 }
    val questions = QuizRepository.questions

    submissions.forEach { sub ->
        try {
            val jsonArr = JSONArray(sub.detailedAnswersJson)
            for (i in 0 until jsonArr.length()) {
                val obj = jsonArr.getJSONObject(i)
                val isCorrect = obj.optBoolean("isCorrect", false)
                val qId = obj.optInt("questionId", i + 1)
                val index = (qId - 1).coerceIn(0, 9)
                if (isCorrect) {
                    questionCorrectCounts[index]++
                }
            }
        } catch (_: Exception) {
            // Fallback: estimate from score if detailed json is absent
            val ratio = sub.score.toFloat() / 100f
            for (i in 0 until 10) {
                if (Math.random() < ratio) questionCorrectCounts[i]++
            }
        }
    }

    val latest = submissions.first()
    val averageScore = submissions.map { it.score }.average()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF10233D)),
        border = androidx.compose.foundation.BorderStroke(1.dp, MathCyanAccent.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MathCyanAccent.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = null,
                            tint = MathCyanAccent,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "DASHBOARD ANALITIK & PERFORMA",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MathCyanAccent
                        )
                        Text(
                            text = "Evaluasi 10 Soal HOTS",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF0B192C),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A5F))
                ) {
                    Text(
                        text = "Rata-rata: ${String.format(Locale.US, "%.1f", averageScore)}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        ),
                        color = MathAmberHighlight,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Stat Cards Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatPill(
                    label = "Total Sesi",
                    value = "$totalAttempts",
                    color = MathCyanAccent,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "Skor Terakhir",
                    value = "${latest.score}",
                    color = if (latest.score >= 75) MathEmeraldGreen else MathCoralRed,
                    modifier = Modifier.weight(1f)
                )
                StatPill(
                    label = "Akurasi Kelas",
                    value = "${(averageScore).toInt()}%",
                    color = MathAmberHighlight,
                    modifier = Modifier.weight(1f)
                )
            }

            // Chart 1: Bar Chart of Question Accuracy (Soal 1 - 10)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BarChart, contentDescription = null, tint = MathCyanAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Tingkat Keberhasilan Tiap Butir Soal (% Benar)",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFFE2E8F0)
                        )
                    }
                    Text(
                        text = "10 Soal HOTS",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                QuestionAccuracyBarChart(
                    questionCorrectCounts = questionCorrectCounts,
                    totalAttempts = totalAttempts
                )
            }

            // Chart 2: Donut Chart & Progress Trend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Donut Distribution
                Box(
                    modifier = Modifier
                        .weight(1.2f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0C1A2E))
                        .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(12.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PieChart, contentDescription = null, tint = MathAmberHighlight, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Distribusi Nilai",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MathAmberHighlight
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        ScoreDonutChart(submissions = submissions)
                    }
                }

                // Trend Line
                Box(
                    modifier = Modifier
                        .weight(1.4f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF0C1A2E))
                        .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(12.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = MathEmeraldGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Tren Riwayat Skor",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MathEmeraldGreen
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        ScoreTrendLineChart(submissions = submissions)
                    }
                }
            }
        }
    }
}

@Composable
fun StatPill(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFF0B192C),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF94A3B8),
                fontSize = 10.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace
                ),
                color = color
            )
        }
    }
}

/**
 * Bar Chart: Accuracy percentage per Question (Q1 to Q10)
 */
@Composable
fun QuestionAccuracyBarChart(
    questionCorrectCounts: IntArray,
    totalAttempts: Int,
    modifier: Modifier = Modifier
) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(questionCorrectCounts) {
        animProgress.animateTo(1f, animationSpec = tween(700))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0B192C))
            .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            val bottomAxisY = canvasH * 0.82f
            val topMargin = canvasH * 0.14f
            val chartH = bottomAxisY - topMargin

            // Background horizontal grid lines
            val gridPaints = listOf(0.25f, 0.5f, 0.75f, 1.0f)
            gridPaints.forEach { fraction ->
                val gy = bottomAxisY - chartH * fraction
                drawLine(
                    color = Color(0xFF1E3A5F).copy(alpha = 0.5f),
                    start = Offset(0f, gy),
                    end = Offset(canvasW, gy),
                    strokeWidth = 1.dp.toPx()
                )
            }

            val barCount = 10
            val barSlotW = canvasW / barCount
            val barW = barSlotW * 0.65f

            val textPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 9.dp.toPx()
                color = android.graphics.Color.parseColor("#94A3B8")
                textAlign = android.graphics.Paint.Align.CENTER
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }

            for (i in 0 until barCount) {
                val correctCount = questionCorrectCounts[i]
                val ratio = if (totalAttempts > 0) correctCount.toFloat() / totalAttempts else 0f
                val barH = chartH * ratio * animProgress.value
                val bx = i * barSlotW + (barSlotW - barW) / 2
                val by = bottomAxisY - barH

                // Color coding according to question difficulty
                val barColor = when {
                    ratio >= 0.75f -> Color(0xFF10B981) // High mastery
                    ratio >= 0.45f -> Color(0xFF00ADB5) // Moderate
                    else -> Color(0xFFFFB703) // Hard HOTS
                }

                // Draw Bar
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(barColor, barColor.copy(alpha = 0.5f)),
                        startY = by,
                        endY = bottomAxisY
                    ),
                    topLeft = Offset(bx, by),
                    size = Size(barW, barH.coerceAtLeast(2.dp.toPx())),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx())
                )

                // Label No. Soal (1..10)
                drawContext.canvas.nativeCanvas.drawText(
                    "S${i + 1}",
                    bx + barW / 2,
                    bottomAxisY + 14.dp.toPx(),
                    textPaint
                )

                // Percentage text above bar if enough height
                if (ratio > 0.1f) {
                    val percentPaint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        textSize = 8.dp.toPx()
                        color = android.graphics.Color.parseColor("#E2E8F0")
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                    val pct = (ratio * 100).toInt()
                    drawContext.canvas.nativeCanvas.drawText(
                        "$pct%",
                        bx + barW / 2,
                        (by - 3.dp.toPx()).coerceAtLeast(topMargin),
                        percentPaint
                    )
                }
            }
        }
    }
}

/**
 * Donut Chart: Student Mastery Segments (Lulus KKM ≥ 75 vs Remedial < 75)
 */
@Composable
fun ScoreDonutChart(
    submissions: List<QuizSubmission>,
    modifier: Modifier = Modifier
) {
    val passedCount = submissions.count { it.score >= 75 }
    val remCount = submissions.size - passedCount
    val total = submissions.size.toFloat().coerceAtLeast(1f)

    val passedRatio = passedCount / total
    val remRatio = remCount / total

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = (size.height / 2 - 8.dp.toPx()).coerceAtLeast(10f)
            val strokeW = 12.dp.toPx()

            // Remedial Arc (Red)
            val remSweep = remRatio * 360f
            drawArc(
                color = Color(0xFFEF4444),
                startAngle = -90f,
                sweepAngle = remSweep,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )

            // Passed Arc (Green)
            val passSweep = passedRatio * 360f
            if (passSweep > 0) {
                drawArc(
                    color = Color(0xFF10B981),
                    startAngle = -90f + remSweep,
                    sweepAngle = passSweep,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeW, cap = StrokeCap.Round)
                )
            }

            val textPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 11.dp.toPx()
                color = android.graphics.Color.WHITE
                textAlign = android.graphics.Paint.Align.CENTER
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }
            val subPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 8.dp.toPx()
                color = android.graphics.Color.parseColor("#94A3B8")
                textAlign = android.graphics.Paint.Align.CENTER
            }

            drawContext.canvas.nativeCanvas.drawText(
                "${(passedRatio * 100).toInt()}%",
                center.x,
                center.y + 2.dp.toPx(),
                textPaint
            )
            drawContext.canvas.nativeCanvas.drawText(
                "Lulus",
                center.x,
                center.y + 12.dp.toPx(),
                subPaint
            )
        }
    }
}

/**
 * Score Trendline: History of tests (most recent on the right)
 */
@Composable
fun ScoreTrendLineChart(
    submissions: List<QuizSubmission>,
    modifier: Modifier = Modifier
) {
    // Reverse so chronologically left to right (oldest to newest)
    val history = submissions.take(8).reversed()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            if (history.isEmpty()) return@Canvas

            val topY = canvasH * 0.15f
            val bottomY = canvasH * 0.82f
            val spanH = bottomY - topY

            // Target KKM 75 dashed line
            val kkmY = bottomY - spanH * 0.75f
            drawLine(
                color = Color(0xFFFFB703).copy(alpha = 0.5f),
                start = Offset(0f, kkmY),
                end = Offset(canvasW, kkmY),
                strokeWidth = 1.dp.toPx()
            )

            val stepX = if (history.size > 1) canvasW / (history.size - 1) else canvasW / 2
            val path = Path()

            val points = history.mapIndexed { idx, sub ->
                val x = if (history.size > 1) idx * stepX else canvasW / 2
                val y = bottomY - spanH * (sub.score.toFloat() / 100f).coerceIn(0f, 1f)
                Offset(x, y)
            }

            points.forEachIndexed { i, pt ->
                if (i == 0) path.moveTo(pt.x, pt.y) else path.lineTo(pt.x, pt.y)
            }

            // Draw line
            drawPath(
                path = path,
                color = Color(0xFF00ADB5),
                style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw points
            points.forEach { pt ->
                drawCircle(color = Color(0xFF00FFF5), radius = 3.5.dp.toPx(), center = pt)
                drawCircle(color = Color(0xFF0F172A), radius = 1.8.dp.toPx(), center = pt)
            }

            // KKM label
            val textPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 8.dp.toPx()
                color = android.graphics.Color.parseColor("#FFB703")
            }
            drawContext.canvas.nativeCanvas.drawText("KKM 75", 4f, kkmY - 4.dp.toPx(), textPaint)
        }
    }
}
