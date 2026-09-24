package com.example.myapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp.ui.screens.ForgotPasswordScreen
import com.example.myapp.ui.screens.LoginScreen
import com.example.myapp.ui.screens.OtpScreen
import com.example.myapp.ui.screens.RegisterScreen
import com.example.myapp.ui.screens.ResetPasswordScreen
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.ScreenBackground

enum class Screen {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    OTP,
    RESET_PASSWORD
}

@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
    var userEmailForReset by remember { mutableStateOf("") }

    // Handle system back button press
    BackHandler(enabled = currentScreen != Screen.LOGIN) {
        currentScreen = when (currentScreen) {
            Screen.REGISTER -> Screen.LOGIN
            Screen.FORGOT_PASSWORD -> Screen.LOGIN
            Screen.OTP -> Screen.FORGOT_PASSWORD
            Screen.RESET_PASSWORD -> Screen.OTP
            Screen.LOGIN -> Screen.LOGIN
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ScreenBackground)
        ) {
            when (currentScreen) {
                Screen.LOGIN -> LoginScreen(
                    onNavigateToRegister = {
                        currentScreen = Screen.REGISTER
                    },
                    onNavigateToForgotPassword = {
                        currentScreen = Screen.FORGOT_PASSWORD
                    },
                    onLoginSuccess = { user ->
                        // Navigate or perform action after successful login
                    }
                )

                Screen.REGISTER -> RegisterScreen(
                    onNavigateToLogin = {
                        currentScreen = Screen.LOGIN
                    },
                    onRegisterSuccess = {
                        currentScreen = Screen.LOGIN
                    }
                )

                Screen.FORGOT_PASSWORD -> ForgotPasswordScreen(
                    onBack = {
                        currentScreen = Screen.LOGIN
                    },
                    onNavigateToOtp = { emailOrPhone ->
                        userEmailForReset = emailOrPhone
                        currentScreen = Screen.OTP
                    }
                )

                Screen.OTP -> OtpScreen(
                    onBack = {
                        currentScreen = Screen.FORGOT_PASSWORD
                    },
                    onNavigateToResetPassword = {
                        currentScreen = Screen.RESET_PASSWORD
                    }
                )

                Screen.RESET_PASSWORD -> ResetPasswordScreen(
                    emailOrPhone = userEmailForReset,
                    onBack = {
                        currentScreen = Screen.OTP
                    },
                    onResetSuccess = {
                        currentScreen = Screen.LOGIN
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    MyappTheme {
        MainApp()
    }
}
