package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DataSheetScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MateriScreen
import com.example.ui.screens.QuizResultScreen
import com.example.ui.screens.SimulasiScreen
import com.example.ui.screens.SoalQuizScreen
import com.example.ui.screens.TentangPengembangScreen
import com.example.ui.theme.MathNavy
import com.example.ui.theme.TrigonometriTheme
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrigonometriTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MathNavy
                ) {
                    AppNavigationRoot(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigationRoot(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val session by viewModel.userSession.collectAsState()

    // Handle back button for sub-screens
    if (currentScreen != ScreenState.LOGIN && currentScreen != ScreenState.HOME_DASHBOARD) {
        BackHandler {
            viewModel.navigateBack()
        }
    }

    when (currentScreen) {
        ScreenState.LOGIN -> {
            LoginScreen(
                onLoginSuccess = { nama, kelas, noAbsen ->
                    viewModel.login(nama, kelas, noAbsen)
                }
            )
        }

        ScreenState.HOME_DASHBOARD -> {
            DashboardScreen(
                session = session,
                onNavigate = { targetScreen ->
                    if (targetScreen == ScreenState.SOAL_QUIZ) {
                        viewModel.startNewQuiz()
                    } else {
                        viewModel.navigateTo(targetScreen)
                    }
                },
                onLogout = {
                    viewModel.logout()
                }
            )
        }

        ScreenState.MATERI -> {
            MateriScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateBack() },
                onGoToSimulasi = { viewModel.navigateTo(ScreenState.SIMULASI) }
            )
        }

        ScreenState.SIMULASI -> {
            SimulasiScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateBack() },
                onGoToQuiz = { viewModel.startNewQuiz() }
            )
        }

        ScreenState.SOAL_QUIZ -> {
            SoalQuizScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateBack() }
            )
        }

        ScreenState.QUIZ_RESULT -> {
            QuizResultScreen(
                viewModel = viewModel,
                onBackToHome = { viewModel.navigateTo(ScreenState.HOME_DASHBOARD) },
                onGoToDataSheet = { viewModel.navigateTo(ScreenState.DATA_SHEET) },
                onRetryQuiz = { viewModel.startNewQuiz() }
            )
        }

        ScreenState.DATA_SHEET -> {
            DataSheetScreen(
                viewModel = viewModel,
                onBack = { viewModel.navigateBack() }
            )
        }

        ScreenState.TENTANG_PENGEMBANG -> {
            TentangPengembangScreen(
                onBack = { viewModel.navigateBack() }
            )
        }
    }
}
