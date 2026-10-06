import React, { useRef, useEffect } from 'react';

interface CoconutTreeSimulationCanvasProps {
  distanceMeters: number;
  observerHeightMeters: number;
  elevationAngleDegrees: number;
  calculatedTreeHeight: number;
  className?: string;
}

export const CoconutTreeSimulationCanvas: React.FC<CoconutTreeSimulationCanvasProps> = ({
  distanceMeters,
  observerHeightMeters,
  elevationAngleDegrees,
  calculatedTreeHeight,
  className = '',
}) => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    // Handle high DPI displays
    const dpr = window.devicePixelRatio || 1;
    const rect = canvas.getBoundingClientRect();
    const width = rect.width;
    const height = rect.height;

    canvas.width = width * dpr;
    canvas.height = height * dpr;
    ctx.scale(dpr, dpr);

    // Background Gradient (Sky to ground horizon)
    const skyGrad = ctx.createLinearGradient(0, 0, 0, height);
    skyGrad.addColorStop(0, '#0B192C');
    skyGrad.addColorStop(0.75, '#1E3E62');
    skyGrad.addColorStop(1, '#0B2418');
    ctx.fillStyle = skyGrad;
    ctx.fillRect(0, 0, width, height);

    // Coordinate Layout
    const groundY = height * 0.82;
    const observerX = Math.max(45, width * 0.18);
    const treeX = Math.min(width - 50, width * 0.82);

    // Scale calculations
    const maxDisplayHeightMeters = 35.0;
    const scaleY = (height * 0.65) / maxDisplayHeightMeters;

    const observerEyeY = groundY - observerHeightMeters * scaleY;
    const radAngle = (elevationAngleDegrees * Math.PI) / 180;
    const oppositeHeight = distanceMeters * Math.tan(radAngle);
    const rawTreeTopY = observerEyeY - oppositeHeight * scaleY;
    const treeTopVisualY = Math.max(height * 0.12, rawTreeTopY);

    // 1. Draw Ground Fill & Line
    ctx.fillStyle = '#064E3B';
    ctx.fillRect(0, groundY, width, height - groundY);

    ctx.strokeStyle = '#10B981';
    ctx.lineWidth = 4;
    ctx.beginPath();
    ctx.moveTo(0, groundY);
    ctx.lineTo(width, groundY);
    ctx.stroke();

    // 2. Draw Coconut Tree Trunk
    const trunkBottomWidth = 14;
    const trunkTopWidth = 9;
    const ctrlX = treeX + 12;
    const ctrlY = (groundY + treeTopVisualY) / 2;

    ctx.fillStyle = '#854D0E';
    ctx.beginPath();
    ctx.moveTo(treeX - trunkBottomWidth / 2, groundY);
    ctx.quadraticCurveTo(ctrlX - 4, ctrlY, treeX - trunkTopWidth / 2, treeTopVisualY);
    ctx.lineTo(treeX + trunkTopWidth / 2, treeTopVisualY);
    ctx.quadraticCurveTo(ctrlX + 6, ctrlY, treeX + trunkBottomWidth / 2, groundY);
    ctx.closePath();
    ctx.fill();

    // Trunk ring details
    ctx.strokeStyle = '#713F12';
    ctx.lineWidth = 1.5;
    for (let step = 1; step <= 8; step++) {
      const ratio = step / 9;
      const ringY = groundY - (groundY - treeTopVisualY) * ratio;
      ctx.beginPath();
      ctx.moveTo(treeX - 8, ringY);
      ctx.lineTo(treeX + 8, ringY);
      ctx.stroke();
    }

    // 3. Draw Palm Leaves (Fronds) & Coconuts
    const leafAngles = [-60, -30, 0, 30, 60, -90, 90, -120, -150];
    ctx.strokeStyle = '#22C55E';
    ctx.lineWidth = 3;
    ctx.lineCap = 'round';

    leafAngles.forEach((deg) => {
      const rad = (deg * Math.PI) / 180;
      const leafLen = 32;
      const endX = treeX + Math.cos(rad) * leafLen;
      const endY = treeTopVisualY + Math.sin(rad) * leafLen * 0.55 - 6;

      ctx.beginPath();
      ctx.moveTo(treeX, treeTopVisualY);
      ctx.quadraticCurveTo((treeX + endX) / 2, treeTopVisualY - 14, endX, endY);
      ctx.stroke();
    });

    // Coconuts
    ctx.fillStyle = '#A16207';
    ctx.beginPath();
    ctx.arc(treeX - 4, treeTopVisualY + 4, 4, 0, Math.PI * 2);
    ctx.fill();

    ctx.fillStyle = '#CA8A04';
    ctx.beginPath();
    ctx.arc(treeX + 4, treeTopVisualY + 5, 3.5, 0, Math.PI * 2);
    ctx.fill();

    // 4. Draw Observer (Student with Clinometer)
    const headRadius = 7;
    ctx.fillStyle = '#FFD166';
    ctx.beginPath();
    ctx.arc(observerX, observerEyeY - headRadius / 2, headRadius, 0, Math.PI * 2);
    ctx.fill();

    // Body
    ctx.strokeStyle = '#38BDF8';
    ctx.lineWidth = 3.5;
    ctx.beginPath();
    ctx.moveTo(observerX, observerEyeY + headRadius / 2);
    ctx.lineTo(observerX, groundY);
    ctx.stroke();

    // Clinometer arm pointing along angle
    const armLen = 14;
    const clinoEndX = observerX + Math.cos(radAngle) * armLen;
    const clinoEndY = observerEyeY - Math.sin(radAngle) * armLen;
    ctx.strokeStyle = '#F59E0B';
    ctx.lineWidth = 3;
    ctx.beginPath();
    ctx.moveTo(observerX, observerEyeY);
    ctx.lineTo(clinoEndX, clinoEndY);
    ctx.stroke();

    // 5. Draw Trigonometric Sight Line (Hypotenuse)
    ctx.strokeStyle = '#00FFF5';
    ctx.lineWidth = 2.5;
    ctx.setLineDash([10, 6]);
    ctx.beginPath();
    ctx.moveTo(observerX, observerEyeY);
    ctx.lineTo(treeX, treeTopVisualY);
    ctx.stroke();
    ctx.setLineDash([]); // Reset dash

    // 6. Draw Horizontal Eye-Line (Adjacent)
    ctx.strokeStyle = '#E2E8F0';
    ctx.lineWidth = 1.5;
    ctx.setLineDash([6, 5]);
    ctx.beginPath();
    ctx.moveTo(observerX, observerEyeY);
    ctx.lineTo(treeX, observerEyeY);
    ctx.stroke();
    ctx.setLineDash([]);

    // 7. Right-Angle Square Indicator
    const sqSize = 10;
    ctx.strokeStyle = '#CBD5E1';
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.moveTo(treeX - sqSize, observerEyeY);
    ctx.lineTo(treeX - sqSize, observerEyeY - sqSize);
    ctx.lineTo(treeX, observerEyeY - sqSize);
    ctx.stroke();

    // 8. Elevation Angle Arc
    const arcRadius = 24;
    ctx.strokeStyle = '#FFB703';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.arc(observerX, observerEyeY, arcRadius, -radAngle, 0, false);
    ctx.stroke();

    // 9. Tree vertical Opposite line (y)
    ctx.strokeStyle = '#FF70A6';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(treeX + 16, treeTopVisualY);
    ctx.lineTo(treeX + 16, observerEyeY);
    ctx.stroke();

    // Observer height bracket line
    ctx.strokeStyle = '#FBBF24';
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(observerX - 14, observerEyeY);
    ctx.lineTo(observerX - 14, groundY);
    ctx.stroke();

    // 10. Labels text
    ctx.font = 'bold 11px Inter, sans-serif';

    // Angle θ label
    ctx.fillStyle = '#FFB703';
    ctx.fillText(`${Math.round(elevationAngleDegrees)}°`, observerX + 28, observerEyeY - 6);

    // Baseline distance d label
    ctx.fillStyle = '#E2E8F0';
    ctx.textAlign = 'center';
    ctx.fillText(`d = ${distanceMeters.toFixed(1)} m`, (observerX + treeX) / 2, observerEyeY + 16);

    // Observer height label
    ctx.fillStyle = '#FBBF24';
    ctx.textAlign = 'right';
    ctx.fillText(`${observerHeightMeters.toFixed(1)}m`, observerX - 18, (observerEyeY + groundY) / 2 + 4);

    // Opposite y label
    ctx.fillStyle = '#FF70A6';
    ctx.textAlign = 'left';
    ctx.fillText(`y = ${oppositeHeight.toFixed(1)} m`, treeX + 22, (treeTopVisualY + observerEyeY) / 2 + 4);

    // Total Tree Height Crown Badge
    ctx.fillStyle = '#00FFF5';
    ctx.textAlign = 'center';
    ctx.fillText(`Tinggi = ${calculatedTreeHeight.toFixed(2)} m`, treeX, treeTopVisualY - 14);

  }, [distanceMeters, observerHeightMeters, elevationAngleDegrees, calculatedTreeHeight]);

  return (
    <div className={`w-full overflow-hidden rounded-2xl border border-[#1E3A5F] shadow-lg relative bg-[#0B192C] ${className}`}>
      <canvas
        ref={canvasRef}
        className="w-full h-[260px] md:h-[290px] block"
      />
    </div>
  );
};
