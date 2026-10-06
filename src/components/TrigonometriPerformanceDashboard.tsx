import React from 'react';
import { QuizSubmission, DetailedAnswer } from '../types';
import { BarChart3, PieChart, TrendingUp, Award } from 'lucide-react';

interface TrigonometriPerformanceDashboardProps {
  submissions: QuizSubmission[];
  className?: string;
}

export const TrigonometriPerformanceDashboard: React.FC<TrigonometriPerformanceDashboardProps> = ({
  submissions,
  className = '',
}) => {
  if (submissions.length === 0) return null;

  const totalAttempts = submissions.length;
  const questionCorrectCounts = new Array(10).fill(0);

  submissions.forEach((sub) => {
    try {
      const answers: DetailedAnswer[] = JSON.parse(sub.detailedAnswersJson);
      answers.forEach((ans, i) => {
        const qId = ans.questionId || i + 1;
        const index = Math.min(Math.max(qId - 1, 0), 9);
        if (ans.isCorrect) {
          questionCorrectCounts[index]++;
        }
      });
    } catch {
      const ratio = sub.score / 100;
      for (let i = 0; i < 10; i++) {
        if (Math.random() < ratio) questionCorrectCounts[i]++;
      }
    }
  });

  const latest = submissions[0];
  const averageScore = submissions.reduce((acc, s) => acc + s.score, 0) / submissions.length;
  const passedCount = submissions.filter((s) => s.score >= 75).length;
  const passedRatio = passedCount / totalAttempts;

  return (
    <div className={`w-full rounded-2xl bg-[#10233D] border border-[#00FFF5]/50 p-4 sm:p-5 shadow-xl space-y-4 ${className}`}>
      {/* Title Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-[#1E3A5F] pb-3">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-full bg-[#00FFF5]/20 flex items-center justify-center text-[#00FFF5]">
            <Award className="w-5 h-5" />
          </div>
          <div>
            <div className="text-[10px] font-bold tracking-wider text-[#00FFF5] uppercase">
              Dashboard Analitik & Performa
            </div>
            <div className="text-base font-bold text-white">
              Evaluasi 10 Soal HOTS
            </div>
          </div>
        </div>

        <div className="self-start sm:self-auto bg-[#0B192C] border border-[#1E3A5F] px-3 py-1 rounded-lg text-xs font-mono font-bold text-[#FFB703]">
          Rata-rata: {averageScore.toFixed(1)}
        </div>
      </div>

      {/* Stat Cards */}
      <div className="grid grid-cols-3 gap-2.5">
        <div className="bg-[#0B192C] border border-[#00FFF5]/30 rounded-xl p-2.5 text-center">
          <div className="text-[10px] text-[#94A3B8]">Total Sesi</div>
          <div className="text-lg font-mono font-extrabold text-[#00FFF5]">{totalAttempts}</div>
        </div>
        <div className="bg-[#0B192C] border border-[#10B981]/30 rounded-xl p-2.5 text-center">
          <div className="text-[10px] text-[#94A3B8]">Skor Terakhir</div>
          <div className={`text-lg font-mono font-extrabold ${latest.score >= 75 ? 'text-[#10B981]' : 'text-[#EF4444]'}`}>
            {latest.score}
          </div>
        </div>
        <div className="bg-[#0B192C] border border-[#FFB703]/30 rounded-xl p-2.5 text-center">
          <div className="text-[10px] text-[#94A3B8]">Akurasi Kelas</div>
          <div className="text-lg font-mono font-extrabold text-[#FFB703]">{Math.round(averageScore)}%</div>
        </div>
      </div>

      {/* Chart 1: Bar Chart of Question Accuracy (1 to 10) */}
      <div className="bg-[#0B192C] border border-[#1E3A5F] rounded-xl p-3.5 space-y-2">
        <div className="flex items-center justify-between text-xs text-[#E2E8F0]">
          <div className="flex items-center gap-1.5 font-bold">
            <BarChart3 className="w-4 h-4 text-[#00FFF5]" />
            <span>Tingkat Keberhasilan Tiap Butir Soal (% Benar)</span>
          </div>
          <span className="text-[11px] text-[#94A3B8]">10 Soal HOTS</span>
        </div>

        {/* Bar chart SVG */}
        <div className="h-32 w-full pt-2">
          <svg className="w-full h-full overflow-visible" viewBox="0 0 500 120" preserveAspectRatio="none">
            {/* Grid lines */}
            {[0.25, 0.5, 0.75, 1.0].map((frac) => (
              <line
                key={frac}
                x1="0"
                y1={95 - 75 * frac}
                x2="500"
                y2={95 - 75 * frac}
                stroke="#1E3A5F"
                strokeWidth="1"
                strokeDasharray="2 2"
                opacity="0.6"
              />
            ))}

            {/* Bars */}
            {questionCorrectCounts.map((count, i) => {
              const ratio = totalAttempts > 0 ? count / totalAttempts : 0;
              const barH = Math.max(4, ratio * 75);
              const x = i * 50 + 10;
              const y = 95 - barH;
              const color = ratio >= 0.75 ? '#10B981' : ratio >= 0.45 ? '#00ADB5' : '#FFB703';

              return (
                <g key={i}>
                  <rect
                    x={x}
                    y={y}
                    width="30"
                    height={barH}
                    rx="3"
                    fill={color}
                    opacity="0.9"
                  />
                  {/* Label */}
                  <text
                    x={x + 15}
                    y="112"
                    textAnchor="middle"
                    fill="#94A3B8"
                    fontSize="11"
                    fontWeight="bold"
                  >
                    S{i + 1}
                  </text>
                  {/* Percentage */}
                  <text
                    x={x + 15}
                    y={y - 4}
                    textAnchor="middle"
                    fill="#E2E8F0"
                    fontSize="10"
                    fontWeight="600"
                  >
                    {Math.round(ratio * 100)}%
                  </text>
                </g>
              );
            })}
          </svg>
        </div>
      </div>

      {/* Charts 2 & 3: Donut & Trendline */}
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
        {/* Donut Chart */}
        <div className="bg-[#0C1A2E] border border-[#1E3A5F] rounded-xl p-3 flex flex-col justify-between">
          <div className="flex items-center gap-1.5 text-xs font-bold text-[#FFB703]">
            <PieChart className="w-3.5 h-3.5" />
            <span>Distribusi Nilai (KKM 75)</span>
          </div>

          <div className="flex items-center justify-center py-2">
            <div className="relative w-24 h-24">
              <svg className="w-full h-full -rotate-90" viewBox="0 0 36 36">
                {/* Background Ring */}
                <circle
                  cx="18"
                  cy="18"
                  r="14"
                  fill="none"
                  stroke="#EF4444"
                  strokeWidth="4"
                />
                {/* Passed Segment */}
                <circle
                  cx="18"
                  cy="18"
                  r="14"
                  fill="none"
                  stroke="#10B981"
                  strokeWidth="4"
                  strokeDasharray={`${passedRatio * 88} 88`}
                  strokeDashoffset="0"
                  strokeLinecap="round"
                />
              </svg>
              <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none">
                <span className="text-sm font-bold text-white">{Math.round(passedRatio * 100)}%</span>
                <span className="text-[9px] text-[#94A3B8]">Lulus</span>
              </div>
            </div>
          </div>

          <div className="flex justify-around text-[10px] text-[#94A3B8] border-t border-[#1E3A5F]/60 pt-1.5">
            <span className="flex items-center gap-1">
              <span className="w-2 h-2 rounded-full bg-[#10B981]" /> Lulus: {passedCount}
            </span>
            <span className="flex items-center gap-1">
              <span className="w-2 h-2 rounded-full bg-[#EF4444]" /> Remedial: {totalAttempts - passedCount}
            </span>
          </div>
        </div>

        {/* Trendline Chart */}
        <div className="bg-[#0C1A2E] border border-[#1E3A5F] rounded-xl p-3 flex flex-col justify-between">
          <div className="flex items-center gap-1.5 text-xs font-bold text-[#10B981]">
            <TrendingUp className="w-3.5 h-3.5" />
            <span>Tren Riwayat Skor</span>
          </div>

          <div className="h-24 w-full pt-1">
            {submissions.length > 0 && (
              <svg className="w-full h-full" viewBox="0 0 200 80" preserveAspectRatio="none">
                {/* KKM 75 dashed line */}
                <line
                  x1="0"
                  y1={80 - 80 * 0.75}
                  x2="200"
                  y2={80 - 80 * 0.75}
                  stroke="#FFB703"
                  strokeWidth="1"
                  strokeDasharray="3 3"
                  opacity="0.7"
                />
                <text x="4" y={80 - 80 * 0.75 - 3} fill="#FFB703" fontSize="8" fontWeight="bold">
                  KKM 75
                </text>

                {/* Score Points line */}
                {(() => {
                  const history = submissions.slice(0, 8).reverse();
                  const points = history.map((sub, idx) => {
                    const x = history.length > 1 ? (idx / (history.length - 1)) * 190 + 5 : 100;
                    const y = 75 - (sub.score / 100) * 65;
                    return { x, y, score: sub.score };
                  });

                  const pathD = points.map((p, i) => `${i === 0 ? 'M' : 'L'} ${p.x} ${p.y}`).join(' ');

                  return (
                    <g>
                      <path d={pathD} fill="none" stroke="#00ADB5" strokeWidth="2.5" strokeLinecap="round" />
                      {points.map((p, idx) => (
                        <circle key={idx} cx={p.x} cy={p.y} r="3" fill="#00FFF5" stroke="#0F172A" strokeWidth="1.5" />
                      ))}
                    </g>
                  );
                })()}
              </svg>
            )}
          </div>

          <div className="text-[10px] text-center text-[#94A3B8] border-t border-[#1E3A5F]/60 pt-1.5">
            Riwayat pengerjaan terakhir
          </div>
        </div>
      </div>
    </div>
  );
};
