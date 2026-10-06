import React, { useRef, useEffect } from 'react';

interface QuestionIllustrationProps {
  questionId: number;
  className?: string;
}

export const QuestionIllustration: React.FC<QuestionIllustrationProps> = ({ questionId, className = '' }) => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    const dpr = window.devicePixelRatio || 1;
    const rect = canvas.getBoundingClientRect();
    const width = rect.width;
    const height = rect.height;

    canvas.width = width * dpr;
    canvas.height = height * dpr;
    ctx.scale(dpr, dpr);

    // Dark gradient background
    const bgGrad = ctx.createLinearGradient(0, 0, 0, height);
    bgGrad.addColorStop(0, '#0B192C');
    bgGrad.addColorStop(0.5, '#0F2B48');
    bgGrad.addColorStop(1, '#081424');
    ctx.fillStyle = bgGrad;
    ctx.fillRect(0, 0, width, height);

    ctx.font = 'bold 11px Inter, sans-serif';

    switch (questionId) {
      case 1:
        drawQuestion1(ctx, width, height);
        break;
      case 2:
        drawQuestion2(ctx, width, height);
        break;
      case 3:
        drawQuestion3(ctx, width, height);
        break;
      case 4:
        drawQuestion4(ctx, width, height);
        break;
      case 5:
        drawQuestion5(ctx, width, height);
        break;
      default:
        break;
    }
  }, [questionId]);

  return (
    <div className={`w-full overflow-hidden rounded-xl border border-[#1E3A5F] shadow bg-[#0B192C] ${className}`}>
      <canvas
        ref={canvasRef}
        className="w-full h-[180px] block"
      />
    </div>
  );
};

// Soal 1: Pohon Kelapa (Klinometer 1.6m, Jarak 15m, Sudut 45°)
function drawQuestion1(ctx: CanvasRenderingContext2D, width: number, height: number) {
  const groundY = height * 0.85;
  const obsX = width * 0.20;
  const treeX = width * 0.80;
  const treeTopY = height * 0.18;
  const obsEyeY = groundY - 24;

  // Ground line
  ctx.strokeStyle = '#10B981';
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(10, groundY);
  ctx.lineTo(width - 10, groundY);
  ctx.stroke();

  // Trunk
  ctx.fillStyle = '#854D0E';
  ctx.beginPath();
  ctx.moveTo(treeX - 8, groundY);
  ctx.quadraticCurveTo(treeX + 6, (groundY + treeTopY) / 2, treeX - 5, treeTopY);
  ctx.lineTo(treeX + 5, treeTopY);
  ctx.quadraticCurveTo(treeX + 12, (groundY + treeTopY) / 2, treeX + 8, groundY);
  ctx.closePath();
  ctx.fill();

  // Leaves
  const angles = [-70, -40, -10, 20, 50, 80, -110, -140];
  ctx.strokeStyle = '#22C55E';
  ctx.lineWidth = 2.5;
  ctx.lineCap = 'round';
  angles.forEach((deg) => {
    const rad = (deg * Math.PI) / 180;
    const len = 22;
    ctx.beginPath();
    ctx.moveTo(treeX, treeTopY);
    ctx.lineTo(treeX + Math.cos(rad) * len, treeTopY + Math.sin(rad) * len * 0.5);
    ctx.stroke();
  });

  // Observer
  ctx.fillStyle = '#FFD166';
  ctx.beginPath();
  ctx.arc(obsX, obsEyeY - 5, 5, 0, Math.PI * 2);
  ctx.fill();

  ctx.strokeStyle = '#38BDF8';
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(obsX, obsEyeY);
  ctx.lineTo(obsX, groundY);
  ctx.stroke();

  // Eye level horizontal
  ctx.strokeStyle = '#CBD5E1';
  ctx.lineWidth = 1.5;
  ctx.setLineDash([6, 5]);
  ctx.beginPath();
  ctx.moveTo(obsX, obsEyeY);
  ctx.lineTo(treeX, obsEyeY);
  ctx.stroke();

  // Hypotenuse
  ctx.strokeStyle = '#00FFF5';
  ctx.lineWidth = 2;
  ctx.setLineDash([]);
  ctx.beginPath();
  ctx.moveTo(obsX, obsEyeY);
  ctx.lineTo(treeX, treeTopY);
  ctx.stroke();

  // Right angle indicator
  const sq = 8;
  ctx.strokeStyle = '#94A3B8';
  ctx.beginPath();
  ctx.moveTo(treeX - sq, obsEyeY);
  ctx.lineTo(treeX - sq, obsEyeY - sq);
  ctx.lineTo(treeX, obsEyeY - sq);
  ctx.stroke();

  // Arc 45
  ctx.strokeStyle = '#FFB703';
  ctx.beginPath();
  ctx.arc(obsX, obsEyeY, 20, -Math.PI / 4, 0);
  ctx.stroke();

  // Observer bracket
  ctx.strokeStyle = '#FBBF24';
  ctx.beginPath();
  ctx.moveTo(obsX - 10, obsEyeY);
  ctx.lineTo(obsX - 10, groundY);
  ctx.stroke();

  // Labels
  ctx.fillStyle = '#FFB703';
  ctx.textAlign = 'left';
  ctx.fillText('45°', obsX + 24, obsEyeY - 4);

  ctx.fillStyle = '#FBBF24';
  ctx.textAlign = 'right';
  ctx.fillText('1,6 m', obsX - 14, (obsEyeY + groundY) / 2 + 4);

  ctx.fillStyle = '#CBD5E1';
  ctx.textAlign = 'center';
  ctx.fillText('jarak = 15 m', (obsX + treeX) / 2, obsEyeY + 16);

  ctx.fillStyle = '#00FFF5';
  ctx.textAlign = 'center';
  ctx.fillText('Tinggi Total = ?', treeX, treeTopY - 8);

  ctx.fillStyle = '#FF70A6';
  ctx.textAlign = 'left';
  ctx.fillText('y = 15 · tan(45°)', treeX + 12, (obsEyeY + treeTopY) / 2);
}

