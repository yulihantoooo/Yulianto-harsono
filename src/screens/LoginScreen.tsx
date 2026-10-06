import React, { useState } from 'react';
import { Pi, User, Layers, Hash, Play } from 'lucide-react';

interface LoginScreenProps {
  onLoginSuccess: (nama: string, kelas: string, noAbsen: string) => void;
}

export const LoginScreen: React.FC<LoginScreenProps> = ({ onLoginSuccess }) => {
  const [nama, setNama] = useState('');
  const [kelas, setKelas] = useState('X-MIPA 1');
  const [noAbsen, setNoAbsen] = useState('');
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!nama.trim()) {
      setErrorMessage('Silakan isi nama lengkap siswa!');
      return;
    }
    if (!kelas.trim()) {
      setErrorMessage('Silakan isi kelas siswa!');
      return;
    }
    if (!noAbsen.trim()) {
      setErrorMessage('Silakan isi nomer absen siswa!');
      return;
    }
    setErrorMessage(null);
    onLoginSuccess(nama.trim(), kelas.trim(), noAbsen.trim());
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#0A192F] via-[#0F223D] to-[#07101E] flex flex-col justify-center items-center p-4 sm:p-6 text-white select-none">
      <div className="w-full max-w-md space-y-6">
        {/* Brand Hero */}
        <div className="text-center space-y-2">
          <div className="inline-flex w-20 h-20 rounded-3xl bg-gradient-to-tr from-[#00ADB5] to-[#00FFF5] items-center justify-center shadow-lg shadow-[#00FFF5]/20 text-[#0A192F] mb-1">
            <Pi className="w-12 h-12 stroke-[2.5]" />
          </div>
          <p className="text-xs font-bold tracking-[0.2em] text-[#00FFF5] uppercase">
            Multimedia Interaktif
          </p>
          <h1 className="text-3xl font-extrabold tracking-wide text-white drop-shadow">
            TRIGONOMETRI
          </h1>
          <p className="text-sm font-semibold text-[#FFB703]">
            Matematika SMA KELAS X
          </p>
        </div>

        {/* Teacher Banner */}
        <div className="bg-[#132B45] border-2 border-[#FFB703]/80 rounded-2xl p-3 sm:p-4 shadow-xl flex items-center gap-4">
          <img
            src="/img_yulianto_harsono.png"
            alt="Foto Guru Pengampu Yulianto Harsono"
            className="w-16 h-16 rounded-full object-cover border-2 border-[#FFB703] shadow-md flex-shrink-0"
            onError={(e) => {
              // fallback if image fails
              (e.currentTarget as HTMLImageElement).src = 'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="%23FFB703"><circle cx="12" cy="8" r="4"/><path d="M20 21a8 8 0 1 0-16 0"/></svg>';
            }}
          />
          <div className="flex-1 min-w-0">
            <div className="text-[11px] text-[#94A3B8]">Guru Pengampu:</div>
            <div className="text-base sm:text-lg font-extrabold text-[#FFB703] truncate">
              YULIANTO HARSONO
            </div>
            <div className="text-xs text-[#E2E8F0] font-medium">
              Matematika SMA Kelas X
            </div>
          </div>
        </div>

        {/* Login Form Card */}
        <div className="bg-[#132338] border border-[#233550] rounded-2xl p-5 sm:p-6 shadow-2xl space-y-5">
          <div className="border-b border-[#1E3A5F] pb-3">
            <h2 className="text-lg font-bold text-white">Identitas Siswa</h2>
            <p className="text-xs text-[#94A3B8] mt-0.5">
              Masukkan data diri Anda untuk memulai simulasi dan kuis
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">
            {/* Nama */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-[#CBD5E1] block">
                Nama Lengkap Siswa
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-[#00FFF5]">
                  <User className="w-5 h-5" />
                </div>
                <input
                  type="text"
                  value={nama}
                  onChange={(e) => {
                    setNama(e.target.value);
                    setErrorMessage(null);
                  }}
                  placeholder="Contoh: Rizky Pratama"
                  data-testid="login_input_name"
                  className="w-full bg-[#0B192C] border border-[#334155] focus:border-[#00FFF5] rounded-xl pl-10 pr-4 py-2.5 text-sm text-white placeholder-[#64748B] outline-none transition-colors"
                />
              </div>
            </div>

            {/* Kelas */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-[#CBD5E1] block">
                Kelas
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-[#00FFF5]">
                  <Layers className="w-5 h-5" />
                </div>
                <input
                  type="text"
                  value={kelas}
                  onChange={(e) => {
                    setKelas(e.target.value);
                    setErrorMessage(null);
                  }}
                  placeholder="Contoh: X-1 / X-MIPA 2"
                  data-testid="login_input_class"
                  className="w-full bg-[#0B192C] border border-[#334155] focus:border-[#00FFF5] rounded-xl pl-10 pr-4 py-2.5 text-sm text-white placeholder-[#64748B] outline-none transition-colors"
                />
              </div>
            </div>

            {/* No Absen */}
            <div className="space-y-1.5">
              <label className="text-xs font-semibold text-[#CBD5E1] block">
                Nomer Absen
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-[#00FFF5]">
                  <Hash className="w-5 h-5" />
                </div>
                <input
                  type="text"
                  value={noAbsen}
                  onChange={(e) => {
                    setNoAbsen(e.target.value);
                    setErrorMessage(null);
                  }}
                  placeholder="Contoh: 18"
                  data-testid="login_input_absent"
                  className="w-full bg-[#0B192C] border border-[#334155] focus:border-[#00FFF5] rounded-xl pl-10 pr-4 py-2.5 text-sm text-white placeholder-[#64748B] outline-none transition-colors"
                />
              </div>
            </div>

            {errorMessage && (
              <div className="text-xs font-semibold text-[#EF4444] bg-[#EF4444]/10 border border-[#EF4444]/30 rounded-lg p-2.5">
                {errorMessage}
              </div>
            )}

            <button
              type="submit"
              data-testid="login_submit_button"
              className="w-full mt-2 bg-gradient-to-r from-[#00FFF5] to-[#00ADB5] hover:opacity-95 active:scale-[0.99] text-[#0A192F] font-bold py-3 rounded-xl shadow-lg shadow-[#00FFF5]/20 flex items-center justify-center gap-2 transition-all cursor-pointer"
            >
              <Play className="w-5 h-5 fill-current" />
              <span>Masuk & Mulai Belajar</span>
            </button>
          </form>
        </div>

        {/* Footer info */}
        <div className="text-center text-xs text-[#64748B] space-y-1">
          <p>Kurikulum Merdeka • SMA Kelas X</p>
          <p>© Pengembang: Yulianto Harsono</p>
        </div>
      </div>
    </div>
  );
};
