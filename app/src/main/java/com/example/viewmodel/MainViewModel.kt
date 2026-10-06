package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.QuizSubmission
import com.example.model.LearningMaterials
import com.example.model.MaterialSection
import com.example.model.Question
import com.example.model.QuizRepository
import com.example.model.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

enum class ScreenState {
    LOGIN,
    HOME_DASHBOARD,
    MATERI,
    SIMULASI,
    SOAL_QUIZ,
    QUIZ_RESULT,
    DATA_SHEET,
    TENTANG_PENGEMBANG
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.quizSubmissionDao()

    // Database flow for data sheet
    val submissions: StateFlow<List<QuizSubmission>> = dao.getAllSubmissions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Current screen navigation
    private val _currentScreen = MutableStateFlow(ScreenState.LOGIN)
    val currentScreen: StateFlow<ScreenState> = _currentScreen.asStateFlow()

    // Navigation backstack for smooth back handler
    private val backStack = mutableListOf<ScreenState>()

    // User session
    private val _userSession = MutableStateFlow(UserSession())
    val userSession: StateFlow<UserSession> = _userSession.asStateFlow()

    // Simulation state (Menghitung tinggi pohon kelapa)
    private val _distanceToTree = MutableStateFlow(15.0f) // meters (5m to 40m)
    val distanceToTree: StateFlow<Float> = _distanceToTree.asStateFlow()

    private val _observerHeight = MutableStateFlow(1.6f) // meters (1.2m to 2.0m)
    val observerHeight: StateFlow<Float> = _observerHeight.asStateFlow()

    private val _elevationAngle = MutableStateFlow(45.0f) // degrees (15° to 75°)
    val elevationAngle: StateFlow<Float> = _elevationAngle.asStateFlow()

    private val _simulationChallengeActive = MutableStateFlow(false)
    val simulationChallengeActive: StateFlow<Boolean> = _simulationChallengeActive.asStateFlow()

    private val _targetTreeHeight = MutableStateFlow(18.5f) // meters challenge target
    val targetTreeHeight: StateFlow<Float> = _targetTreeHeight.asStateFlow()

    // Quiz state
    val questions: List<Question> = QuizRepository.questions
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    // user answers map: question index -> chosen option index (0..4)
    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, Int>> = _selectedAnswers.asStateFlow()

    private val _quizCompleted = MutableStateFlow(false)
    val quizCompleted: StateFlow<Boolean> = _quizCompleted.asStateFlow()

    private val _latestScore = MutableStateFlow(0)
    val latestScore: StateFlow<Int> = _latestScore.asStateFlow()

    private val _latestCorrectCount = MutableStateFlow(0)
    val latestCorrectCount: StateFlow<Int> = _latestCorrectCount.asStateFlow()

    // Learning materials
    val materials: List<MaterialSection> = LearningMaterials.sections
    private val _selectedMaterialIndex = MutableStateFlow(0)
    val selectedMaterialIndex: StateFlow<Int> = _selectedMaterialIndex.asStateFlow()

    // Export / Share status message
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun navigateTo(screen: ScreenState) {
        if (_currentScreen.value != screen) {
            backStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (backStack.isNotEmpty()) {
            val prev = backStack.removeAt(backStack.size - 1)
            _currentScreen.value = prev
            return true
        }
        return false
    }

    fun login(nama: String, kelas: String, noAbsen: String) {
        _userSession.value = UserSession(
            studentName = nama.trim(),
            studentClass = kelas.trim(),
            studentAbsentNumber = noAbsen.trim()
        )
        navigateTo(ScreenState.HOME_DASHBOARD)
    }

    fun logout() {
        _userSession.value = UserSession()
        backStack.clear()
        _currentScreen.value = ScreenState.LOGIN
    }

    // Simulation methods
    fun updateDistance(distance: Float) {
        _distanceToTree.value = distance
    }

    fun updateObserverHeight(height: Float) {
        _observerHeight.value = height
    }

    fun updateElevationAngle(angle: Float) {
        _elevationAngle.value = angle
    }

    fun setSimulationChallenge(active: Boolean, targetHeight: Float = 20.0f) {
        _simulationChallengeActive.value = active
        _targetTreeHeight.value = targetHeight
    }

    fun calculateTreeHeight(): Double {
        val d = _distanceToTree.value.toDouble()
        val hObs = _observerHeight.value.toDouble()
        val rad = Math.toRadians(_elevationAngle.value.toDouble())
        val triangleOpposite = d * Math.tan(rad)
        return hObs + triangleOpposite
    }

    // Material methods
    fun selectMaterial(index: Int) {
        _selectedMaterialIndex.value = index
    }

    // Quiz methods
    fun startNewQuiz() {
        _currentQuestionIndex.value = 0
        _selectedAnswers.value = emptyMap()
        _quizCompleted.value = false
        _latestScore.value = 0
        _latestCorrectCount.value = 0
        navigateTo(ScreenState.SOAL_QUIZ)
    }

    fun selectAnswer(questionIndex: Int, optionIndex: Int) {
        val map = _selectedAnswers.value.toMutableMap()
        map[questionIndex] = optionIndex
        _selectedAnswers.value = map
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < questions.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun prevQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun jumpToQuestion(index: Int) {
        if (index in questions.indices) {
            _currentQuestionIndex.value = index
        }
    }

    fun submitQuiz() {
        var correctCount = 0
        val detailedJsonArray = JSONArray()

        questions.forEachIndexed { index, question ->
            val chosen = _selectedAnswers.value[index]
            val isCorrect = chosen == question.correctIndex
            if (isCorrect) correctCount++

            val obj = JSONObject()
            obj.put("questionId", question.id)
            obj.put("chosenOption", chosen ?: -1)
            obj.put("correctOption", question.correctIndex)
            obj.put("isCorrect", isCorrect)
            detailedJsonArray.put(obj)
        }

        val total = questions.size
        val score = (correctCount.toDouble() / total * 100).toInt()

        _latestCorrectCount.value = correctCount
        _latestScore.value = score
        _quizCompleted.value = true

        val session = _userSession.value
        val submission = QuizSubmission(
            studentName = session.studentName.ifBlank { "Siswa Anonim" },
            studentClass = session.studentClass.ifBlank { "X" },
            studentAbsentNumber = session.studentAbsentNumber.ifBlank { "0" },
            score = score,
            totalQuestions = total,
            correctAnswersCount = correctCount,
            detailedAnswersJson = detailedJsonArray.toString(),
            teacherName = "YULIANTO HARSONO",
            completedAt = System.currentTimeMillis()
        )

        viewModelScope.launch {
            dao.insertSubmission(submission)
        }

        navigateTo(ScreenState.QUIZ_RESULT)
    }

    fun deleteSubmission(id: Long) {
        viewModelScope.launch {
            dao.deleteSubmissionById(id)
        }
    }

    fun clearAllSubmissions() {
        viewModelScope.launch {
            dao.clearAllSubmissions()
        }
    }

    fun setStatusMessage(msg: String?) {
        _statusMessage.value = msg
    }
}
