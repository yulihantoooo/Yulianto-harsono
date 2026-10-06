import { UserSession, QuizSubmission } from '../types';

const SESSION_KEY = 'trigonometri_user_session';
const SUBMISSIONS_KEY = 'trigonometri_quiz_submissions';

export const StorageService = {
  getUserSession(): UserSession | null {
    try {
      const data = localStorage.getItem(SESSION_KEY);
      return data ? JSON.parse(data) : null;
    } catch {
      return null;
    }
  },

  saveUserSession(session: UserSession): void {
    try {
      localStorage.setItem(SESSION_KEY, JSON.stringify(session));
    } catch (e) {
      console.error("Failed to save session", e);
    }
  },

  clearUserSession(): void {
    try {
      localStorage.removeItem(SESSION_KEY);
    } catch (e) {
      console.error("Failed to clear session", e);
    }
  },

  getAllSubmissions(): QuizSubmission[] {
    try {
      const data = localStorage.getItem(SUBMISSIONS_KEY);
      if (data) {
        return JSON.parse(data);
      }
      // Seed with initial example submission if completely empty
      const sample: QuizSubmission[] = [
        {
          id: 1,
          studentName: "Rizky Pratama",
          studentClass: "X-MIPA 1",
          studentAbsentNumber: "18",
          score: 90,
          totalQuestions: 10,
          correctAnswersCount: 9,
          detailedAnswersJson: JSON.stringify([
            { questionId: 1, chosenOption: 2, correctOption: 2, isCorrect: true },
            { questionId: 2, chosenOption: 1, correctOption: 1, isCorrect: true },
            { questionId: 3, chosenOption: 1, correctOption: 1, isCorrect: true },
            { questionId: 4, chosenOption: 0, correctOption: 0, isCorrect: true },
            { questionId: 5, chosenOption: 1, correctOption: 1, isCorrect: true },
            { questionId: 6, chosenOption: 2, correctOption: 2, isCorrect: true },
            { questionId: 7, chosenOption: 2, correctOption: 2, isCorrect: true },
            { questionId: 8, chosenOption: 0, correctOption: 0, isCorrect: true },
            { questionId: 9, chosenOption: 1, correctOption: 1, isCorrect: true },
            { questionId: 10, chosenOption: 2, correctOption: 0, isCorrect: false }
          ]),
          teacherName: "YULIANTO HARSONO",
          completedAt: Date.now() - 3600000 * 2
        }
      ];
      localStorage.setItem(SUBMISSIONS_KEY, JSON.stringify(sample));
      return sample;
    } catch {
      return [];
    }
  },

  saveSubmission(item: Omit<QuizSubmission, 'id'>): QuizSubmission {
    const list = this.getAllSubmissions();
    const newId = list.length > 0 ? Math.max(...list.map(s => s.id)) + 1 : 1;
    const newSubmission: QuizSubmission = {
      ...item,
      id: newId
    };
    const updated = [newSubmission, ...list];
    try {
      localStorage.setItem(SUBMISSIONS_KEY, JSON.stringify(updated));
    } catch (e) {
      console.error("Failed to save submission", e);
    }
    return newSubmission;
  },

  deleteSubmission(id: number): QuizSubmission[] {
    const list = this.getAllSubmissions().filter(s => s.id !== id);
    try {
      localStorage.setItem(SUBMISSIONS_KEY, JSON.stringify(list));
    } catch (e) {
      console.error("Failed to delete submission", e);
    }
    return list;
  },

  clearAllSubmissions(): void {
    try {
      localStorage.setItem(SUBMISSIONS_KEY, JSON.stringify([]));
    } catch (e) {
      console.error("Failed to clear submissions", e);
    }
  },

  exportToCsv(submissions: QuizSubmission[]): string {
    const headers = ["ID", "Nama Siswa", "Kelas", "No Absen", "Skor", "Benar", "Total Soal", "Guru Pengampu", "Waktu Selesai"];
    const rows = submissions.map(s => {
      const date = new Date(s.completedAt).toLocaleString('id-ID');
      return [
        s.id,
        `"${s.studentName.replace(/"/g, '""')}"`,
        `"${s.studentClass.replace(/"/g, '""')}"`,
        `"${s.studentAbsentNumber}"`,
        s.score,
        s.correctAnswersCount,
        s.totalQuestions,
        `"${s.teacherName}"`,
        `"${date}"`
      ].join(',');
    });
    return [headers.join(','), ...rows].join('\n');
  }
};
