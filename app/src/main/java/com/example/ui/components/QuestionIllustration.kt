package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Diagram ilustrasi matematis untuk Soal HOTS 1 s/d 5.
 */
@Composable
fun QuestionIllustration(
    questionId: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B192C),
                        Color(0xFF0F2B48),
                        Color(0xFF081424)
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            val textPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 11.dp.toPx()
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }

            when (questionId) {
                1 -> drawIllustrationQuestion1(canvasW, canvasH, textPaint)
                2 -> drawIllustrationQuestion2(canvasW, canvasH, textPaint)
                3 -> drawIllustrationQuestion3(canvasW, canvasH, textPaint)
                4 -> drawIllustrationQuestion4(canvasW, canvasH, textPaint)
                5 -> drawIllustrationQuestion5(canvasW, canvasH, textPaint)
            }
        }
    }
}

// Soal 1: Pengamatan Pohon Kelapa (Klinometer 1.6m, Jarak 15m, Sudut 45°)
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawIllustrationQuestion1(
    canvasW: Float,
    canvasH: Float,
    textPaint: android.graphics.Paint
) {
    val groundY = canvasH * 0.85f
    val obsX = canvasW * 0.20f
    val treeX = canvasW * 0.80f
    val treeTopY = canvasH * 0.18f
    val obsEyeY = groundY - 24.dp.toPx()

    // Tanah
    drawLine(
        color = Color(0xFF10B981),
        start = Offset(10f, groundY),
        end = Offset(canvasW - 10f, groundY),
        strokeWidth = 3.dp.toPx()
    )

    // Batang Pohon Kelapa
    val trunkPath = Path().apply {
        moveTo(treeX - 8.dp.toPx(), groundY)
        quadraticBezierTo(treeX + 6.dp.toPx(), (groundY + treeTopY) / 2, treeX - 5.dp.toPx(), treeTopY)
        lineTo(treeX + 5.dp.toPx(), treeTopY)
        quadraticBezierTo(treeX + 12.dp.toPx(), (groundY + treeTopY) / 2, treeX + 8.dp.toPx(), groundY)
        close()
    }
    drawPath(trunkPath, color = Color(0xFF854D0E))

    // Daun kelapa
    val leafAngles = listOf(-70f, -40f, -10f, 20f, 50f, 80f, -110f, -140f)
    leafAngles.forEach { deg ->
        val rad = Math.toRadians(deg.toDouble())
        val len = 22.dp.toPx()
        val endX = treeX + (cos(rad) * len).toFloat()
        val endY = treeTopY + (sin(rad) * len * 0.5f).toFloat()
        drawLine(
            color = Color(0xFF22C55E),
            start = Offset(treeX, treeTopY),
            end = Offset(endX, endY),
            strokeWidth = 2.5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }

    // Pengamat & Klinometer
    drawCircle(
        color = Color(0xFFFFD166),
        radius = 5.dp.toPx(),
        center = Offset(obsX, obsEyeY - 5.dp.toPx())
    )
    drawLine(
        color = Color(0xFF38BDF8),
        start = Offset(obsX, obsEyeY),
        end = Offset(obsX, groundY),
        strokeWidth = 3.dp.toPx()
    )

    // Garis horizontal sejajar mata (x = 15 m)
    drawLine(
        color = Color(0xFFCBD5E1),
        start = Offset(obsX, obsEyeY),
        end = Offset(treeX, obsEyeY),
        strokeWidth = 1.5.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
    )

    // Garis pandang sudut elevasi (hipotenusa)
    drawLine(
        color = Color(0xFF00FFF5),
        start = Offset(obsX, obsEyeY),
        end = Offset(treeX, treeTopY),
        strokeWidth = 2.dp.toPx()
    )

    // Siku-siku di persilangan horizontal & pohon
    val sqSize = 8.dp.toPx()
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(treeX - sqSize, obsEyeY),
        end = Offset(treeX - sqSize, obsEyeY - sqSize),
        strokeWidth = 1.5.dp.toPx()
    )
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(treeX - sqSize, obsEyeY - sqSize),
        end = Offset(treeX, obsEyeY - sqSize),
        strokeWidth = 1.5.dp.toPx()
    )

    // Busur Sudut 45°
    val arcR = 20.dp.toPx()
    drawArc(
        color = Color(0xFFFFB703),
        startAngle = 0f,
        sweepAngle = -45f,
        useCenter = false,
        topLeft = Offset(obsX - arcR, obsEyeY - arcR),
        size = Size(arcR * 2, arcR * 2),
        style = Stroke(width = 2.dp.toPx())
    )

    // Garis kurung tinggi pengamat (1.6 m)
    drawLine(
        color = Color(0xFFFBBF24),
        start = Offset(obsX - 10.dp.toPx(), obsEyeY),
        end = Offset(obsX - 10.dp.toPx(), groundY),
        strokeWidth = 1.5.dp.toPx()
    )

    // Teks Label Matematika
    drawContext.canvas.nativeCanvas.apply {
        textPaint.color = android.graphics.Color.parseColor("#FFB703")
        drawText("45°", obsX + 24.dp.toPx(), obsEyeY - 4.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#FBBF24")
        drawText("1,6 m", obsX - 34.dp.toPx(), (obsEyeY + groundY) / 2 + 4.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#CBD5E1")
        drawText("jarak = 15 m", (obsX + treeX) / 2 - 28.dp.toPx(), obsEyeY + 16.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#00FFF5")
        drawText("Tinggi Total = ?", treeX - 35.dp.toPx(), treeTopY - 8.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#FF70A6")
        drawText("y = 15 · tan(45°)", treeX + 12.dp.toPx(), (obsEyeY + treeTopY) / 2, textPaint)
    }
}

// Soal 2: Dua Pengamat di Seberang Sungai (Andi 60°, Budi 30°, Jarak Budi-Andi = 20 m)
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawIllustrationQuestion2(
    canvasW: Float,
    canvasH: Float,
    textPaint: android.graphics.Paint
) {
    val groundY = canvasH * 0.82f
    val treeX = canvasW * 0.84f
    val andiX = canvasW * 0.52f
    val budiX = canvasW * 0.16f
    val treeTopY = canvasH * 0.16f

    // Garis Tanah & Area Sungai
    drawLine(
        color = Color(0xFF10B981),
        start = Offset(0f, groundY),
        end = Offset(canvasW, groundY),
        strokeWidth = 3.dp.toPx()
    )

    // Ilustrasi Sungai (antara Andi dan Pohon)
    drawRect(
        color = Color(0xFF0284C7).copy(alpha = 0.35f),
        topLeft = Offset(andiX + 16.dp.toPx(), groundY - 2.dp.toPx()),
        size = Size(treeX - andiX - 22.dp.toPx(), 18.dp.toPx())
    )

    // Pohon Kelapa
    val trunk = Path().apply {
        moveTo(treeX - 7.dp.toPx(), groundY)
        lineTo(treeX - 4.dp.toPx(), treeTopY)
        lineTo(treeX + 4.dp.toPx(), treeTopY)
        lineTo(treeX + 7.dp.toPx(), groundY)
        close()
    }
    drawPath(trunk, color = Color(0xFF854D0E))
    drawCircle(Color(0xFF22C55E), radius = 16.dp.toPx(), center = Offset(treeX, treeTopY))

    // Titik Pengamat Budi & Andi
    drawCircle(Color(0xFFFFD166), radius = 4.dp.toPx(), center = Offset(budiX, groundY - 14.dp.toPx()))
    drawLine(Color(0xFF38BDF8), start = Offset(budiX, groundY - 10.dp.toPx()), end = Offset(budiX, groundY), strokeWidth = 2.5.dp.toPx())

    drawCircle(Color(0xFFFFB703), radius = 4.dp.toPx(), center = Offset(andiX, groundY - 14.dp.toPx()))
    drawLine(Color(0xFFF59E0B), start = Offset(andiX, groundY - 10.dp.toPx()), end = Offset(andiX, groundY), strokeWidth = 2.5.dp.toPx())

    // Garis Pandang Andi ke Puncak (60°)
    drawLine(
        color = Color(0xFFF59E0B),
        start = Offset(andiX, groundY),
        end = Offset(treeX, treeTopY),
        strokeWidth = 2.dp.toPx()
    )

    // Garis Pandang Budi ke Puncak (30°)
    drawLine(
        color = Color(0xFF00FFF5),
        start = Offset(budiX, groundY),
        end = Offset(treeX, treeTopY),
        strokeWidth = 2.dp.toPx()
    )

    // Jarak Budi ke Andi (20 m)
    val bracketY = groundY + 12.dp.toPx()
    drawLine(Color(0xFFE2E8F0), start = Offset(budiX, bracketY), end = Offset(andiX, bracketY), strokeWidth = 1.5.dp.toPx())
    drawLine(Color(0xFFE2E8F0), start = Offset(budiX, bracketY - 4.dp.toPx()), end = Offset(budiX, bracketY + 4.dp.toPx()), strokeWidth = 1.5.dp.toPx())
    drawLine(Color(0xFFE2E8F0), start = Offset(andiX, bracketY - 4.dp.toPx()), end = Offset(andiX, bracketY + 4.dp.toPx()), strokeWidth = 1.5.dp.toPx())

    drawContext.canvas.nativeCanvas.apply {
        textPaint.color = android.graphics.Color.parseColor("#E2E8F0")
        drawText("20 meter", (budiX + andiX) / 2 - 20.dp.toPx(), bracketY + 14.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#00FFF5")
        drawText("Budi (30°)", budiX - 16.dp.toPx(), groundY - 22.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#F59E0B")
        drawText("Andi (60°)", andiX - 16.dp.toPx(), groundY - 22.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#38BDF8")
        drawText("~ Sungai ~", (andiX + treeX) / 2 - 22.dp.toPx(), groundY + 10.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#00FFF5")
        drawText("Tinggi h = ?", treeX - 25.dp.toPx(), treeTopY - 6.dp.toPx(), textPaint)
    }
}

// Soal 3: Kuadran II & Segitiga siku-siku (sin α = 5/13, α tumpul)
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawIllustrationQuestion3(
    canvasW: Float,
    canvasH: Float,
    textPaint: android.graphics.Paint
) {
    val originX = canvasW * 0.58f
    val originY = canvasH * 0.65f

    // Sumbu Salib Koordinat Kartesius (X dan Y)
    drawLine(
        color = Color(0xFF64748B),
        start = Offset(20f, originY),
        end = Offset(canvasW - 20f, originY),
        strokeWidth = 1.5.dp.toPx()
    )
    drawLine(
        color = Color(0xFF64748B),
        start = Offset(originX, 15f),
        end = Offset(originX, canvasH - 15f),
        strokeWidth = 1.5.dp.toPx()
    )

    // Titik di Kuadran II: P(-12, 5)
    val pointX = originX - 70.dp.toPx()
    val pointY = originY - 50.dp.toPx()

    // Segitiga referensi di Kuadran II
    val triPath = Path().apply {
        moveTo(originX, originY)
        lineTo(pointX, originY)
        lineTo(pointX, pointY)
        close()
    }
    drawPath(triPath, color = Color(0xFF00FFF5).copy(alpha = 0.15f))

    // Hipotenusa (r = 13)
    drawLine(
        color = Color(0xFF00FFF5),
        start = Offset(originX, originY),
        end = Offset(pointX, pointY),
        strokeWidth = 3.dp.toPx()
    )

    // Sisi Tegak / Depan (y = +5)
    drawLine(
        color = Color(0xFF10B981),
        start = Offset(pointX, originY),
        end = Offset(pointX, pointY),
        strokeWidth = 2.5.dp.toPx()
    )

    // Sisi Datar / Samping (x = -12)
    drawLine(
        color = Color(0xFFEF4444),
        start = Offset(originX, originY),
        end = Offset(pointX, originY),
        strokeWidth = 2.5.dp.toPx()
    )

    // Sudut α dari sumbu X positif ke hipotenusa
    val arcR = 30.dp.toPx()
    drawArc(
        color = Color(0xFFFFB703),
        startAngle = 0f,
        sweepAngle = -145f,
        useCenter = false,
        topLeft = Offset(originX - arcR, originY - arcR),
        size = Size(arcR * 2, arcR * 2),
        style = Stroke(width = 2.dp.toPx())
    )

    drawContext.canvas.nativeCanvas.apply {
        textPaint.color = android.graphics.Color.parseColor("#94A3B8")
        drawText("Kuadran II (90° < α < 180°)", 25.dp.toPx(), 28.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#FFB703")
        drawText("Sudut α", originX - 18.dp.toPx(), originY - 35.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#00FFF5")
        drawText("miring = 13", (originX + pointX) / 2 - 20.dp.toPx(), (originY + pointY) / 2 - 8.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#10B981")
        drawText("depan y = +5", pointX - 58.dp.toPx(), (originY + pointY) / 2 + 4.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#EF4444")
        drawText("samping x = -12", (originX + pointX) / 2 - 32.dp.toPx(), originY + 16.dp.toPx(), textPaint)
    }
}

// Soal 4: Tangga Keselamatan Bersandar pada Pohon (Panjang 10m, Sudut 60°)
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawIllustrationQuestion4(
    canvasW: Float,
    canvasH: Float,
    textPaint: android.graphics.Paint
) {
    val groundY = canvasH * 0.85f
    val wallX = canvasW * 0.75f
    val ladderFootX = canvasW * 0.25f
    val ladderTopY = canvasH * 0.20f

    // Garis Tanah & Pohon Tegak
    drawLine(Color(0xFF10B981), start = Offset(15f, groundY), end = Offset(canvasW - 15f, groundY), strokeWidth = 3.dp.toPx())

    // Batang pohon kelapa tegak lurus
    drawLine(Color(0xFF854D0E), start = Offset(wallX, groundY), end = Offset(wallX, 15f), strokeWidth = 14.dp.toPx())
    // Indikator siku-siku di pangkal pohon
    val sq = 10.dp.toPx()
    drawLine(Color(0xFF94A3B8), start = Offset(wallX - sq, groundY), end = Offset(wallX - sq, groundY - sq), strokeWidth = 1.5.dp.toPx())
    drawLine(Color(0xFF94A3B8), start = Offset(wallX - sq, groundY - sq), end = Offset(wallX, groundY - sq), strokeWidth = 1.5.dp.toPx())

    // Tangga Besi (Hipotenusa = 10 m)
    drawLine(
        color = Color(0xFF00FFF5),
        start = Offset(ladderFootX, groundY),
        end = Offset(wallX, ladderTopY),
        strokeWidth = 4.dp.toPx(),
        cap = StrokeCap.Round
    )

    // Anak Tangga (Rungs)
    for (i in 1..7) {
        val frac = i / 8f
        val rx = ladderFootX + (wallX - ladderFootX) * frac
        val ry = groundY + (ladderTopY - groundY) * frac
        drawLine(
            color = Color(0xFFF8FAFC),
            start = Offset(rx - 4.dp.toPx(), ry + 3.dp.toPx()),
            end = Offset(rx + 4.dp.toPx(), ry - 3.dp.toPx()),
            strokeWidth = 2.dp.toPx()
        )
    }

    // Busur Sudut 60°
    val arcR = 26.dp.toPx()
    drawArc(
        color = Color(0xFFFFB703),
        startAngle = 0f,
        sweepAngle = -60f,
        useCenter = false,
        topLeft = Offset(ladderFootX - arcR, groundY - arcR),
        size = Size(arcR * 2, arcR * 2),
        style = Stroke(width = 2.dp.toPx())
    )

    drawContext.canvas.nativeCanvas.apply {
        textPaint.color = android.graphics.Color.parseColor("#FFB703")
        drawText("60°", ladderFootX + 30.dp.toPx(), groundY - 8.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#00FFF5")
        drawText("Tangga = 10 meter", (ladderFootX + wallX) / 2 - 45.dp.toPx(), (groundY + ladderTopY) / 2 - 10.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#38BDF8")
        drawText("Jarak kaki = 10·cos(60°)", (ladderFootX + wallX) / 2 - 40.dp.toPx(), groundY + 16.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#F472B6")
        drawText("Tinggi sentuh = 10·sin(60°)", wallX + 10.dp.toPx(), (groundY + ladderTopY) / 2, textPaint)
    }
}

// Soal 5: Identitas Trigonometri & Geometri Segitiga Satuan sin²x + cos²x = 1
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawIllustrationQuestion5(
    canvasW: Float,
    canvasH: Float,
    textPaint: android.graphics.Paint
) {
    val originX = canvasW * 0.35f
    val originY = canvasH * 0.75f
    val r = 65.dp.toPx()

    // Sumbu koordinat kecil
    drawLine(Color(0xFF64748B), start = Offset(originX - 10.dp.toPx(), originY), end = Offset(originX + r + 20.dp.toPx(), originY), strokeWidth = 1.5.dp.toPx())
    drawLine(Color(0xFF64748B), start = Offset(originX, originY + 10.dp.toPx()), end = Offset(originX, originY - r - 20.dp.toPx()), strokeWidth = 1.5.dp.toPx())

    // Busur lingkaran satuan
    drawArc(
        color = Color(0xFF334155),
        startAngle = -90f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = Offset(originX - r, originY - r),
        size = Size(r * 2, r * 2),
        style = Stroke(width = 1.5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
    )

    // Sudut x = 40°
    val rad = Math.toRadians(40.0)
    val px = originX + (cos(rad) * r).toFloat()
    val py = originY - (sin(rad) * r).toFloat()

    // Segitiga siku-siku identitas
    val tri = Path().apply {
        moveTo(originX, originY)
        lineTo(px, originY)
        lineTo(px, py)
        close()
    }
    drawPath(tri, color = Color(0xFF00ADB5).copy(alpha = 0.2f))

    // Hipotenusa = 1
    drawLine(Color(0xFF00FFF5), start = Offset(originX, originY), end = Offset(px, py), strokeWidth = 2.5.dp.toPx())
    // Sisi depan = sin x
    drawLine(Color(0xFFF472B6), start = Offset(px, originY), end = Offset(px, py), strokeWidth = 2.5.dp.toPx())
    // Sisi samping = cos x
    drawLine(Color(0xFF38BDF8), start = Offset(originX, originY), end = Offset(px, originY), strokeWidth = 2.5.dp.toPx())

    drawContext.canvas.nativeCanvas.apply {
        textPaint.color = android.graphics.Color.parseColor("#00FFF5")
        drawText("r = 1", (originX + px) / 2 - 14.dp.toPx(), (originY + py) / 2 - 6.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#38BDF8")
        drawText("cos(x)", (originX + px) / 2 - 12.dp.toPx(), originY + 14.dp.toPx(), textPaint)

        textPaint.color = android.graphics.Color.parseColor("#F472B6")
        drawText("sin(x)", px + 6.dp.toPx(), (originY + py) / 2, textPaint)

        // Rumus Identitas Pokok di kanan
        textPaint.color = android.graphics.Color.parseColor("#FFB703")
        drawText("Identitas Pythagoras:", canvasW * 0.58f, canvasH * 0.32f, textPaint)

        textPaint.color = android.graphics.Color.parseColor("#F8FAFC")
        drawText("sin²(x) + cos²(x) = 1", canvasW * 0.58f, canvasH * 0.50f, textPaint)

        textPaint.color = android.graphics.Color.parseColor("#CBD5E1")
        drawText("(sin x + cos x)² = 1 + 2 sin x cos x", canvasW * 0.58f, canvasH * 0.68f, textPaint)
    }
}
