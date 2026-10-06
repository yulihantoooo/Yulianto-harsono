import React from 'react';
import { ArrowLeft } from 'lucide-react';

interface AppTopBarProps {
  title: string;
  subtitle?: string;
  onBackClick?: () => void;
  actions?: React.ReactNode;
  className?: string;
}

export const AppTopBar: React.FC<AppTopBarProps> = ({
  title,
  subtitle,
  onBackClick,
  actions,
  className = '',
}) => {
  return (
    <header className={`sticky top-0 z-30 bg-[#0A192F]/90 backdrop-blur-md border-b border-[#1E3A5F] px-4 py-3 flex items-center justify-between ${className}`}>
      <div className="flex items-center gap-3">
        {onBackClick && (
          <button
            onClick={onBackClick}
            data-testid="top_bar_back_button"
            className="p-2 -ml-1 rounded-full text-[#00FFF5] hover:bg-[#1E3A5F]/50 active:scale-95 transition-all"
            aria-label="Kembali"
          >
            <ArrowLeft className="w-5 h-5" />
          </button>
        )}
        <div>
          <h1 className="text-base sm:text-lg font-bold text-white tracking-wide leading-tight">
            {title}
          </h1>
          {subtitle && (
            <p className="text-xs text-[#00FFF5] font-medium leading-none mt-0.5">
              {subtitle}
            </p>
          )}
        </div>
      </div>

      {actions && <div className="flex items-center gap-2">{actions}</div>}
    </header>
  );
};
