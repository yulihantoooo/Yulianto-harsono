import React from 'react';
import { AppTopBar } from '../components/AppTopBar';
import { BookOpen, Check, Layers } from 'lucide-react';

interface TentangPengembangScreenProps {
  onBack: () => void;
}

export const TentangPengembangScreen: React.FC<TentangPengembangScreenProps> = ({ onBack }) => {
  const points = [
    'Memahami rasio trigonometri (sin, cos, tan) pada segitiga siku-siku.',
    'Menentukan tinggi objek nyata (pohon kelapa) dengan klinometer digital.',
    'Menyelesaikan 10 soal HOTS berorientasi asesmen kompetensi minimum.',
    'Merekam dan memantau capaian belajar secara mandiri di data sheet.',
  ];

  return (
    <div className="min-h-screen bg-[#0A192F] text-white flex flex-col pb-12">
      <AppTopBar
        title="Tentang Media & Pengembang"
        subtitle="Multimedia Interaktif Trigonometri"
        onBackClick={onBack}
      />

      <div className="p-4 sm:p-6 max-w-3xl mx-auto w-full space-y-5">
        {/* Profile Card */}
        <div className="bg-[#132B45] border-2 border-[#FFB703] rounded-3xl p-6 shadow-xl flex flex-col items-center text-center space-y-3.5">
          <img
            src="/img_yulianto_harsono.png"
            alt="Foto Guru Pengampu Yulianto Harsono"
            className="w-28 h-28 sm:w-32 sm:h-32 rounded-full object-cover border-3 border-[#FFB703] shadow-lg"
          />

          <div>
            <h2 className="text-xl sm:text-2xl font-extrabold text-[#FFB703] tracking-wide">
              YULIANTO HARSONO
            </h2>
            <p className="text-xs sm:text-sm text-[#E2E8F0] font-medium mt-1">
              Guru Pengampu / Pengembang Multimedia Pembelajaran
            </p>
          </div>

          <span className="inline-block px-3.5 py-1 rounded-full text-xs font-bold bg-[#00FFF5]/15 text-[#00FFF5] border border-[#00FFF5]/30">
            Matematika SMA Kelas X (Fase E)
          </span>
        </div>

        {/* Learning Objectives */}
        <div className="bg-[#1E293B] border border-[#334155] rounded-2xl p-5 shadow-lg space-y-3.5">
          <div className="flex items-center gap-2 text-[#00FFF5] font-bold text-sm sm:text-base border-b border-[#334155] pb-2">
            <BookOpen className="w-5 h-5" />
            <span>Tujuan & Sasaran Pembelajaran</span>
          </div>

          <p className="text-xs sm:text-sm text-[#CBD5E1] leading-relaxed">
            Multimedia Interaktif Pembelajaran Trigonometri dirancang untuk membantu peserta didik SMA Kelas X memahami konsep abstrak trigonometri melalui visualisasi interaktif dan pemecahan masalah kontekstual.
          </p>

          <div className="space-y-2 pt-1">
            {points.map((pt, idx) => (
              <div key={idx} className="flex items-start gap-2.5">
                <div className="w-5 h-5 rounded-full bg-[#10B981]/20 flex items-center justify-center text-[#10B981] flex-shrink-0 mt-0.5">
                  <Check className="w-3.5 h-3.5" />
                </div>
                <span className="text-xs sm:text-sm text-[#E2E8F0] leading-snug">
                  {pt}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* Technical Specs Card */}
        <div className="bg-[#0F1E33] border border-[#1E3A5F] rounded-2xl p-5 shadow-lg space-y-3">
          <div className="flex items-center gap-2 text-[#00FFF5] font-bold text-sm">
            <Layers className="w-4 h-4" />
            <span>Fitur Sistem Pembelajaran Web App</span>
          </div>

          <div className="text-xs text-[#94A3B8] space-y-1.5 leading-relaxed font-mono">
            <p>• Login Siswa: Nama, Kelas, dan Nomer Absen</p>
            <p>• Materi Lengkap: 5 Bab Trigonometri SMA Kelas X</p>
            <p>• Simulasi Klinometer: Menghitung Tinggi Pohon Kelapa secara Real-Time</p>
            <p>• 10 Soal Pilihan Ganda HOTS dengan Diagram Geometris & Pembahasan Detail</p>
            <p>• Lembar Data Sheet: Penyimpanan Data Lokal & Ekspor CSV</p>
            <p>• Desain Responsif: Optimal dibuka di HP Android, iOS, Tablet, maupun Laptop</p>
          </div>
        </div>
      </div>
    </div>
  );
};
