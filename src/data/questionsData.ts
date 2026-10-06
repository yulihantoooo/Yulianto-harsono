import { Question } from '../types';

export const QUESTIONS_DATA: Question[] = [
  {
    id: 1,
    title: "Soal 1: Pengamatan Puncak Pohon Kelapa",
    contextStory: "Seorang siswa bernama Rizky berdiri pada jarak horizontal 15 meter dari pangkal pohon kelapa. Dengan klinometer setinggi 1,6 meter dari tanah, ia membidik puncak pohon kelapa dengan sudut elevasi 45°.",
    questionText: "Berapakah perkiraan tinggi total pohon kelapa tersebut dari permukaan tanah?",
    options: [
      "A. 13,4 meter",
      "B. 15,0 meter",
      "C. 16,6 meter",
      "D. 18,2 meter",
      "E. 21,5 meter"
    ],
    correctIndex: 2,
    explanation: "Gunakan perbandingan tangen: tan(45°) = y / x => 1 = y / 15 => y = 15 meter. Tinggi total pohon kelapa = tinggi pengamat + y = 1,6 m + 15 m = 16,6 meter.",
    hotsCategory: "Aplikasi Kontekstual Trigonometri"
  },
  {
    id: 2,
    title: "Soal 2: Menghindari Rintangan Sungai",
    contextStory: "Dua orang siswa, Andi dan Budi, ingin mengukur tinggi pohon kelapa di seberang sungai tanpa menyeberang. Andi mengamati puncak pohon dengan sudut elevasi 60°. Budi mundur sejauh 20 meter menjauhi sungai segaris dengan Andi dan pohon, lalu mengamati puncak dengan sudut elevasi 30° (tinggi mata kedua siswa diabaikan atau sama).",
    questionText: "Berapakah tinggi pohon kelapa tersebut?",
    options: [
      "A. 10 meter",
      "B. 10√3 meter",
      "C. 15√3 meter",
      "D. 20√3 meter",
      "E. 30 meter"
    ],
    correctIndex: 1,
    explanation: "Misal jarak Andi ke pohon adalah d dan tinggi pohon h. h = d·tan(60°) = d√3, sehingga d = h/√3. Dari Budi: tan(30°) = h / (d + 20) => 1/√3 = h / (h/√3 + 20) => h/√3 + 20 = h√3 => 20 = h(√3 - 1/√3) = h(2/√3) => h = 20·√3 / 2 = 10√3 meter (~17,32 m).",
    hotsCategory: "Pemecahan Masalah Dua Sudut Elevasi"
  },
  {
    id: 3,
    title: "Soal 3: Nilai Perbandingan Trigonometri di Kuadran II",
    contextStory: "Diberikan sudut α tumpul (90° < α < 180°) yang memenuhi persamaan sin(α) = 5/13.",
    questionText: "Nilai dari cos(α) + tan(α) adalah...",
    options: [
      "A. -119/156",
      "B. -209/156",
      "C. 119/156",
      "D. -17/12",
      "E. -7/13"
    ],
    correctIndex: 1,
    explanation: "Di Kuadran II, cosinus dan tangen bernilai negatif. Sisi depan = 5, miring = 13, sisi samping = √(13² - 5²) = 12. Maka cos(α) = -12/13 dan tan(α) = -5/12. Jadi cos(α) + tan(α) = -12/13 + (-5/12) = (-144 - 65) / 156 = -209/156.",
    hotsCategory: "Analisis Relasi Sudut Antar Kuadran"
  },
  {
    id: 4,
    title: "Soal 4: Tangga Keselamatan Pemanjat Kelapa",
    contextStory: "Pak tani menyandarkan tangga darurat sepanjang 10 meter ke batang pohon kelapa. Standar keselamatan menyatakan sudut kemiringan tangga terhadap tanah datar yang aman adalah 60°.",
    questionText: "Jarak kaki tangga dari pangkal pohon dan tinggi ujung tangga yang menyentuh batang pohon berturut-turut adalah...",
    options: [
      "A. 5 meter dan 5√3 meter",
      "B. 5√3 meter dan 5 meter",
      "C. 5√2 meter dan 5√2 meter",
      "D. 6 meter dan 8 meter",
      "E. 4 meter dan 6√2 meter"
    ],
    correctIndex: 0,
    explanation: "Jarak kaki tangga ke pohon (sisi samping) = L · cos(60°) = 10 · 1/2 = 5 meter. Tinggi sentuh tangga (sisi depan) = L · sin(60°) = 10 · (√3/2) = 5√3 meter (~8,66 m).",
    hotsCategory: "Pemodelan Fisik & Vektor Geometri"
  },
  {
    id: 5,
    title: "Soal 5: Penyederhanaan Identitas Trigonometri",
    contextStory: "Dalam analisis osilasi ayunan buah kelapa ditiup angin, terdapat ekspresi identitas trigonometri: [ (sin x + cos x)² - 1 ] / (2 · sin x · cos x).",
    questionText: "Bentuk sederhana dari ekspresi tersebut untuk nilai sin x · cos x ≠ 0 adalah...",
    options: [
      "A. 0",
      "B. 1",
      "C. 2",
      "D. tan x",
      "E. sin² x"
    ],
    correctIndex: 1,
    explanation: "(sin x + cos x)² = sin² x + 2 sin x cos x + cos² x = (sin² x + cos² x) + 2 sin x cos x = 1 + 2 sin x cos x. Maka pembilang = 1 + 2 sin x cos x - 1 = 2 sin x cos x. Dibagi penyebut 2 sin x cos x menghasilkan 1.",
    hotsCategory: "Verifikasi & Manipulasi Aljabar Identitas"
  },
  {
    id: 6,
    title: "Soal 6: Bayangan Pohon Kelapa Akibat Sudut Matahari",
    contextStory: "Pada pukul 09.00 pagi, sinar matahari membentuk sudut elevasi 30° terhadap tanah dan panjang bayangan pohon kelapa terukur 24 meter. Pada pukul 12.00, sudut elevasi sinar matahari menjadi 60°.",
    questionText: "Berapakah pengurangan panjang bayangan pohon kelapa tersebut dari pukul 09.00 ke 12.00?",
    options: [
      "A. 8 meter",
      "B. 12 meter",
      "C. 16 meter",
      "D. 8√3 meter",
      "E. 16√3 meter"
    ],
    correctIndex: 2,
    explanation: "Tinggi pohon t = bayangan₁ · tan(30°) = 24 · (1/√3) = 8√3 meter. Pada pukul 12.00: bayangan₂ = t / tan(60°) = (8√3) / √3 = 8 meter. Pengurangan panjang bayangan = 24 - 8 = 16 meter.",
    hotsCategory: "Analisis Perubahan Variabel Dinamis"
  },
  {
    id: 7,
    title: "Soal 7: Sudut Depresi dari Atas Pohon",
    contextStory: "Pemetik kelapa berada di pucuk pohon setinggi 20 meter di atas tanah. Ia melihat sebuah keranjang buah di tanah dengan sudut depresi 30°.",
    questionText: "Berapakah jarak lurus (hipotenusa) antara mata pemetik kelapa dan keranjang di tanah tersebut?",
    options: [
      "A. 20√3 meter",
      "B. 30 meter",
      "C. 40 meter",
      "D. 40√3 meter",
      "E. 25 meter"
    ],
    correctIndex: 2,
    explanation: "Sudut depresi 30° sama dengan sudut elevasi dari keranjang ke pucuk pohon (sudut dalam berseberangan). sin(30°) = tinggi / jarak_lurus => 1/2 = 20 / R => R = 20 · 2 = 40 meter.",
    hotsCategory: "Konversi Sudut Depresi & Jarak Pandang"
  },
  {
    id: 8,
    title: "Soal 8: Evaluasi Sudut Berelasi (Kuadran III & IV)",
    contextStory: "Dalam pengukuran arah angin pantai dengan kompas azimuth kelautan, diperoleh ekspresi nilai: [ sin(210°) + cos(300°) - tan(225°) ].",
    questionText: "Hasil perhitungan nilai eksak dari ekspresi tersebut adalah...",
    options: [
      "A. -1",
      "B. 0",
      "C. 1",
      "D. -1/2",
      "E. 1/2"
    ],
    correctIndex: 0,
    explanation: "sin(210°) = sin(180° + 30°) = -sin(30°) = -1/2. cos(300°) = cos(360° - 60°) = cos(60°) = +1/2. tan(225°) = tan(180° + 45°) = +tan(45°) = 1. Jadi: (-1/2) + (1/2) - (1) = 0 - 1 = -1.",
    hotsCategory: "Evaluasi Komputasi Sudut Relasi Luar Kuadran I"
  },
  {
    id: 9,
    title: "Soal 9: Perbandingan Luas Segitiga Berdasarkan Sinus Sudut",
    contextStory: "Lahan kebun kelapa Pak Yulianto berbentuk segitiga dengan dua sisi pembatas masing-masing berukuran 30 meter dan 40 meter, serta sudut yang diapit kedua sisi tersebut sebesar 150°.",
    questionText: "Berapakah luas lahan kebun kelapa tersebut?",
    options: [
      "A. 600 m²",
      "B. 300 m²",
      "C. 300√3 m²",
      "D. 150 m²",
      "E. 450 m²"
    ],
    correctIndex: 1,
    explanation: "Rumus luas segitiga dengan aturan sinus: L = 1/2 · a · b · sin(θ). Di sini a = 30 m, b = 40 m, θ = 150°. sin(150°) = sin(180° - 30°) = sin(30°) = 1/2. L = 1/2 · 30 · 40 · 1/2 = 300 m².",
    hotsCategory: "Aplikasi Aturan Trigonometri pada Luas Bidang"
  },
  {
    id: 10,
    title: "Soal 10: Aturan Sinus dan Penentuan Jarak Patok",
    contextStory: "Dua patok A dan B dipasang berjarak 100 meter di tepi pantai. Sebuah pohon kelapa C diamati dari patok A dengan sudut ∠CAB = 30° dan dari patok B dengan sudut ∠CBA = 45°.",
    questionText: "Berapakah jarak lurus dari patok A ke pohon kelapa C (panjang sisi AC)? Gunakan sin(105°) = (√6 + √2) / 4 atau aturan perbandingan sudut segitiga.",
    options: [
      "A. 100√2 meter",
      "B. 100(√6 - √2) meter",
      "C. 100(√6 + √2) meter",
      "D. 50(√6 - √2) meter",
      "E. 50√2 meter"
    ],
    correctIndex: 0,
    explanation: "Sudut C = 180° - (30° + 45°) = 105°. Namun untuk jarak patok B ke C: BC = 100·sin(30°)/sin(105°). Jika ditanyakan jarak AC: AC/sin(45°) = 100/sin(105°). Alternatif segitiga dengan sudut di C = 45° dan B = 30°: AC / sin(30°) = AB / sin(45°) => AC = 100 · (1/2) / (√2/2) = 100 / √2 = 50√2 m. Jika sudut ∠CAB = 105°, ∠CBA = 45°, maka ∠ACB = 30°. Maka AC / sin(45°) = AB / sin(30°) => AC = 100 · (√2/2) / (1/2) = 100√2 meter.",
    hotsCategory: "Penerapan Aturan Sinus Segitiga Sembarang"
  }
];
