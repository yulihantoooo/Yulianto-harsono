package com.example.model

data class MaterialSection(
    val id: String,
    val title: String,
    val subtitle: String,
    val badge: String,
    val content: String,
    val formulas: List<Pair<String, String>>, // Label, Formula
    val examples: List<Pair<String, String>> // Question, Answer/Explanation
)

object LearningMaterials {
    val sections = listOf(
        MaterialSection(
            id = "dasar",
            title = "1. Definisi Perbandingan Trigonometri",
            subtitle = "Segitiga Siku-Siku (Right-Angled Triangle)",
            badge = "Konsep Dasar",
            content = "Pada sebuah segitiga siku-siku dengan sudut acuan θ (teta):\n" +
                    "• Sisi Depan (Opposite / De): sisi yang berhadapan langsung dengan sudut θ.\n" +
                    "• Sisi Samping (Adjacent / Sa): sisi siku-siku yang melekat di samping sudut θ.\n" +
                    "• Sisi Miring (Hypotenuse / Mi): sisi terpanjang yang berhadapan dengan sudut 90°.\n\n" +
                    "Jembatan Keledai Populer di Indonesia:\n" +
                    "• Sin-De-Mi (sin θ = depan / miring)\n" +
                    "• Cos-Sa-Mi (cos θ = samping / miring)\n" +
                    "• Tan-De-Sa (tan θ = depan / samping)",
            formulas = listOf(
                "sin(θ)" to "depan / miring (De / Mi)",
                "cos(θ)" to "samping / miring (Sa / Mi)",
                "tan(θ)" to "depan / samping = sin(θ) / cos(θ) (De / Sa)",
                "csc(θ)" to "1 / sin(θ) = miring / depan (Mi / De)",
                "sec(θ)" to "1 / cos(θ) = miring / samping (Mi / Sa)",
                "cot(θ)" to "1 / tan(θ) = samping / depan (Sa / De)"
            ),
            examples = listOf(
                "Segitiga dengan sisi tegak 3 cm dan alas 4 cm:" to
                        "Sisi miring = √(3² + 4²) = 5 cm.\nMaka sin(θ) = 3/5, cos(θ) = 4/5, tan(θ) = 3/4."
            )
        ),
        MaterialSection(
            id = "istimewa",
            title = "2. Sudut-Sudut Istimewa",
            subtitle = "Nilai Eksak Sudut 0°, 30°, 45°, 60°, 90°",
            badge = "Tabel Referensi",
            content = "Sudut-sudut istimewa sering muncul dalam perhitungan praktis dan fisika. Nilai eksak berikut wajib dipahami tanpa kalkulator:\n\n" +
                    "• Sudut 0°: sin = 0, cos = 1, tan = 0\n" +
                    "• Sudut 30°: sin = 1/2, cos = 1/2√3, tan = 1/3√3\n" +
                    "• Sudut 45°: sin = 1/2√2, cos = 1/2√2, tan = 1\n" +
                    "• Sudut 60°: sin = 1/2√3, cos = 1/2, tan = √3\n" +
                    "• Sudut 90°: sin = 1, cos = 0, tan = Tidak Terdefinisi (∞)",
            formulas = listOf(
                "sin(30°) = cos(60°)" to "1/2",
                "sin(45°) = cos(45°)" to "1/2 · √2 ≈ 0,707",
                "sin(60°) = cos(30°)" to "1/2 · √3 ≈ 0,866",
                "tan(45°)" to "1 (Basis simulasi tinggi pohon)",
                "tan(60°)" to "√3 ≈ 1,732",
                "tan(30°)" to "1/√3 = 1/3 · √3 ≈ 0,577"
            ),
            examples = listOf(
                "Hitung nilai dari sin²(30°) + cos²(30°):" to
                        "(1/2)² + (1/2√3)² = 1/4 + 3/4 = 4/4 = 1 (Membuktikan Identitas Pythagoras)."
            )
        ),
        MaterialSection(
            id = "kuadran",
            title = "3. Sudut Berelasi di 4 Kuadran",
            subtitle = "Sistem Koordinat Kartesius & Aturan Tanda",
            badge = "Kuadran I - IV",
            content = "Lingkaran trigonometri dibagi menjadi 4 kuadran:\n\n" +
                    "• Kuadran I (0° - 90°): Semua bernilai Positif (ALL POSITIVE)\n" +
                    "• Kuadran II (90° - 180°): Hanya Sinus (dan Csc) Positif. Sudut: (180° - α)\n" +
                    "• Kuadran III (180° - 270°): Hanya Tangen (dan Cot) Positif. Sudut: (180° + α)\n" +
                    "• Kuadran IV (270° - 360°): Hanya Cosinus (dan Sec) Positif. Sudut: (360° - α)\n\n" +
                    "Jembatan Keledai: 'Semua Siswa Tahu Caranya' (Semua, Sin, Tan, Cos).",
            formulas = listOf(
                "Kuadran II" to "sin(180°-α) = sin α ; cos(180°-α) = -cos α ; tan(180°-α) = -tan α",
                "Kuadran III" to "sin(180°+α) = -sin α ; cos(180°+α) = -cos α ; tan(180°+α) = +tan α",
                "Kuadran IV" to "sin(360°-α) = -sin α ; cos(360°-α) = +cos α ; tan(360°-α) = -tan α",
                "Periodisitas" to "sin(k·360° + α) = sin α"
            ),
            examples = listOf(
                "Berapakah nilai sin(150°) dan cos(120°)?" to
                        "sin(150°) = sin(180°-30°) = sin(30°) = +1/2.\ncos(120°) = cos(180°-60°) = -cos(60°) = -1/2."
            )
        ),
        MaterialSection(
            id = "identitas",
            title = "4. Identitas Dasar Trigonometri",
            subtitle = "Teorema Pythagoras pada Trigonometri",
            badge = "Identitas Aljabar",
            content = "Hubungan mendasar yang selalu berlaku untuk setiap sudut real:\n\n" +
                    "1. sin²(θ) + cos²(θ) = 1\n" +
                    "2. 1 + tan²(θ) = sec²(θ)\n" +
                    "3. 1 + cot²(θ) = csc²(θ)\n\n" +
                    "Identitas ini diturunkan langsung dari rumus Pythagoras a² + b² = c² dengan membagi kedua ruas dengan kuadrat sisi miring.",
            formulas = listOf(
                "Identitas Pokok" to "sin²(θ) + cos²(θ) = 1",
                "Bentuk Turunan 1" to "sin²(θ) = 1 - cos²(θ)",
                "Bentuk Turunan 2" to "cos²(θ) = 1 - sin²(θ)",
                "Identitas Tangen" to "1 + tan²(θ) = sec²(θ)",
                "Identitas Kotangen" to "1 + cot²(θ) = csc²(θ)"
            ),
            examples = listOf(
                "Jika sin(x) = 3/5 di kuadran I, cari cos(x):" to
                        "cos(x) = √(1 - sin²(x)) = √(1 - 9/25) = √(16/25) = 4/5."
            )
        ),
        MaterialSection(
            id = "aplikasi",
            title = "5. Aplikasi Klinometer & Sudut Elevasi",
            subtitle = "Menghitung Ketinggian Objek Nyata (Pohon / Gedung)",
            badge = "Aplikasi Kontekstual",
            content = "Klinometer adalah alat sederhana untuk mengukur sudut elevasi atau sudut depresi terhadap garis pandang horizontal.\n\n" +
                    "Langkah Menghitung Ketinggian Pohon Kelapa:\n" +
                    "1. Ukur jarak horizontal pengamat ke pangkal pohon (d).\n" +
                    "2. Teropong puncak pohon menggunakan klinometer untuk mendapatkan sudut elevasi (α).\n" +
                    "3. Hitung tinggi pohon di atas mata pengamat: y = d · tan(α).\n" +
                    "4. Tambahkan tinggi mata pengamat dari tanah (t_pengamat):\n" +
                    "   Tinggi Total Pohon = t_pengamat + (d · tan(α)).",
            formulas = listOf(
                "Rumus Klinometer" to "T_total = t_pengamat + (d · tan α)",
                "Jika Sudut α = 45°" to "tan(45°) = 1 => T_total = t_pengamat + d",
                "Jika Sudut α = 60°" to "T_total = t_pengamat + d · √3 ≈ t_pengamat + 1,732 · d",
                "Sudut Depresi" to "Sudut dari garis horizontal menunduk ke arah objek di bawah"
            ),
            examples = listOf(
                "Pengamat setinggi 1,5 m berjarak 20 m dari pohon kelapa, sudut elevasi 45°:" to
                        "Tinggi total = 1,5 + 20 · tan(45°) = 1,5 + 20(1) = 21,5 meter."
            )
        )
    )
}
