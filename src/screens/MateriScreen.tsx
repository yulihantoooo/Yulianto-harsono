import React, { useState } from 'react';
import { MaterialSection } from '../types';
import { LEARNING_MATERIALS } from '../data/materialsData';
import { AppTopBar } from '../components/AppTopBar';
import { BookOpen, Sigma, Lightbulb, ArrowRight } from 'lucide-react';

interface MateriScreenProps {
  onBack: () => void;
  onGoToSimulasi: () => void;
}

export const MateriScreen: React.FC<MateriScreenProps> = ({ onBack, onGoToSimulasi }) => {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const currentMaterial: MaterialSection = LEARNING_MATERIALS[selectedIndex];

  return (
    <div className="min-h-screen bg-[#0A192F] text-white flex flex-col">
      <AppTopBar
        title="Materi Trigonometri"
        subtitle="SMA KELAS X • Yulianto Harsono"
        onBackClick={onBack}
      />

      {/* Horizontal Tabs */}
      <div className="bg-[#0F223D] border-b border-[#1E3A5F] px-4 overflow-x-auto flex gap-2 scrollbar-none py-2 sticky top-[57px] z-20">
        {LEARNING_MATERIALS.map((mat, idx) => {
          const isSelected = selectedIndex === idx;
          return (
            <button
              key={mat.id}
              onClick={() => setSelectedIndex(idx)}
              data-testid={`tab_materi_${idx}`}
              className={`px-4 py-2 rounded-xl text-xs font-bold whitespace-nowrap transition-all ${
                isSelected
                  ? 'bg-[#00FFF5] text-[#0A192F] shadow-md shadow-[#00FFF5]/20'
                  : 'bg-[#1E293B]/70 text-[#94A3B8] hover:text-white hover:bg-[#1E293B]'
              }`}
            >
              Bab {idx + 1}
            </button>
          );
        })}
      </div>

      {/* Content Area */}
      <div className="p-4 sm:p-6 max-w-3xl mx-auto w-full space-y-5 pb-12">
        {/* Header Card */}
        <div className="bg-[#132B45] border border-[#1D426B] rounded-2xl p-5 shadow-lg space-y-2">
          <span className="inline-block px-2.5 py-1 rounded-md text-xs font-bold bg-[#00FFF5]/20 text-[#00FFF5]">
            {currentMaterial.badge}
          </span>
          <h2 className="text-xl sm:text-2xl font-bold text-white">
            {currentMaterial.title}
          </h2>
          <p className="text-xs sm:text-sm text-[#94A3B8]">
            {currentMaterial.subtitle}
          </p>
        </div>

        {/* Theory Card */}
        <div className="bg-[#1E293B] border border-[#334155] rounded-2xl p-5 shadow-lg space-y-3">
          <div className="flex items-center gap-2 text-[#00FFF5] font-bold text-sm sm:text-base border-b border-[#334155] pb-2">
            <BookOpen className="w-5 h-5" />
            <span>Uraian Konsep & Teori</span>
          </div>

          <div className="text-sm text-[#E2E8F0] whitespace-pre-line leading-relaxed">
            {currentMaterial.content}
          </div>
        </div>

        {/* Formulas Card */}
        {currentMaterial.formulas.length > 0 && (
          <div className="bg-[#0C243B] border border-[#00ADB5]/40 rounded-2xl p-5 shadow-lg space-y-3">
            <div className="flex items-center gap-2 text-[#00FFF5] font-bold text-sm sm:text-base border-b border-[#1E3A5F] pb-2">
              <Sigma className="w-5 h-5" />
              <span>Rumus & Identitas Pokok</span>
            </div>

            <div className="grid grid-cols-1 gap-2 pt-1">
              {currentMaterial.formulas.map(([label, formula], i) => (
                <div
                  key={i}
                  className="bg-[#08192A] border border-[#1A3552] rounded-xl px-4 py-2.5 flex flex-col sm:flex-row sm:items-center justify-between gap-1 sm:gap-4"
                >
                  <span className="text-xs sm:text-sm font-mono font-bold text-[#FFB703]">
                    {label}
                  </span>
                  <span className="text-xs sm:text-sm font-semibold text-white">
                    {formula}
                  </span>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Examples Card */}
        {currentMaterial.examples.length > 0 && (
          <div className="bg-[#26200A] border border-[#FFB703]/40 rounded-2xl p-5 shadow-lg space-y-3">
            <div className="flex items-center gap-2 text-[#FFB703] font-bold text-sm sm:text-base border-b border-[#3B2E0A] pb-2">
              <Lightbulb className="w-5 h-5" />
              <span>Contoh Pembahasan Terbimbing</span>
            </div>

            <div className="space-y-3 pt-1">
              {currentMaterial.examples.map(([question, answer], i) => (
                <div
                  key={i}
                  className="bg-[#191404] border border-[#3B2E0A] rounded-xl p-3.5 space-y-2"
                >
                  <div className="text-sm font-semibold text-white">
                    {question}
                  </div>
                  <div className="text-xs sm:text-sm text-[#FDE68A] whitespace-pre-line leading-relaxed font-mono">
                    {answer}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* CTA to Simulation */}
        <div
          onClick={onGoToSimulasi}
          data-testid="btn_materi_cta_simulasi"
          className="bg-gradient-to-r from-[#0E4334] to-[#092920] border border-[#00FFF5] rounded-2xl p-4 sm:p-5 shadow-lg flex items-center justify-between gap-3 cursor-pointer hover:opacity-95 active:scale-[0.99] transition-all"
        >
          <div>
            <h4 className="text-sm sm:text-base font-bold text-white">
              Uji Coba Langsung di Simulasi!
            </h4>
            <p className="text-xs text-[#A7F3D0]">
              Praktikkan rumus tangen pada pohon kelapa interaktif.
            </p>
          </div>
          <div className="w-10 h-10 rounded-full bg-[#00FFF5]/20 flex items-center justify-center text-[#00FFF5] flex-shrink-0">
            <ArrowRight className="w-5 h-5" />
          </div>
        </div>
      </div>
    </div>
  );
};
