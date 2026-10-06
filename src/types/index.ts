export type ScreenState = 
  | 'LOGIN'
  | 'HOME_DASHBOARD'
  | 'MATERI'
  | 'SIMULASI'
  | 'SOAL_QUIZ'
  | 'QUIZ_RESULT'
  | 'DATA_SHEET'
  | 'TENTANG_PENGEMBANG';

export interface UserSession {
  studentName: string;
  studentClass: string;
  studentAbsentNumber: string;
}

export interface DetailedAnswer {
  questionId: number;
  chosenOption: number;
  correctOption: number;
  isCorrect: boolean;
}

export interface QuizSubmission {
  id: number;
  studentName: string;
  studentClass: string;
  studentAbsentNumber: string;
  score: number;
  totalQuestions: number;
  correctAnswersCount: number;
  detailedAnswersJson: string; // JSON string of DetailedAnswer[]
  teacherName: string;
  completedAt: number;
}

export interface Question {
  id: number;
  title: string;
  contextStory: string;
  questionText: string;
  options: string[];
  correctIndex: number;
  explanation: string;
  hotsCategory: string;
}

export interface MaterialSection {
  id: string;
  title: string;
  subtitle: string;
  badge: string;
  content: string;
  formulas: [string, string][]; // [Label, Formula]
  examples: [string, string][]; // [Question, Answer]
}
