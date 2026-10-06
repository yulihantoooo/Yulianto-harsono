import React, { useState } from 'react';
import { AppTopBar } from '../components/AppTopBar';
import { CoconutTreeSimulationCanvas } from '../components/CoconutTreeSimulationCanvas';
import { Calculator, RotateCcw, ArrowRight, Target } from 'lucide-react';

interface SimulasiScreenProps {
  onBack: () => void;
  onGoToQuiz: () => void;
}

export const SimulasiScreen: React.FC<SimulasiScreenProps> = ({ onBack, onGoToQuiz }) => {
  const [distance, setDistance] = useState(15.0); // meters (5 to 35)
  const [observerHeight, setObserverHeight] = useState(1.6); // meters (1.2 to 2.0)
  const [elevationAngle, setElevationAngle] = useState(45.0); // degrees (15 to 75)

  // Calculations
  const rad = (elevationAngle * Math.PI) / 180;
  const tanValue = Math.tan(rad);
  const triangleOpposite = distance * tanValue;
  const calculatedHeight = observerHeight + triangleOpposite;

  // Mini-game challenge
  const [targetHeight, setTargetHeight] = useState(16.6);
  const [feedback, setFeedback] = useState<string | null>(null);

  const checkChallenge = () => {
    const diff = Math.abs(calculatedHeight - targetHeight);
    if (diff <= 0.35) {
      setFeedback(`Hebat! Kamu berhasil menyetel parameter dengan sangat akurat (Selisih hanya ${diff.toFixed(2)} m)!`);
    } else {
      setFeedback(`Belum tepat, tinggi saat ini ${calculatedHeight.toFixed(2)} m (Target: ${targetHeight.toFixed(1)} m). Coba ubah jarak atau sudut!`);
    }
  };

  const randomizeChallenge = () => {
    const targets = [16.6, 11.6, 18.9, 21.6, 25.0];
    const nextTarget = targets[Math.floor(Math.random() * targets.length)];
    setTargetHeight(nextTarget);
    setFeedback(null);
  };

  return (
    <div className="min-h-screen bg-[#0A192F] text-white flex flex-col">
      <AppTopBar
        title="Simulasi Menghitung Pohon Kelapa"
        subtitle="Eksplorasi Klinometer • Yulianto Harsono"
        onBackClick={onBack}
      />

      <div className="p-4 sm:p-6 max-w-3xl mx-auto w-full space-y-5 pb-12">
        {/* Canvas */}
        <CoconutTreeSimulationCanvas
          distanceMeters={distance}
          observerHeightMeters={observerHeight}
          elevationAngleDegrees={elevationAngle}
          calculatedTreeHeight={calculatedHeight}
        />

        {/* Realtime Result Banner */}
        <div className="bg-[#0F2C47] border-2 border-[#00FFF5] rounded-2xl p-4 sm:p-5 shadow-xl space-y-3">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-1.5 border-b border-[#1E3A5F] pb-2">
            <span className="text-xs font-bold text-[#00FFF5] tracking-wider uppercase">
              Hasil Perhitungan Trigonometri
            </span>
            <span className="self-start sm:self-auto bg-[#FFB703]/20 text-[#FFB703] border border-[#FFB703]/40 px-2 py-0.5 rounded text-[11px] font-mono font-bold">
              Rumus: T = h + d·tan(θ)
            </span>
          </div>

          <div className="flex flex-col sm:flex-row sm:items-end justify-between gap-2">
            <div>
              <div className="text-xs text-[#94A3B8]">Tinggi Total Pohon Kelapa:</div>
              <div className="text-2xl sm:text-3xl font-extrabold text-white font-mono tracking-tight">
                {calculatedHeight.toFixed(2)} meter
              </div>
            </div>

            <div className="text-xs font-mono text-right sm:text-right space-y-0.5">
              <div className="text-[#00FFF5]">
                tan({Math.round(elevationAngle)}°) = {tanValue.toFixed(4)}
              </div>
              <div className="text-[#FF70A6]">
                y = {triangleOpposite.toFixed(2)} m
              </div>
            </div>
          </div>

          <div className="text-xs text-[#CBD5E1] bg-[#0B192C]/80 p-2.5 rounded-lg border border-[#1E3A5F] font-mono">
            Penjabaran: {observerHeight.toFixed(1)} m (pengamat) + ({distance.toFixed(1)} m × {tanValue.toFixed(4)}) = <span className="text-[#00FFF5] font-bold">{calculatedHeight.toFixed(2)} m</span>
          </div>
        </div>

        {/* Slider Controls */}
        <div className="bg-[#1E293B] border border-[#334155] rounded-2xl p-5 shadow-xl space-y-5">
          <div className="border-b border-[#334155] pb-2">
            <h3 className="text-base font-bold text-white">Panel Kontrol Parameter Simulasi</h3>
            <p className="text-xs text-[#94A3B8]">
              Geser slider di bawah ini untuk melihat perubahan geometris secara real-time
            </p>
          </div>

          {/* Slider 1: Sudut */}
          <div className="space-y-1.5">
            <div className="flex justify-between items-center text-sm">
              <span className="font-semibold text-[#FFB703]">1. Sudut Elevasi (θ)</span>
              <span className="font-mono font-bold text-[#FFB703] text-base">
                {Math.round(elevationAngle)}°
              </span>
            </div>
            <input
              type="range"
              min="15"
              max="75"
              step="1"
              value={elevationAngle}
              onChange={(e) => {
                setElevationAngle(parseFloat(e.target.value));
                setFeedback(null);
              }}
              data-testid="slider_elevation_angle"
              className="w-full accent-[#FFB703] cursor-pointer"
            />
          </div>

          {/* Slider 2: Jarak */}
          <div className="space-y-1.5">
            <div className="flex justify-between items-center text-sm">
              <span className="font-semibold text-[#00FFF5]">2. Jarak ke Pohon (d)</span>
              <span className="font-mono font-bold text-[#00FFF5] text-base">
                {distance.toFixed(1)} m
              </span>
            </div>
            <input
              type="range"
              min="5"
              max="35"
              step="0.5"
              value={distance}
              onChange={(e) => {
                setDistance(parseFloat(e.target.value));
                setFeedback(null);
              }}
              data-testid="slider_distance"
              className="w-full accent-[#00FFF5] cursor-pointer"
            />
          </div>

          {/* Slider 3: Tinggi Pengamat */}
          <div className="space-y-1.5">
            <div className="flex justify-between items-center text-sm">
              <span className="font-semibold text-[#10B981]">3. Tinggi Pengamat (t)</span>
              <span className="font-mono font-bold text-[#10B981] text-base">
                {observerHeight.toFixed(2)} m
              </span>
            </div>
            <input
              type="range"
              min="1.2"
              max="2.0"
              step="0.05"
              value={observerHeight}
              onChange={(e) => {
                setObserverHeight(parseFloat(e.target.value));
                setFeedback(null);
              }}
              data-testid="slider_observer_height"
              className="w-full accent-[#10B981] cursor-pointer"
            />
          </div>
        </div>

        {/* Preset Buttons */}
        <div className="bg-[#132238] border border-[#1F3350] rounded-2xl p-4 space-y-2.5">
          <div className="text-xs font-bold text-[#94A3B8] uppercase tracking-wider">
            Preset Sudut Istimewa Lapangan:
          </div>
          <div className="grid grid-cols-3 gap-2">
            <button
              onClick={() => {
                setElevationAngle(30);
                setDistance(15);
              }}
              data-testid="preset_30_deg"
              className="px-3 py-2 bg-[#0B192C] hover:bg-[#1E3A5F] border border-[#334155] rounded-xl text-xs font-semibold text-white transition-all cursor-pointer"
            >
              30° (1/√3)
            </button>
            <button
              onClick={() => {
                setElevationAngle(45);
                setDistance(15);
              }}
              data-testid="preset_45_deg"
              className="px-3 py-2 bg-[#0B192C] hover:bg-[#1E3A5F] border border-[#334155] rounded-xl text-xs font-semibold text-white transition-all cursor-pointer"
            >
              45° (tan=1)
            </button>
            <button
              onClick={() => {
                setElevationAngle(60);
                setDistance(10);
              }}
              data-testid="preset_60_deg"
              className="px-3 py-2 bg-[#0B192C] hover:bg-[#1E3A5F] border border-[#334155] rounded-xl text-xs font-semibold text-white transition-all cursor-pointer"
            >
              60° (√3)
            </button>
          </div>
        </div>

        {/* Mini Game Challenge */}
        <div className="bg-[#1C1905] border border-[#FFB703]/50 rounded-2xl p-4 sm:p-5 shadow-lg space-y-3">
          <div className="flex items-center gap-2 text-[#FFB703] font-bold text-sm sm:text-base">
            <Target className="w-5 h-5" />
            <span>Misi Tantangan Interaktif</span>
          </div>

          <p className="text-xs sm:text-sm text-[#FEF08A] leading-relaxed">
            Tantangan: Sesuaikan slider hingga Tinggi Pohon Kelapa mencapai sekitar <strong className="text-white underline">{targetHeight.toFixed(1)} meter</strong> (toleransi ±0,35 m)!
          </p>

          <div className="flex gap-2 pt-1">
            <button
              onClick={checkChallenge}
              data-testid="btn_verify_challenge"
              className="flex-1 bg-[#FFB703] hover:bg-[#F59E0B] text-black font-bold py-2.5 rounded-xl text-xs sm:text-sm shadow-md transition-all cursor-pointer"
            >
              Periksa Jawaban
            </button>
            <button
              onClick={randomizeChallenge}
              data-testid="btn_random_challenge"
              className="px-3 py-2.5 bg-[#0B192C] border border-[#FFB703]/50 hover:bg-[#1E3A5F] text-[#FFB703] rounded-xl text-xs font-semibold flex items-center justify-center transition-all cursor-pointer"
              title="Acak Target Tantangan"
            >
              <RotateCcw className="w-4 h-4" />
            </button>
          </div>

          {feedback && (
            <div
              className={`p-3 rounded-xl text-xs sm:text-sm font-medium border ${
                feedback.startsWith('Hebat')
                  ? 'bg-[#064E3B] text-emerald-100 border-[#10B981]'
                  : 'bg-[#451A03] text-amber-100 border-[#F59E0B]'
              }`}
            >
              {feedback}
            </div>
          )}
        </div>

        {/* CTA to Quiz */}
        <button
          onClick={onGoToQuiz}
          data-testid="btn_simulasi_to_quiz"
          className="w-full bg-gradient-to-r from-[#00FFF5] to-[#00ADB5] hover:opacity-95 text-[#0A192F] font-bold py-3.5 rounded-xl shadow-lg shadow-[#00FFF5]/20 flex items-center justify-center gap-2 transition-all cursor-pointer"
        >
          <span>Lanjut Kerjakan 10 Soal HOTS</span>
          <ArrowRight className="w-5 h-5" />
        </button>
      </div>
    </div>
  );
};