// Soal 2: Dua Pengamat di Seberang Sungai (Andi 60°, Budi 30°, Jarak Budi-Andi = 20 m)
function drawQuestion2(ctx: CanvasRenderingContext2D, width: number, height: number) {
  const groundY = height * 0.82;
  const treeX = width * 0.84;
  const andiX = width * 0.52;
  const budiX = width * 0.16;
  const treeTopY = height * 0.16;

  // Ground
  ctx.strokeStyle = '#10B981';
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(0, groundY);
  ctx.lineTo(width, groundY);
  ctx.stroke();

  // River water
  ctx.fillStyle = 'rgba(2, 132, 199, 0.35)';
  ctx.fillRect(andiX + 16, groundY - 2, treeX - andiX - 22, 18);

  // Tree
  ctx.fillStyle = '#854D0E';
  ctx.beginPath();
  ctx.moveTo(treeX - 7, groundY);
  ctx.lineTo(treeX - 4, treeTopY);
  ctx.lineTo(treeX + 4, treeTopY);
  ctx.lineTo(treeX + 7, groundY);
  ctx.closePath();
  ctx.fill();

  ctx.fillStyle = '#22C55E';
  ctx.beginPath();
  ctx.arc(treeX, treeTopY, 16, 0, Math.PI * 2);
  ctx.fill();

  // Budi & Andi
  ctx.fillStyle = '#FFD166';
  ctx.beginPath();
  ctx.arc(budiX, groundY - 14, 4, 0, Math.PI * 2);
  ctx.fill();
  ctx.strokeStyle = '#38BDF8';
  ctx.lineWidth = 2.5;
  ctx.beginPath();
  ctx.moveTo(budiX, groundY - 10);
  ctx.lineTo(budiX, groundY);
  ctx.stroke();

  ctx.fillStyle = '#FFB703';
  ctx.beginPath();
  ctx.arc(andiX, groundY - 14, 4, 0, Math.PI * 2);
  ctx.fill();
  ctx.strokeStyle = '#F59E0B';
  ctx.beginPath();
  ctx.moveTo(andiX, groundY - 10);
  ctx.lineTo(andiX, groundY);
  ctx.stroke();

  // Sight lines
  ctx.strokeStyle = '#F59E0B';
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(andiX, groundY);
  ctx.lineTo(treeX, treeTopY);
  ctx.stroke();

  ctx.strokeStyle = '#00FFF5';
  ctx.beginPath();
  ctx.moveTo(budiX, groundY);
  ctx.lineTo(treeX, treeTopY);
  ctx.stroke();

  // Bracket Budi-Andi
  const bracketY = groundY + 12;
  ctx.strokeStyle = '#E2E8F0';
  ctx.lineWidth = 1.5;
  ctx.beginPath();
  ctx.moveTo(budiX, bracketY);
  ctx.lineTo(andiX, bracketY);
  ctx.stroke();

  // Labels
  ctx.fillStyle = '#E2E8F0';
  ctx.textAlign = 'center';
  ctx.fillText('20 meter', (budiX + andiX) / 2, bracketY + 14);

  ctx.fillStyle = '#00FFF5';
  ctx.fillText('Budi (30°)', budiX, groundY - 22);

  ctx.fillStyle = '#F59E0B';
  ctx.fillText('Andi (60°)', andiX, groundY - 22);

  ctx.fillStyle = '#38BDF8';
  ctx.fillText('~ Sungai ~', (andiX + treeX) / 2, groundY + 10);

  ctx.fillStyle = '#00FFF5';
  ctx.fillText('Tinggi h = ?', treeX, treeTopY - 6);
}

