import React, { useState } from 'react';
import { QuizSubmission } from '../types';
import { AppTopBar } from '../components/AppTopBar';
import { TrigonometriPerformanceDashboard } from '../components/TrigonometriPerformanceDashboard';
import { StorageService } from '../services/storage';
import { 
  FileDown, 
  Trash2, 
  Share2, 
  Table2, 
  AlertCircle 
} from 'lucide-react';

interface DataSheetScreenProps {
  submissions: QuizSubmission[];
  onBack: () => void;
  onDeleteSubmission: (id: number) => void;
  onClearAllSubmissions: () => void;
}

export const DataSheetScreen: React.FC<DataSheetScreenProps> = ({
  submissions,
  onBack,
  onDeleteSubmission,
  onClearAllSubmissions,
}) => {
  const [showClearModal, setShowClearModal] = useState(false);

  const handleExportCsv = () => {
    if (submissions.length === 0) return;
    const csvContent = StorageService.exportToCsv(submissions);
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `data_sheet_trigonometri_${Date.now()}.csv`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  const handleShareOrCopy = () => {
    if (submissions.length === 0) return;
    const csvContent = StorageService.exportToCsv(submissions);
    if (navigator.share) {
      navigator.share({
        title: 'Data Sheet Nilai Trigonometri - Yulianto Harsono',
        text: csvContent,
      }).catch(() => {});
    } else {
      navigator.clipboard.writeText(csvContent).then(() => {
        alert('Data Sheet CSV berhasil disalin ke clipboard!');
      });
    }
  };

  return (
    <div className="min-h-screen bg-[#0A192F] text-white flex flex-col pb-12">
      <AppTopBar
        title="Data Sheet Nilai Siswa"
        subtitle="Rekapitulasi Evaluasi • Yulianto Harsono"
        onBackClick={onBack}
        actions={
          submissions.length > 0 && (
            <button
              onClick={handleShareOrCopy}
              data-testid="btn_share_datasheet"
              className="p-2 rounded-xl text-[#00FFF5] hover:bg-[#1E3A5F] active:scale-95 transition-all"
              title="Ekspor / Bagikan Data Sheet"
            >
              <Share2 className="w-5 h-5" />
            </button>
          )
        }
      />

      <div className="p-4 sm:p-6 max-w-4xl mx-auto w-full space-y-5">
        {/* Header Summary Card */}
        <div className="bg-[#132B45] border border-[#1F3F66] rounded-2xl p-4 sm:p-5 shadow-lg space-y-3">
          <div className="flex items-center justify-between border-b border-[#1E3A5F] pb-2.5">
            <span className="text-[11px] font-bold tracking-wider text-[#00FFF5] uppercase">
              Lembar Data Penilaian (Data Sheet)
            </span>
            <span className="px-2.5 py-0.5 rounded-full text-xs font-bold bg-[#FFB703]/20 text-[#FFB703] border border-[#FFB703]/40">
              {submissions.length} Rekaman
            </span>
          </div>

          <div>
            <h2 className="text-base sm:text-lg font-bold text-white">
              Guru Pengampu: YULIANTO HARSONO
            </h2>
            <p className="text-xs text-[#94A3B8]">
              Mata Pelajaran Matematika SMA Kelas X - Materi Trigonometri
            </p>
          </div>

          <div className="flex gap-2.5 pt-1">
            <button
              onClick={handleExportCsv}
              disabled={submissions.length === 0}
              data-testid="btn_export_csv"
              className="flex-1 py-2.5 px-3 bg-[#00ADB5]/20 hover:bg-[#00ADB5]/30 border border-[#00FFF5] text-[#00FFF5] rounded-xl text-xs sm:text-sm font-bold flex items-center justify-center gap-1.5 disabled:opacity-40 disabled:cursor-not-allowed transition-all cursor-pointer"
            >
              <FileDown className="w-4 h-4" />
              <span>Ekspor CSV</span>
            </button>

            <button
              onClick={() => setShowClearModal(true)}
              disabled={submissions.length === 0}
              data-testid="btn_clear_datasheet"
              className="py-2.5 px-3.5 bg-[#EF4444]/15 hover:bg-[#EF4444]/25 border border-[#EF4444]/50 text-[#EF4444] rounded-xl text-xs sm:text-sm font-bold flex items-center justify-center disabled:opacity-40 disabled:cursor-not-allowed transition-all cursor-pointer"
              title="Hapus Semua Rekaman"
            >
              <Trash2 className="w-4 h-4" />
            </button>
          </div>
        </div>

        {/* Analytics Dashboard */}
        {submissions.length > 0 && (
          <TrigonometriPerformanceDashboard submissions={submissions} />
        )}

        {/* Table / List View */}
        <div className="space-y-3">
          {submissions.length > 0 && (
            <div className="hidden sm:grid grid-cols-12 bg-[#0F223D] border border-[#1E3A5F] rounded-xl px-4 py-2.5 text-xs font-bold text-[#94A3B8]">
              <div className="col-span-4">No / Siswa</div>
              <div className="col-span-3">Kelas / Absen</div>
              <div className="col-span-3 text-center">Nilai</div>
              <div className="col-span-2 text-right">Aksi</div>
            </div>
          )}

          {submissions.length === 0 ? (
            <div className="bg-[#1E293B] border border-[#334155] rounded-2xl p-8 sm:p-12 text-center space-y-3">
              <Table2 className="w-12 h-12 text-[#64748B] mx-auto" />
              <h3 className="text-base font-bold text-white">
                Belum Ada Data Rekaman Kuis
              </h3>
              <p className="text-xs text-[#94A3B8] max-w-md mx-auto leading-relaxed">
                Kerjakan 10 Soal HOTS pada menu kuis, maka hasil pengerjaan siswa akan otomatis terekam secara persisten di data sheet ini.
              </p>
            </div>
          ) : (
            <div className="space-y-2.5">
              {submissions.map((sub) => {
                const isPassed = sub.score >= 75;
                const formattedDate = new Date(sub.completedAt).toLocaleString('id-ID', {
                  day: '2-digit',
                  month: '2-digit',
                  year: '2-digit',
                  hour: '2-digit',
                  minute: '2-digit',
                });

                return (
                  <div
                    key={sub.id}
                    className="bg-[#1E293B] border border-[#334155] hover:border-[#64748B] rounded-xl p-3.5 sm:p-4 transition-all flex flex-col sm:grid sm:grid-cols-12 items-start sm:items-center gap-3 sm:gap-2"
                  >
                    {/* Student & Date */}
                    <div className="col-span-4 flex-1">
                      <div className="text-sm font-bold text-white truncate">
                        {sub.studentName}
                      </div>
                      <div className="text-[11px] text-[#94A3B8]">
                        {formattedDate}
                      </div>
                    </div>

                    {/* Class & Absent */}
                    <div className="col-span-3">
                      <div className="text-xs font-semibold text-[#00FFF5]">
                        {sub.studentClass}
                      </div>
                      <div className="text-[11px] text-[#CBD5E1]">
                        No. Absen: {sub.studentAbsentNumber}
                      </div>
                    </div>

                    {/* Score */}
                    <div className="col-span-3 self-center sm:self-auto text-center">
                      <span
                        className={`inline-block px-3 py-1 rounded-lg text-sm font-mono font-extrabold border ${
                          isPassed
                            ? 'bg-[#10B981]/20 text-[#10B981] border-[#10B981]'
                            : 'bg-[#EF4444]/20 text-[#EF4444] border-[#EF4444]'
                        }`}
                      >
                        {sub.score}
                      </span>
                      <div className="text-[10px] text-[#94A3B8] mt-0.5">
                        {sub.correctAnswersCount}/10 Benar
                      </div>
                    </div>

                    {/* Action */}
                    <div className="col-span-2 self-end sm:self-auto text-right">
                      <button
                        onClick={() => onDeleteSubmission(sub.id)}
                        data-testid={`btn_delete_sub_${sub.id}`}
                        className="p-2 text-[#EF4444]/80 hover:text-[#EF4444] hover:bg-[#EF4444]/10 rounded-lg transition-colors cursor-pointer"
                        title="Hapus Rekaman"
                      >
                        <Trash2 className="w-4 h-4" />
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>
          )}
        </div>
      </div>

      {/* Clear Confirmation Modal */}
      {showClearModal && (
        <div className="fixed inset-0 z-50 bg-black/75 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-[#132B45] border border-[#1E3A5F] rounded-2xl p-5 sm:p-6 max-w-sm w-full shadow-2xl space-y-4">
            <div className="flex items-center gap-2 text-[#EF4444] font-bold">
              <AlertCircle className="w-5 h-5" />
              <span>Hapus Semua Data Sheet?</span>
            </div>

            <p className="text-xs text-[#CBD5E1] leading-relaxed">
              Seluruh riwayat nilai kuis yang tersimpan akan dihapus permanen dari basis data peramban.
            </p>

            <div className="flex justify-end gap-2.5 pt-2">
              <button
                onClick={() => setShowClearModal(false)}
                className="px-4 py-2 rounded-xl border border-[#334155] text-xs font-semibold text-[#CBD5E1] hover:bg-[#1E3A5F] transition-all cursor-pointer"
              >
                Batal
              </button>
              <button
                onClick={() => {
                  onClearAllSubmissions();
                  setShowClearModal(false);
                }}
                className="px-4 py-2 rounded-xl bg-[#EF4444] hover:bg-red-600 text-white text-xs font-bold transition-all cursor-pointer shadow-md"
              >
                Hapus Semua
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
