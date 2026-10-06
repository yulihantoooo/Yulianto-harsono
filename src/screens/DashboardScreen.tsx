import React from 'react';
import { UserSession, ScreenState } from '../types';
import { StudentHeaderBadge } from '../components/StudentHeaderBadge';
import { 
  BookOpen, 
  Palmtree, 
  HelpCircle, 
  Table2, 
  Info, 
  LogOut, 
  ChevronRight 
} from 'lucide-react';

interface DashboardScreenProps {
  session: UserSession;
  onNavigate: (screen: ScreenState) => void;
  onLogout: () => void;
}

export const DashboardScreen: React.FC<DashboardScreenProps> = ({
  session,
  onNavigate,
  onLogout,
}) => {
  return (
    <div className="min-h-screen bg-[#0A192F] text-white p-4 sm:p-6 pb-12 max-w-3xl mx-auto space-y-5">
      {/* Top Header */}
      <div className="flex items-center justify-between border-b border-[#1E3A5F] pb-3">
        <div>
          <span className="text-[10px] font-bold tracking-[0.2em] text-[#00FFF5] uppercase block">
            Multimedia Interaktif
          </span>
          <h1 className="text-xl sm:text-2xl font-extrabold text-white">
            Trigonometri Kelas X
          </h1>
        </div>

        <div className="flex items-center gap-1">
          <button
            onClick={() => onNavigate('TENTANG_PENGEMBANG')}
            data-testid="btn_about_developer"
            className="p-2 rounded-xl text-[#00FFF5] hover:bg-[#1E3A5F] active:scale-95 transition-all"
            title="Tentang Pengembang"
          >
            <Info className="w-5 h-5" />
          </button>
          <button
            onClick={onLogout}
            data-testid="btn_logout"
            className="p-2 rounded-xl text-[#EF4444] hover:bg-[#1E3A5F] active:scale-95 transition-all"
            title="Keluar"
          >
            <LogOut className="w-5 h-5" />
          </button>
        </div>
      </div>

      {/* Student Badge */}
      <StudentHeaderBadge session={session} />

      {/* Teacher Banner */}
      <div className="bg-[#132A45] border border-[#1E3A5F] rounded-2xl p-4 shadow-lg flex items-center gap-4">
        <img
          src="/img_yulianto_harsono.png"
          alt="Foto Guru Yulianto Harsono"
          className="w-14 h-14 rounded-full object-cover border-2 border-[#FFB703] shadow flex-shrink-0"
        />
        <div className="flex-1 min-w-0">
          <div className="text-[11px] text-[#94A3B8]">Guru Pengampu:</div>
          <div className="text-base font-bold text-[#FFB703] tracking-wide truncate">
            YULIANTO HARSONO
          </div>
          <div className="text-xs text-[#CBD5E1]">
            Mata Pelajaran Matematika Wajib SMA KELAS X
          </div>
        </div>
      </div>

      {/* Section Title */}
      <div className="pt-1">
        <h2 className="text-xs font-bold tracking-wider text-[#00FFF5] uppercase">
          Menu Utama Pembelajaran
        </h2>
      </div>

      {/* Menu Cards */}
      <div className="grid grid-cols-1 gap-3.5">
        {/* 1. Materi */}
        <div
          onClick={() => onNavigate('MATERI')}
          data-testid="menu_btn_materi"
          className="group relative overflow-hidden rounded-2xl p-4 sm:p-5 bg-gradient-to-r from-[#0F3A57] to-[#0D243B] border border-[#00FFF5]/35 hover:border-[#00FFF5] shadow-lg cursor-pointer transition-all active:scale-[0.99]"
        >
          <div className="flex items-center gap-4">
            <div className="w-13 h-13 rounded-2xl bg-[#00FFF5]/20 flex items-center justify-center text-[#00FFF5] flex-shrink-0 p-3">
              <BookOpen className="w-7 h-7" />
            </div>

            <div className="flex-1 min-w-0">
              <span className="inline-block px-2.5 py-0.5 rounded-md text-[10px] font-bold bg-[#00FFF5]/20 text-[#00FFF5] mb-1">
                Teori & Rumus
              </span>
              <h3 className="text-base sm:text-lg font-bold text-white group-hover:text-[#00FFF5] transition-colors">
                1. MATERI TRIGONOMETRI
              </h3>
              <p className="text-xs text-[#CBD5E1] line-clamp-2 mt-0.5 leading-relaxed">
                Konsep dasar sin, cos, tan, sudut istimewa, sudut berelasi 4 kuadran, dan identitas trigonometri.
              </p>
            </div>

            <ChevronRight className="w-5 h-5 text-[#94A3B8] group-hover:text-[#00FFF5] transition-colors flex-shrink-0" />
          </div>
        </div>

        {/* 2. Simulasi */}
        <div
          onClick={() => onNavigate('SIMULASI')}
          data-testid="menu_btn_simulasi"
          className="group relative overflow-hidden rounded-2xl p-4 sm:p-5 bg-gradient-to-r from-[#0D4733] to-[#0A2B20] border border-[#10B981]/35 hover:border-[#10B981] shadow-lg cursor-pointer transition-all active:scale-[0.99]"
        >
          <div className="flex items-center gap-4">
            <div className="w-13 h-13 rounded-2xl bg-[#10B981]/20 flex items-center justify-center text-[#10B981] flex-shrink-0 p-3">
              <Palmtree className="w-7 h-7" />
            </div>

            <div className="flex-1 min-w-0">
              <span className="inline-block px-2.5 py-0.5 rounded-md text-[10px] font-bold bg-[#10B981]/20 text-[#10B981] mb-1">
                Simulasi Interaktif
              </span>
              <h3 className="text-base sm:text-lg font-bold text-white group-hover:text-[#10B981] transition-colors">
                2. SIMULASI KLINOMETER
              </h3>
              <p className="text-xs text-[#CBD5E1] line-clamp-2 mt-0.5 leading-relaxed">
                Game simulasi interaktif menghitung tinggi pohon kelapa secara nyata menggunakan perbandingan tangen sudut elevasi.
              </p>
            </div>

            <ChevronRight className="w-5 h-5 text-[#94A3B8] group-hover:text-[#10B981] transition-colors flex-shrink-0" />
          </div>
        </div>

        {/* 3. Soal HOTS */}
        <div
          onClick={() => onNavigate('SOAL_QUIZ')}
          data-testid="menu_btn_soal"
          className="group relative overflow-hidden rounded-2xl p-4 sm:p-5 bg-gradient-to-r from-[#4C3005] to-[#281902] border border-[#FFB703]/35 hover:border-[#FFB703] shadow-lg cursor-pointer transition-all active:scale-[0.99]"
        >
          <div className="flex items-center gap-4">
            <div className="w-13 h-13 rounded-2xl bg-[#FFB703]/20 flex items-center justify-center text-[#FFB703] flex-shrink-0 p-3">
              <HelpCircle className="w-7 h-7" />
            </div>

            <div className="flex-1 min-w-0">
              <span className="inline-block px-2.5 py-0.5 rounded-md text-[10px] font-bold bg-[#FFB703]/20 text-[#FFB703] mb-1">
                Evaluasi Kuis
              </span>
              <h3 className="text-base sm:text-lg font-bold text-white group-hover:text-[#FFB703] transition-colors">
                3. SOAL HOTS (10 SOAL)
              </h3>
              <p className="text-xs text-[#CBD5E1] line-clamp-2 mt-0.5 leading-relaxed">
                Uji pemahaman dengan 10 butir soal pilihan ganda High Order Thinking Skills (HOTS) berorientasi AKM & Kontekstual.
              </p>
            </div>

            <ChevronRight className="w-5 h-5 text-[#94A3B8] group-hover:text-[#FFB703] transition-colors flex-shrink-0" />
          </div>
        </div>

        {/* 4. Data Sheet */}
        <div
          onClick={() => onNavigate('DATA_SHEET')}
          data-testid="menu_btn_data_sheet"
          className="group relative overflow-hidden rounded-2xl p-4 sm:p-5 bg-gradient-to-r from-[#331652] to-[#1B0B2E] border border-[#A855F7]/35 hover:border-[#A855F7] shadow-lg cursor-pointer transition-all active:scale-[0.99]"
        >
          <div className="flex items-center gap-4">
            <div className="w-13 h-13 rounded-2xl bg-[#A855F7]/20 flex items-center justify-center text-[#A855F7] flex-shrink-0 p-3">
              <Table2 className="w-7 h-7" />
            </div>

            <div className="flex-1 min-w-0">
              <span className="inline-block px-2.5 py-0.5 rounded-md text-[10px] font-bold bg-[#A855F7]/20 text-[#A855F7] mb-1">
                Rekapitulasi Hasil
              </span>
              <h3 className="text-base sm:text-lg font-bold text-white group-hover:text-[#A855F7] transition-colors">
                4. DATA SHEET NILAI
              </h3>
              <p className="text-xs text-[#CBD5E1] line-clamp-2 mt-0.5 leading-relaxed">
                Rekaman hasil belajar, skor kuis, dan data jawaban siswa tersimpan rapi untuk analisis capaian belajar guru & siswa.
              </p>
            </div>

            <ChevronRight className="w-5 h-5 text-[#94A3B8] group-hover:text-[#A855F7] transition-colors flex-shrink-0" />
          </div>
        </div>
      </div>
    </div>
  );
};
