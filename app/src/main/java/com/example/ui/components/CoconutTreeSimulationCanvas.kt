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
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun CoconutTreeSimulationCanvas(
    distanceMeters: Float,
    observerHeightMeters: Float,
    elevationAngleDegrees: Float,
    calculatedTreeHeight: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF1E3A5F), RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B192C), // Sky top
                        Color(0xFF1E3E62), // Sky horizon
                        Color(0xFF0B2418)  // Ground
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            // Coordinate layout
            val groundY = canvasH * 0.82f
            val observerX = canvasW * 0.18f
            val treeX = canvasW * 0.82f
            val baselineSpanX = treeX - observerX

            // Scale calculations (dynamic mapping so tree fits in canvas)
            // Estimated tree height in canvas coordinates
            val maxDisplayHeightMeters = 35.0f
            val scaleY = (canvasH * 0.65f) / maxDisplayHeightMeters

            val observerEyeY = groundY - (observerHeightMeters * scaleY)
            val treeTopVisualY = (observerEyeY - (distanceMeters * tan(Math.toRadians(elevationAngleDegrees.toDouble())) * scaleY)).toFloat()
                .coerceAtLeast(canvasH * 0.12f)

            // 1. Draw Ground Line & Grass
            drawLine(
                color = Color(0xFF10B981),
                start = Offset(0f, groundY),
                end = Offset(canvasW, groundY),
                strokeWidth = 4.dp.toPx()
            )
            // Ground fill
            drawRect(
                color = Color(0xFF064E3B),
                topLeft = Offset(0f, groundY),
                size = Size(canvasW, canvasH - groundY)
            )

            // 2. Draw Coconut Tree Trunk
            val trunkBottomWidth = 14.dp.toPx()
            val trunkTopWidth = 9.dp.toPx()

            val trunkPath = Path().apply {
                moveTo(treeX - trunkBottomWidth / 2, groundY)
                // Slight curved natural palm trunk
                val ctrlX = treeX + 12.dp.toPx()
                val ctrlY = (groundY + treeTopVisualY) / 2
                quadraticBezierTo(ctrlX - 4.dp.toPx(), ctrlY, treeX - trunkTopWidth / 2, treeTopVisualY)
                lineTo(treeX + trunkTopWidth / 2, treeTopVisualY)
                quadraticBezierTo(ctrlX + 6.dp.toPx(), ctrlY, treeX + trunkBottomWidth / 2, groundY)
                close()
            }
            drawPath(
                path = trunkPath,
                color = Color(0xFF854D0E) // Palm trunk wood color
            )

            // Palm Trunk ring details
            for (step in 1..8) {
                val ratio = step / 9f
                val ringY = groundY - (groundY - treeTopVisualY) * ratio
                drawLine(
                    color = Color(0xFF713F12),
                    start = Offset(treeX - 8.dp.toPx(), ringY),
                    end = Offset(treeX + 8.dp.toPx(), ringY),
                    strokeWidth = 1.5.dp.toPx()
                )
            }

            // 3. Draw Palm Leaves (Fronds) & Coconuts
            val leafAngles = listOf(-60f, -30f, 0f, 30f, 60f, -90f, 90f, -120f, -150f)
            leafAngles.forEach { deg ->
                val rad = Math.toRadians(deg.toDouble())
                val leafLen = 32.dp.toPx()
                val endX = treeX + (cos(rad) * leafLen).toFloat()
                val endY = treeTopVisualY + (sin(rad) * leafLen * 0.55f).toFloat() - 6.dp.toPx()

                val leafPath = Path().apply {
                    moveTo(treeX, treeTopVisualY)
                    quadraticBezierTo(
                        (treeX + endX) / 2,
                        treeTopVisualY - 14.dp.toPx(),
                        endX,
                        endY
                    )
                }
                drawPath(
                    path = leafPath,
                    color = Color(0xFF22C55E),
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Coconuts at top
            drawCircle(
                color = Color(0xFFA16207),
                radius = 4.dp.toPx(),
                center = Offset(treeX - 4.dp.toPx(), treeTopVisualY + 4.dp.toPx())
            )
            drawCircle(
                color = Color(0xFFCA8A04),
                radius = 3.5.dp.toPx(),
                center = Offset(treeX + 4.dp.toPx(), treeTopVisualY + 5.dp.toPx())
            )

            // 4. Draw Observer (Student with Clinometer)
            val headRadius = 7.dp.toPx()
            // Student Head
            drawCircle(
                color = Color(0xFFFFD166),
                radius = headRadius,
                center = Offset(observerX, observerEyeY - headRadius / 2)
            )
            // Body line
            drawLine(
                color = Color(0xFF38BDF8),
                start = Offset(observerX, observerEyeY + headRadius / 2),
                end = Offset(observerX, groundY),
                strokeWidth = 3.5.dp.toPx(),
                cap = StrokeCap.Round
            )
            // Clinometer device line pointing to tree top
            val armLen = 14.dp.toPx()
            val radAngle = Math.toRadians(elevationAngleDegrees.toDouble())
            val clinoEndX = observerX + (cos(radAngle) * armLen).toFloat()
            val clinoEndY = observerEyeY - (sin(radAngle) * armLen).toFloat()
            drawLine(
                color = Color(0xFFF59E0B),
                start = Offset(observerX, observerEyeY),
                end = Offset(clinoEndX, clinoEndY),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )

            // 5. Draw Trigonometric Sight Line (Hypotenuse)
            drawLine(
                color = Color(0xFF00FFF5),
                start = Offset(observerX, observerEyeY),
                end = Offset(treeX, treeTopVisualY),
                strokeWidth = 2.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
            )

            // 6. Draw Horizontal Eye-Line (Adjacent / Jarak Samping)
            val treeEyeLevelPoint = Offset(treeX, observerEyeY)
            drawLine(
                color = Color(0xFFE2E8F0),
                start = Offset(observerX, observerEyeY),
                end = treeEyeLevelPoint,
                strokeWidth = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
            )

            // 7. Right-Angle Square indicator at treeEyeLevelPoint
            val squareSize = 10.dp.toPx()
            drawLine(
                color = Color(0xFFCBD5E1),
                start = Offset(treeX - squareSize, observerEyeY),
                end = Offset(treeX - squareSize, observerEyeY - squareSize),
                strokeWidth = 1.5.dp.toPx()
            )
            drawLine(
                color = Color(0xFFCBD5E1),
                start = Offset(treeX - squareSize, observerEyeY - squareSize),
                end = Offset(treeX, observerEyeY - squareSize),
                strokeWidth = 1.5.dp.toPx()
            )

            // 8. Elevation Angle Arc at Observer Eye
            val arcRadius = 26.dp.toPx()
            drawArc(
                color = Color(0xFFFFB703),
                startAngle = 0f,
                sweepAngle = -elevationAngleDegrees,
                useCenter = false,
                topLeft = Offset(observerX - arcRadius, observerEyeY - arcRadius),
                size = Size(arcRadius * 2, arcRadius * 2),
                style = Stroke(width = 2.dp.toPx())
            )

            // 9. Tree vertical Opposite line (y = d * tan theta)
            drawLine(
                color = Color(0xFFFF70A6),
                start = Offset(treeX + 16.dp.toPx(), treeTopVisualY),
                end = Offset(treeX + 16.dp.toPx(), observerEyeY),
                strokeWidth = 2.dp.toPx()
            )

            // Observer height bracket line (t_pengamat)
            drawLine(
                color = Color(0xFFFBBF24),
                start = Offset(observerX - 16.dp.toPx(), observerEyeY),
                end = Offset(observerX - 16.dp.toPx(), groundY),
                strokeWidth = 2.dp.toPx()
            )

            // 10. Draw Native Canvas Text Labels for Crisp Visualization
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 11.dp.toPx()
                typeface = android.graphics.Typeface.DEFAULT_BOLD
            }

            drawContext.canvas.nativeCanvas.apply {
                // Angle label (theta)
                paint.color = android.graphics.Color.parseColor("#FFB703")
                drawText(
                    "${elevationAngleDegrees.toInt()}°",
                    observerX + 30.dp.toPx(),
                    observerEyeY - 6.dp.toPx(),
                    paint
                )

                // Baseline Distance label
                paint.color = android.graphics.Color.parseColor("#E2E8F0")
                val distanceLabel = String.format(Locale.US, "d = %.1f m", distanceMeters)
                drawText(
                    distanceLabel,
                    (observerX + treeX) / 2 - 25.dp.toPx(),
                    observerEyeY + 16.dp.toPx(),
                    paint
                )

                // Observer height label
                paint.color = android.graphics.Color.parseColor("#FBBF24")
                val obsLabel = String.format(Locale.US, "%.1fm", observerHeightMeters)
                drawText(
                    obsLabel,
                    observerX - 44.dp.toPx(),
                    (observerEyeY + groundY) / 2,
                    paint
                )

                // Tree opposite height (y) label
                paint.color = android.graphics.Color.parseColor("#FF70A6")
                val yVal = distanceMeters * tan(Math.toRadians(elevationAngleDegrees.toDouble()))
                val yLabel = String.format(Locale.US, "y = %.1f m", yVal)
                drawText(
                    yLabel,
                    treeX + 20.dp.toPx(),
                    (treeTopVisualY + observerEyeY) / 2,
                    paint
                )

                // Total tree height crown badge
                paint.color = android.graphics.Color.parseColor("#00FFF5")
                val totalLabel = String.format(Locale.US, "Tinggi = %.2f m", calculatedTreeHeight)
                drawText(
                    totalLabel,
                    treeX - 40.dp.toPx(),
                    treeTopVisualY - 14.dp.toPx(),
                    paint
                )
            }
        }
    }
}
