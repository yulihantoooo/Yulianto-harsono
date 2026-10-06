import React from 'react';
import { UserSession, QuizSubmission } from '../types';
import { QUESTIONS_DATA } from '../data/questionsData';
import { AppTopBar } from '../components/AppTopBar';
import { TrigonometriPerformanceDashboard } from '../components/TrigonometriPerformanceDashboard';
import { QuestionIllustration } from '../components/QuestionIllustration';
import { 
  Table2, 
  RotateCcw, 
  Home, 
  CheckCircle2, 
  XCircle, 
  Award 
} from 'lucide-react';

interface QuizResultScreenProps {
  session: UserSession;
  latestScore: number;
  latestCorrectCount: number;
  selectedAnswers: Record<number, number>;
  submissions: QuizSubmission[];
  onBackToHome: () => void;
  onGoToDataSheet: () => void;
  onRetryQuiz: () => void;
}

export const QuizResultScreen: React.FC<QuizResultScreenProps> = ({
  session,
  latestScore,
  latestCorrectCount,
  selectedAnswers,
  submissions,
  onBackToHome,
  onGoToDataSheet,
  onRetryQuiz,
}) => {
  const totalCount = QUESTIONS_DATA.length;
  const isPassed = latestScore >= 75;

  return (
    <div className="min-h-screen bg-[#0A192F] text-white flex flex-col pb-12">
      <AppTopBar
        title="Hasil Evaluasi Kuis"
        subtitle="Terekam di Data Sheet • Yulianto Harsono"
        onBackClick={onBackToHome}
      />

      <div className="p-4 sm:p-6 max-w-3xl mx-auto w-full space-y-6">
        {/* Big Score Card */}
        <div
          className={`rounded-3xl p-6 text-center border-2 shadow-2xl transition-all ${
            isPassed
              ? 'bg-gradient-to-b from-[#0F3A2B] to-[#0A241B] border-[#10B981]'
              : 'bg-gradient-to-b from-[#3B1515] to-[#250D0D] border-[#EF4444]'
          }`}
        >
          <span
            className={`inline-block px-4 py-1 rounded-full text-xs font-bold uppercase tracking-wider mb-3 ${
              isPassed
                ? 'bg-[#10B981]/20 text-[#10B981] border border-[#10B981]/40'
                : 'bg-[#EF4444]/20 text-[#EF4444] border border-[#EF4444]/40'
            }`}
          >
            {isPassed ? 'LULUS KOMPETENSI (KKM ≥ 75)' : 'PERLU REMEDIAL & PENGAYAAN'}
          </span>

          <div className="text-6xl sm:text-7xl font-extrabold text-white font-mono tracking-tight my-2">
            {latestScore}
          </div>

          <p className="text-xs sm:text-sm text-[#CBD5E1] font-medium">
            Skor Akhir dari 100
          </p>

          <div className="flex items-center justify-center gap-4 text-xs sm:text-sm font-bold mt-4 pt-3 border-t border-white/10">
            <span className="text-[#10B981] flex items-center gap-1">
              <CheckCircle2 className="w-4 h-4" /> Benar: {latestCorrectCount} soal
            </span>
            <span className="text-[#64748B]">•</span>
            <span className="text-[#EF4444] flex items-center gap-1">
              <XCircle className="w-4 h-4" /> Salah: {totalCount - latestCorrectCount} soal
            </span>
          </div>

          <p className="text-[11px] text-[#94A3B8] mt-3">
            Tercatat untuk siswa: {session.studentName} ({session.studentClass} / Absen {session.studentAbsentNumber})
          </p>
        </div>

        {/* Action Buttons */}
        <div className="grid grid-cols-2 gap-3">
          <button
            onClick={onGoToDataSheet}
            data-testid="btn_result_to_datasheet"
            className="flex items-center justify-center gap-2 py-3 px-4 bg-[#00FFF5] hover:bg-[#00ADB5] text-[#0A192F] font-bold rounded-xl text-xs sm:text-sm shadow-md transition-all cursor-pointer"
          >
            <Table2 className="w-4 h-4" />
            <span>Lihat Data Sheet</span>
          </button>

          <button
            onClick={onRetryQuiz}
            data-testid="btn_result_retry"
            className="flex items-center justify-center gap-2 py-3 px-4 bg-[#1E293B] hover:bg-[#334155] border border-[#475569] text-white font-semibold rounded-xl text-xs sm:text-sm transition-all cursor-pointer"
          >
            <RotateCcw className="w-4 h-4" />
            <span>Ulangi Kuis</span>
          </button>
        </div>

        {/* Visual Analytics Dashboard */}
        {submissions.length > 0 && (
          <TrigonometriPerformanceDashboard submissions={submissions} />
        )}

        {/* Detailed Review Section */}
        <div className="space-y-4 pt-2">
          <div className="flex items-center gap-2 text-[#00FFF5] font-bold text-xs sm:text-sm uppercase tracking-wider">
            <Award className="w-4 h-4" />
            <span>Pembahasan Lengkap 10 Soal HOTS</span>
          </div>

          <div className="space-y-4">
            {QUESTIONS_DATA.map((q, idx) => {
              const chosen = selectedAnswers[idx];
              const isCorrect = chosen === q.correctIndex;
              const chosenText = chosen !== undefined ? q.options[chosen] : 'Tidak dijawab';

              return (
                <div
                  key={q.id}
                  className={`rounded-2xl p-4 sm:p-5 border bg-[#1E293B] shadow-lg space-y-3 ${
                    isCorrect ? 'border-[#10B981]/50' : 'border-[#EF4444]/50'
                  }`}
                >
                  <div className="flex items-center justify-between border-b border-[#334155] pb-2">
                    <span className="font-bold text-sm text-white">Soal {idx + 1}</span>

                    <span
                      className={`inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-[11px] font-bold ${
                        isCorrect
                          ? 'bg-[#10B981]/20 text-[#10B981] border border-[#10B981]/30'
                          : 'bg-[#EF4444]/20 text-[#EF4444] border border-[#EF4444]/30'
                      }`}
                    >
                      {isCorrect ? (
                        <>
                          <CheckCircle2 className="w-3.5 h-3.5" /> BENAR (+10)
                        </>
                      ) : (
                        <>
                          <XCircle className="w-3.5 h-3.5" /> SALAH (0)
                        </>
                      )}
                    </span>
                  </div>

                  <p className="text-xs sm:text-sm text-[#E2E8F0] font-semibold leading-relaxed">
                    {q.questionText}
                  </p>

                  {/* Geometric Illustration Diagram for Q1-5 */}
                  {q.id >= 1 && q.id <= 5 && (
                    <div className="pt-1">
                      <QuestionIllustration questionId={q.id} />
                    </div>
                  )}

                  {/* Student Answer */}
                  <div className="text-xs space-y-1 pt-1">
                    <div className={isCorrect ? 'text-[#10B981]' : 'text-[#EF4444]'}>
                      <span className="font-semibold">Jawaban Kamu:</span> {chosenText}
                    </div>

                    {!isCorrect && (
                      <div className="text-[#00FFF5] font-semibold">
                        Kunci Jawaban Benar: {q.options[q.correctIndex]}
                      </div>
                    )}
                  </div>

                  {/* Explanation */}
                  <div className="bg-[#0F1A2A] border border-[#1E3A5F] rounded-xl p-3 text-xs space-y-1">
                    <div className="font-bold text-[#FFB703]">Pembahasan Konsep:</div>
                    <div className="text-[#CBD5E1] leading-relaxed whitespace-pre-line font-mono text-[11px] sm:text-xs">
                      {q.explanation}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        {/* Back to Home Button */}
        <button
          onClick={onBackToHome}
          data-testid="btn_result_back_home"
          className="w-full py-3.5 bg-[#334155] hover:bg-[#475569] text-white font-bold rounded-xl flex items-center justify-center gap-2 transition-all cursor-pointer shadow-md"
        >
          <Home className="w-4 h-4" />
          <span>Kembali ke Menu Utama</span>
        </button>
      </div>
    </div>
  );
};