// Soal 3: Kuadran II & Koordinat Kartesius (sin α = 5/13, α tumpul)
function drawQuestion3(ctx: CanvasRenderingContext2D, width: number, height: number) {
  const originX = width * 0.58;
  const originY = height * 0.65;

  // Axes
  ctx.strokeStyle = '#64748B';
  ctx.lineWidth = 1.5;
  ctx.beginPath();
  ctx.moveTo(20, originY);
  ctx.lineTo(width - 20, originY);
  ctx.moveTo(originX, 15);
  ctx.lineTo(originX, height - 15);
  ctx.stroke();

  // Point in Q2
  const pointX = originX - 70;
  const pointY = originY - 50;

  // Triangle fill
  ctx.fillStyle = 'rgba(0, 255, 245, 0.15)';
  ctx.beginPath();
  ctx.moveTo(originX, originY);
  ctx.lineTo(pointX, originY);
  ctx.lineTo(pointX, pointY);
  ctx.closePath();
  ctx.fill();

  // Hypotenuse (r = 13)
  ctx.strokeStyle = '#00FFF5';
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(originX, originY);
  ctx.lineTo(pointX, pointY);
  ctx.stroke();

  // Opposite (y = 5)
  ctx.strokeStyle = '#10B981';
  ctx.lineWidth = 2.5;
  ctx.beginPath();
  ctx.moveTo(pointX, originY);
  ctx.lineTo(pointX, pointY);
  ctx.stroke();

  // Adjacent (x = -12)
  ctx.strokeStyle = '#EF4444';
  ctx.beginPath();
  ctx.moveTo(originX, originY);
  ctx.lineTo(pointX, originY);
  ctx.stroke();

  // Angle arc
  ctx.strokeStyle = '#FFB703';
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.arc(originX, originY, 30, Math.PI, 0, true);
  ctx.stroke();

  // Labels
  ctx.fillStyle = '#94A3B8';
  ctx.textAlign = 'left';
  ctx.fillText('Kuadran II (90° < α < 180°)', 25, 26);

  ctx.fillStyle = '#FFB703';
  ctx.fillText('Sudut α', originX - 35, originY - 35);

  ctx.fillStyle = '#00FFF5';
  ctx.fillText('miring = 13', (originX + pointX) / 2 - 20, (originY + pointY) / 2 - 8);

  ctx.fillStyle = '#10B981';
  ctx.textAlign = 'right';
  ctx.fillText('depan y = +5', pointX - 6, (originY + pointY) / 2 + 4);

  ctx.fillStyle = '#EF4444';
  ctx.textAlign = 'center';
  ctx.fillText('samping x = -12', (originX + pointX) / 2, originY + 16);
}

