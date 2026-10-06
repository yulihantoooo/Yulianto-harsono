import React, { useState, useEffect } from 'react';
import { ScreenState, UserSession, QuizSubmission, DetailedAnswer } from './types';
import { StorageService } from './services/storage';
import { QUESTIONS_DATA } from './data/questionsData';

import { LoginScreen } from './screens/LoginScreen';
import { DashboardScreen } from './screens/DashboardScreen';
import { MateriScreen } from './screens/MateriScreen';
import { SimulasiScreen } from './screens/SimulasiScreen';
import { SoalQuizScreen } from './screens/SoalQuizScreen';
import { QuizResultScreen } from './screens/QuizResultScreen';
import { DataSheetScreen } from './screens/DataSheetScreen';
import { TentangPengembangScreen } from './screens/TentangPengembangScreen';

export const App: React.FC = () => {
  const [currentScreen, setCurrentScreen] = useState<ScreenState>('LOGIN');
  const [backStack, setBackStack] = useState<ScreenState[]>([]);
  const [session, setSession] = useState<UserSession>({
    studentName: '',
    studentClass: '',
    studentAbsentNumber: '',
  });

  const [submissions, setSubmissions] = useState<QuizSubmission[]>([]);
  const [selectedAnswers, setSelectedAnswers] = useState<Record<number, number>>({});
  const [latestScore, setLatestScore] = useState(0);
  const [latestCorrectCount, setLatestCorrectCount] = useState(0);

  // Load session & submissions on mount
  useEffect(() => {
    const savedSession = StorageService.getUserSession();
    if (savedSession && savedSession.studentName) {
      setSession(savedSession);
      setCurrentScreen('HOME_DASHBOARD');
    }
    const savedSubs = StorageService.getAllSubmissions();
    setSubmissions(savedSubs);
  }, []);

  // Browser back button handling
  useEffect(() => {
    const handlePopState = () => {
      if (backStack.length > 0) {
        const prev = backStack[backStack.length - 1];
        setBackStack((old) => old.slice(0, old.length - 1));
        setCurrentScreen(prev);
      }
    };
    window.addEventListener('popstate', handlePopState);
    return () => window.removeEventListener('popstate', handlePopState);
  }, [backStack]);

  const navigateTo = (screen: ScreenState) => {
    if (screen !== currentScreen) {
      window.history.pushState({ screen }, '');
      setBackStack((prev) => [...prev, currentScreen]);
      setCurrentScreen(screen);
      window.scrollTo(0, 0);
    }
  };

  const navigateBack = () => {
    if (backStack.length > 0) {
      const prev = backStack[backStack.length - 1];
      setBackStack((old) => old.slice(0, old.length - 1));
      setCurrentScreen(prev);
      window.scrollTo(0, 0);
    } else {
      setCurrentScreen('HOME_DASHBOARD');
    }
  };

  const handleLogin = (nama: string, kelas: string, noAbsen: string) => {
    const newSession: UserSession = {
      studentName: nama,
      studentClass: kelas,
      studentAbsentNumber: noAbsen,
    };
    setSession(newSession);
    StorageService.saveUserSession(newSession);
    setBackStack([]);
    setCurrentScreen('HOME_DASHBOARD');
  };

  const handleLogout = () => {
    StorageService.clearUserSession();
    setSession({
      studentName: '',
      studentClass: '',
      studentAbsentNumber: '',
    });
    setBackStack([]);
    setCurrentScreen('LOGIN');
  };

  const handleStartNewQuiz = () => {
    setSelectedAnswers({});
    setLatestScore(0);
    setLatestCorrectCount(0);
    navigateTo('SOAL_QUIZ');
  };

  const handleSubmitQuiz = (answers: Record<number, number>) => {
    let correctCount = 0;
    const detailedAnswers: DetailedAnswer[] = [];

    QUESTIONS_DATA.forEach((question, index) => {
      const chosen = answers[index];
      const isCorrect = chosen === question.correctIndex;
      if (isCorrect) correctCount++;

      detailedAnswers.push({
        questionId: question.id,
        chosenOption: chosen !== undefined ? chosen : -1,
        correctOption: question.correctIndex,
        isCorrect,
      });
    });

    const total = QUESTIONS_DATA.length;
    const score = Math.round((correctCount / total) * 100);

    setLatestCorrectCount(correctCount);
    setLatestScore(score);
    setSelectedAnswers(answers);

    // Save to storage
    const newSubmission = StorageService.saveSubmission({
      studentName: session.studentName || 'Siswa Anonim',
      studentClass: session.studentClass || 'X',
      studentAbsentNumber: session.studentAbsentNumber || '0',
      score,
      totalQuestions: total,
      correctAnswersCount: correctCount,
      detailedAnswersJson: JSON.stringify(detailedAnswers),
      teacherName: 'YULIANTO HARSONO',
      completedAt: Date.now(),
    });

    setSubmissions((prev) => [newSubmission, ...prev]);
    navigateTo('QUIZ_RESULT');
  };

  const handleDeleteSubmission = (id: number) => {
    const updated = StorageService.deleteSubmission(id);
    setSubmissions(updated);
  };

  const handleClearAllSubmissions = () => {
    StorageService.clearAllSubmissions();
    setSubmissions([]);
  };

  return (
    <div className="min-h-screen bg-[#0A192F] text-[#F1F5F9] font-sans">
      {currentScreen === 'LOGIN' && (
        <LoginScreen onLoginSuccess={handleLogin} />
      )}

      {currentScreen === 'HOME_DASHBOARD' && (
        <DashboardScreen
          session={session}
          onNavigate={(target) => {
            if (target === 'SOAL_QUIZ') {
              handleStartNewQuiz();
            } else {
              navigateTo(target);
            }
          }}
          onLogout={handleLogout}
        />
      )}

      {currentScreen === 'MATERI' && (
        <MateriScreen
          onBack={navigateBack}
          onGoToSimulasi={() => navigateTo('SIMULASI')}
        />
      )}

      {currentScreen === 'SIMULASI' && (
        <SimulasiScreen
          onBack={navigateBack}
          onGoToQuiz={handleStartNewQuiz}
        />
      )}

      {currentScreen === 'SOAL_QUIZ' && (
        <SoalQuizScreen
          session={session}
          onBack={navigateBack}
          onSubmitQuiz={handleSubmitQuiz}
        />
      )}

      {currentScreen === 'QUIZ_RESULT' && (
        <QuizResultScreen
          session={session}
          latestScore={latestScore}
          latestCorrectCount={latestCorrectCount}
          selectedAnswers={selectedAnswers}
          submissions={submissions}
          onBackToHome={() => navigateTo('HOME_DASHBOARD')}
          onGoToDataSheet={() => navigateTo('DATA_SHEET')}
          onRetryQuiz={handleStartNewQuiz}
        />
      )}

      {currentScreen === 'DATA_SHEET' && (
        <DataSheetScreen
          submissions={submissions}
          onBack={navigateBack}
          onDeleteSubmission={handleDeleteSubmission}
          onClearAllSubmissions={handleClearAllSubmissions}
        />
      )}

      {currentScreen === 'TENTANG_PENGEMBANG' && (
        <TentangPengembangScreen onBack={navigateBack} />
      )}
    </div>
  );
};

export default App;
