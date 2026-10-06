import React from 'react';
import { UserSession } from '../types';
import { GraduationCap } from 'lucide-react';

interface StudentHeaderBadgeProps {
  session: UserSession;
  className?: string;
}

export const StudentHeaderBadge: React.FC<StudentHeaderBadgeProps> = ({ session, className = '' }) => {
  return (
    <div className={`w-full rounded-2xl bg-[#132A45] border border-[#1E3A5F] p-3 sm:p-4 shadow-md flex items-center justify-between gap-3 ${className}`}>
      <div className="flex items-center gap-3">
        <div className="w-10 h-10 rounded-full bg-[#00FFF5]/20 border border-[#00FFF5]/40 flex items-center justify-center text-[#00FFF5]">
          <GraduationCap className="w-5 h-5" />
        </div>
        <div>
          <h2 className="text-sm sm:text-base font-bold text-white leading-snug">
            {session.studentName || 'Siswa'}
          </h2>
          <p className="text-xs text-[#94A3B8]">
            Kelas: <span className="text-[#00FFF5] font-semibold">{session.studentClass || '-'}</span> • Absen: <span className="text-[#E2E8F0] font-semibold">{session.studentAbsentNumber || '-'}</span>
          </p>
        </div>
      </div>

      <div className="bg-[#0B192C] border border-[#00ADB5]/40 px-2.5 py-1 rounded-full text-[11px] font-bold text-[#00FFF5] whitespace-nowrap">
        SMA KELAS X
      </div>
    </div>
  );
};