// Soal 4: Tangga Keselamatan Bersandar pada Pohon (Panjang 10m, Sudut 60°)
function drawQuestion4(ctx: CanvasRenderingContext2D, width: number, height: number) {
  const groundY = height * 0.85;
  const wallX = width * 0.75;
  const ladderFootX = width * 0.25;
  const ladderTopY = height * 0.20;

  // Ground
  ctx.strokeStyle = '#10B981';
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(15, groundY);
  ctx.lineTo(width - 15, groundY);
  ctx.stroke();

  // Tree trunk
  ctx.strokeStyle = '#854D0E';
  ctx.lineWidth = 14;
  ctx.beginPath();
  ctx.moveTo(wallX, groundY);
  ctx.lineTo(wallX, 15);
  ctx.stroke();

  // Right angle indicator
  const sq = 10;
  ctx.strokeStyle = '#94A3B8';
  ctx.lineWidth = 1.5;
  ctx.beginPath();
  ctx.moveTo(wallX - sq, groundY);
  ctx.lineTo(wallX - sq, groundY - sq);
  ctx.lineTo(wallX, groundY - sq);
  ctx.stroke();

  // Ladder
  ctx.strokeStyle = '#00FFF5';
  ctx.lineWidth = 4;
  ctx.lineCap = 'round';
  ctx.beginPath();
  ctx.moveTo(ladderFootX, groundY);
  ctx.lineTo(wallX, ladderTopY);
  ctx.stroke();

  // Rungs
  ctx.strokeStyle = '#F8FAFC';
  ctx.lineWidth = 2;
  for (let i = 1; i <= 7; i++) {
    const frac = i / 8;
    const rx = ladderFootX + (wallX - ladderFootX) * frac;
    const ry = groundY + (ladderTopY - groundY) * frac;
    ctx.beginPath();
    ctx.moveTo(rx - 4, ry + 3);
    ctx.lineTo(rx + 4, ry - 3);
    ctx.stroke();
  }

  // Arc 60°
  ctx.strokeStyle = '#FFB703';
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.arc(ladderFootX, groundY, 26, -Math.PI / 3, 0);
  ctx.stroke();

  // Labels
  ctx.fillStyle = '#FFB703';
  ctx.textAlign = 'left';
  ctx.fillText('60°', ladderFootX + 30, groundY - 8);

  ctx.fillStyle = '#00FFF5';
  ctx.textAlign = 'center';
  ctx.fillText('Tangga = 10 meter', (ladderFootX + wallX) / 2, (groundY + ladderTopY) / 2 - 10);

  ctx.fillStyle = '#38BDF8';
  ctx.fillText('Jarak kaki = 10·cos(60°)', (ladderFootX + wallX) / 2, groundY + 16);

  ctx.fillStyle = '#F472B6';
  ctx.textAlign = 'left';
  ctx.fillText('Tinggi sentuh = 10·sin(60°)', wallX + 10, (groundY + ladderTopY) / 2);
}

// Soal 5: Identitas Trigonometri & Geometri Segitiga Satuan sin²x + cos²x = 1
function drawQuestion5(ctx: CanvasRenderingContext2D, width: number, height: number) {
  const originX = width * 0.35;
  const originY = height * 0.75;
  const r = 65;

  // Axes
  ctx.strokeStyle = '#64748B';
  ctx.lineWidth = 1.5;
  ctx.beginPath();
  ctx.moveTo(originX - 10, originY);
  ctx.lineTo(originX + r + 20, originY);
  ctx.moveTo(originX, originY + 10);
  ctx.lineTo(originX, originY - r - 20);
  ctx.stroke();

  // Unit circle arc
  ctx.strokeStyle = '#334155';
  ctx.setLineDash([5, 5]);
  ctx.beginPath();
  ctx.arc(originX, originY, r, -Math.PI / 2, 0);
  ctx.stroke();
  ctx.setLineDash([]);

  // Angle point
  const rad = (40 * Math.PI) / 180;
  const px = originX + Math.cos(rad) * r;
  const py = originY - Math.sin(rad) * r;

  // Triangle fill
  ctx.fillStyle = 'rgba(0, 173, 181, 0.2)';
  ctx.beginPath();
  ctx.moveTo(originX, originY);
  ctx.lineTo(px, originY);
  ctx.lineTo(px, py);
  ctx.closePath();
  ctx.fill();

  // Hypotenuse = 1
  ctx.strokeStyle = '#00FFF5';
  ctx.lineWidth = 2.5;
  ctx.beginPath();
  ctx.moveTo(originX, originY);
  ctx.lineTo(px, py);
  ctx.stroke();

  // Opposite = sin(x)
  ctx.strokeStyle = '#F472B6';
  ctx.beginPath();
  ctx.moveTo(px, originY);
  ctx.lineTo(px, py);
  ctx.stroke();

  // Adjacent = cos(x)
  ctx.strokeStyle = '#38BDF8';
  ctx.beginPath();
  ctx.moveTo(originX, originY);
  ctx.lineTo(px, originY);
  ctx.stroke();

  // Labels
  ctx.fillStyle = '#00FFF5';
  ctx.textAlign = 'center';
  ctx.fillText('r = 1', (originX + px) / 2 - 10, (originY + py) / 2 - 6);

  ctx.fillStyle = '#38BDF8';
  ctx.fillText('cos(x)', (originX + px) / 2, originY + 14);

  ctx.fillStyle = '#F472B6';
  ctx.textAlign = 'left';
  ctx.fillText('sin(x)', px + 6, (originY + py) / 2);

  // Formulas at right
  ctx.fillStyle = '#FFB703';
  ctx.fillText('Identitas Pythagoras:', width * 0.58, height * 0.32);

  ctx.fillStyle = '#F8FAFC';
  ctx.fillText('sin²(x) + cos²(x) = 1', width * 0.58, height * 0.50);

  ctx.fillStyle = '#CBD5E1';
  ctx.fillText('(sin x + cos x)² = 1 + 2 sin x cos x', width * 0.58, height * 0.68);
}
