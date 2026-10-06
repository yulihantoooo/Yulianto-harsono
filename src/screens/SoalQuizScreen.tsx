import React, { useState } from 'react';
import { UserSession, Question } from '../types';
import { QUESTIONS_DATA } from '../data/questionsData';
import { AppTopBar } from '../components/AppTopBar';
import { QuestionIllustration } from '../components/QuestionIllustration';
import { ArrowLeft, ArrowRight, CheckCircle2, Send, AlertTriangle } from 'lucide-react';

interface SoalQuizScreenProps {
  session: UserSession;
  onBack: () => void;
  onSubmitQuiz: (selectedAnswers: Record<number, number>) => void;
}

export const SoalQuizScreen: React.FC<SoalQuizScreenProps> = ({
  session,
  onBack,
  onSubmitQuiz,
}) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [selectedAnswers, setSelectedAnswers] = useState<Record<number, number>>({});
  const [showSubmitModal, setShowSubmitModal] = useState(false);

  const currentQuestion: Question = QUESTIONS_DATA[currentIndex];
  const chosenOption = selectedAnswers[currentIndex];
  const answeredCount = Object.keys(selectedAnswers).length;
  const totalCount = QUESTIONS_DATA.length;

  const handleSelectOption = (optIndex: number) => {
    setSelectedAnswers((prev) => ({
      ...prev,
      [currentIndex]: optIndex,
    }));
  };

  const handleConfirmSubmit = () => {
    setShowSubmitModal(false);
    onSubmitQuiz(selectedAnswers);
  };

  return (
    <div className="min-h-screen bg-[#0A192F] text-white flex flex-col justify-between">
      <div>
        <AppTopBar
          title="Soal HOTS Trigonometri"
          subtitle={`Nomor ${currentIndex + 1} dari ${totalCount} • Yulianto Harsono`}
          onBackClick={onBack}
        />

        {/* Question Selector Chips */}
        <div className="bg-[#0F223D] border-b border-[#1E3A5F] px-4 py-2.5 flex items-center gap-2 overflow-x-auto scrollbar-none sticky top-[57px] z-20">
          {QUESTIONS_DATA.map((_, idx) => {
            const isCurrent = idx === currentIndex;
            const isAnswered = selectedAnswers[idx] !== undefined;

            return (
              <button
                key={idx}
                onClick={() => setCurrentIndex(idx)}
                data-testid={`nav_question_chip_${idx}`}
                className={`w-9 h-9 rounded-full flex items-center justify-center text-xs font-bold transition-all flex-shrink-0 cursor-pointer ${
                  isCurrent
                    ? 'bg-[#00FFF5] text-[#0A192F] ring-2 ring-white shadow-md shadow-[#00FFF5]/30'
                    : isAnswered
                    ? 'bg-[#00ADB5]/40 text-white border border-[#00FFF5]'
                    : 'bg-[#1E293B] text-[#94A3B8] border border-[#334155] hover:border-[#64748B]'
                }`}
              >
                {idx + 1}
              </button>
            );
          })}
        </div>

        {/* Question Body */}
        <div className="p-4 sm:p-6 max-w-3xl mx-auto w-full space-y-4 pb-24">
          {/* Main Question Card */}
          <div className="bg-[#132B45] border border-[#1D426B] rounded-2xl p-5 shadow-lg space-y-3.5">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-1 border-b border-[#1E3A5F] pb-2.5">
              <span className="self-start px-2.5 py-0.5 rounded-md text-[11px] font-bold bg-[#FFB703]/20 text-[#FFB703] border border-[#FFB703]/30">
                HOTS: {currentQuestion.hotsCategory}
              </span>
              <span className="text-xs text-[#94A3B8]">Bobot: 10 Poin</span>
            </div>

            <h3 className="text-base sm:text-lg font-bold text-white">
              {currentQuestion.title}
            </h3>

            {/* Context Story */}
            <div className="bg-[#0C1A2E] border border-[#193254] rounded-xl p-3.5 text-xs sm:text-sm text-[#E2E8F0] leading-relaxed">
              {currentQuestion.contextStory}
            </div>

            {/* Illustration Canvas for Questions 1-5 */}
            {currentQuestion.id >= 1 && currentQuestion.id <= 5 && (
              <div className="space-y-1.5 pt-1">
                <div className="text-[11px] font-bold text-[#FFB703]">
                  Gambar Diagram & Sketsa Geometris:
                </div>
                <QuestionIllustration questionId={currentQuestion.id} />
              </div>
            )}

            {/* Core Question Sentence */}
            <p className="text-sm sm:text-base font-semibold text-[#00FFF5] pt-1 leading-snug">
              {currentQuestion.questionText}
            </p>
          </div>

          {/* Options */}
          <div className="space-y-2.5 pt-1">
            <div className="text-xs text-[#94A3B8]">
              Pilih salah satu jawaban yang paling tepat:
            </div>

            {currentQuestion.options.map((optText, optIdx) => {
              const isSelected = chosenOption === optIdx;
              const optionLetter = String.fromCharCode(65 + optIdx); // A, B, C, D, E

              return (
                <div
                  key={optIdx}
                  onClick={() => handleSelectOption(optIdx)}
                  data-testid={`option_${currentIndex}_${optIdx}`}
                  className={`rounded-xl p-3.5 sm:p-4 border transition-all cursor-pointer flex items-center gap-3.5 ${
                    isSelected
                      ? 'bg-[#0F3A57] border-[#00FFF5] shadow-lg shadow-[#00FFF5]/10'
                      : 'bg-[#1E293B] border-[#334155] hover:border-[#64748B]'
                  }`}
                >
                  <div
                    className={`w-8 h-8 rounded-full flex items-center justify-center font-bold text-xs flex-shrink-0 transition-colors ${
                      isSelected
                        ? 'bg-[#00FFF5] text-[#0A192F]'
                        : 'bg-[#0F172A] text-white border border-[#475569]'
                    }`}
                  >
                    {optionLetter}
                  </div>

                  <span
                    className={`text-xs sm:text-sm flex-1 leading-relaxed ${
                      isSelected ? 'font-semibold text-white' : 'text-[#CBD5E1]'
                    }`}
                  >
                    {optText}
                  </span>

                  {isSelected && (
                    <CheckCircle2 className="w-5 h-5 text-[#00FFF5] flex-shrink-0" />
                  )}
                </div>
              );
            })}
          </div>
        </div>
      </div>

      {/* Bottom Sticky Action Bar */}
      <div className="fixed bottom-0 inset-x-0 bg-[#0F223D]/95 backdrop-blur-md border-t border-[#1E3A5F] p-3 sm:p-4 z-30">
        <div className="max-w-3xl mx-auto flex items-center justify-between gap-3">
          <button
            onClick={() => setCurrentIndex((prev) => Math.max(0, prev - 1))}
            disabled={currentIndex === 0}
            data-testid="btn_prev_question"
            className="px-4 py-2.5 rounded-xl border border-[#334155] text-xs sm:text-sm font-semibold text-white hover:bg-[#1E293B] disabled:opacity-40 disabled:cursor-not-allowed flex items-center gap-1.5 transition-all cursor-pointer"
          >
            <ArrowLeft className="w-4 h-4" />
            <span>Sebelumnya</span>
          </button>

          <div className="text-xs font-bold text-center">
            <span
              className={
                answeredCount === totalCount ? 'text-[#10B981]' : 'text-[#94A3B8]'
              }
            >
              {answeredCount}/{totalCount} Terjawab
            </span>
          </div>

          {currentIndex < totalCount - 1 ? (
            <button
              onClick={() => setCurrentIndex((prev) => Math.min(totalCount - 1, prev + 1))}
              data-testid="btn_next_question"
              className="px-4 py-2.5 rounded-xl bg-[#00FFF5] hover:bg-[#00ADB5] text-[#0A192F] text-xs sm:text-sm font-bold flex items-center gap-1.5 transition-all shadow-md cursor-pointer"
            >
              <span>Selanjutnya</span>
              <ArrowRight className="w-4 h-4" />
            </button>
          ) : (
            <button
              onClick={() => setShowSubmitModal(true)}
              data-testid="btn_finish_quiz"
              className="px-4 py-2.5 rounded-xl bg-[#FFB703] hover:bg-[#F59E0B] text-black text-xs sm:text-sm font-bold flex items-center gap-1.5 transition-all shadow-md cursor-pointer"
            >
              <Send className="w-4 h-4" />
              <span>Selesai & Rekam</span>
            </button>
          )}
        </div>
      </div>

      {/* Submit Confirmation Modal */}
      {showSubmitModal && (
        <div className="fixed inset-0 z-50 bg-black/75 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-[#132B45] border border-[#1E3A5F] rounded-2xl p-5 sm:p-6 max-w-md w-full shadow-2xl space-y-4 animate-in fade-in zoom-in-95 duration-150">
            <h3 className="text-lg font-bold text-white border-b border-[#1E3A5F] pb-2">
              Kirim & Rekam Jawaban?
            </h3>

            <div className="text-xs sm:text-sm text-[#CBD5E1] space-y-2 leading-relaxed">
              <p>
                Siswa:{' '}
                <strong className="text-white">
                  {session.studentName} ({session.studentClass})
                </strong>
              </p>
              <p>
                Total terjawab:{' '}
                <strong className="text-[#00FFF5]">
                  {answeredCount} dari {totalCount} soal
                </strong>
                .
              </p>

              {answeredCount < totalCount && (
                <div className="flex items-center gap-2 p-2.5 rounded-lg bg-[#EF4444]/15 border border-[#EF4444]/40 text-[#EF4444] text-xs font-semibold">
                  <AlertTriangle className="w-4 h-4 flex-shrink-0" />
                  <span>Peringatan: Masih ada {totalCount - answeredCount} soal belum dijawab!</span>
                </div>
              )}

              <p className="text-xs text-[#94A3B8] pt-1">
                Hasil nilai dan rincian jawaban akan terekam otomatis ke Data Sheet evaluasi Bapak Yulianto Harsono.
              </p>
            </div>

            <div className="flex items-center justify-end gap-2.5 pt-2">
              <button
                onClick={() => setShowSubmitModal(false)}
                className="px-4 py-2 rounded-xl border border-[#334155] text-xs font-semibold text-[#CBD5E1] hover:bg-[#1E3A5F] transition-all cursor-pointer"
              >
                Periksa Kembali
              </button>
              <button
                onClick={handleConfirmSubmit}
                data-testid="btn_confirm_submit_quiz"
                className="px-4 py-2 rounded-xl bg-[#00FFF5] hover:bg-[#00ADB5] text-[#0A192F] text-xs font-bold shadow-md transition-all cursor-pointer"
              >
                Ya, Rekam Sekarang
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
