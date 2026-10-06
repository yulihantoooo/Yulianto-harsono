/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        mathNavy: '#0A192F',
        mathDarkSurface: '#112240',
        mathTealPrimary: '#00ADB5',
        mathCyanAccent: '#00FFF5',
        mathAmberHighlight: '#FFB703',
        mathEmeraldGreen: '#10B981',
        mathCoralRed: '#EF4444',
        mathCardBackground: '#1E293B',
        mathCardBorder: '#334155',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'sans-serif'],
        mono: ['JetBrains Mono', 'Fira Code', 'monospace'],
      }
    },
  },
  plugins: [],
};
