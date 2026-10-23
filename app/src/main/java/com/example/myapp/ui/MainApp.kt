package com.example.myapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp.ui.components.LoadingOverlayScreen
import com.example.myapp.ui.screens.ForgotPasswordScreen
import com.example.myapp.ui.screens.HomeScreen
import com.example.myapp.ui.screens.LoginScreen
import com.example.myapp.ui.screens.OtpScreen
import com.example.myapp.ui.screens.PackageInclusionsScreen
import com.example.myapp.ui.screens.RegisterScreen
import com.example.myapp.ui.screens.ResetPasswordScreen
import com.example.myapp.ui.screens.ServiceDetailScreen
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.ScreenBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Screen {
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    OTP,
    RESET_PASSWORD,
    HOME,
    SERVICE_DETAIL,
    PACKAGE_INCLUSIONS
}

@Composable
fun MainApp() {
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
    var userEmailForReset by remember { mutableStateOf("") }
    var loggedInUserName by remember { mutableStateOf("Nguyễn Trần Tài Phương") }
    var selectedServiceTitle by remember { mutableStateOf("Dịch vụ chăm sóc xe") }
    var selectedPackageTitle by remember { mutableStateOf("Dịch vụ cơ bản") }
    var selectedPackagePrice by remember { mutableStateOf("800.000 VND") }
    var isNavigating by remember { mutableStateOf(false) }

    fun navigateTo(target: Screen, actionBeforeNav: () -> Unit = {}) {
        if (isNavigating) return
        scope.launch {
            isNavigating = true
            actionBeforeNav()
            delay(500) // Smooth loading transition duration
            currentScreen = target
            isNavigating = false
        }
    }

    // Handle system back button press
    BackHandler(enabled = (currentScreen != Screen.LOGIN && currentScreen != Screen.HOME) && !isNavigating) {
        val previousScreen = when (currentScreen) {
            Screen.REGISTER -> Screen.LOGIN
            Screen.FORGOT_PASSWORD -> Screen.LOGIN
            Screen.OTP -> Screen.FORGOT_PASSWORD
            Screen.RESET_PASSWORD -> Screen.OTP
            Screen.PACKAGE_INCLUSIONS -> Screen.SERVICE_DETAIL
            Screen.SERVICE_DETAIL -> Screen.HOME
            Screen.HOME -> Screen.LOGIN
            Screen.LOGIN -> Screen.LOGIN
        }
        navigateTo(previousScreen)
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
                        navigateTo(Screen.REGISTER)
                    },
                    onNavigateToForgotPassword = {
                        navigateTo(Screen.FORGOT_PASSWORD)
                    },
                    onLoginSuccess = { user ->
                        navigateTo(Screen.HOME) {
                            loggedInUserName = if (user.name.isNotBlank()) user.name else "Nguyễn Trần Tài Phương"
                        }
                    }
                )

                Screen.HOME -> HomeScreen(
                    userName = loggedInUserName,
                    onLogout = {
                        navigateTo(Screen.LOGIN)
                    },
                    onSelectService = { serviceTitle ->
                        navigateTo(Screen.SERVICE_DETAIL) {
                            selectedServiceTitle = serviceTitle
                        }
                    }
                )

                Screen.SERVICE_DETAIL -> ServiceDetailScreen(
                    serviceTitle = selectedServiceTitle,
                    onBack = {
                        navigateTo(Screen.HOME)
                    },
                    onSelectPackage = { pkgTitle, pkgPrice ->
                        navigateTo(Screen.PACKAGE_INCLUSIONS) {
                            selectedPackageTitle = pkgTitle
                            selectedPackagePrice = pkgPrice
                        }
                    }
                )

                Screen.PACKAGE_INCLUSIONS -> PackageInclusionsScreen(
                    packageTitle = selectedPackageTitle,
                    packagePrice = selectedPackagePrice,
                    onBack = {
                        navigateTo(Screen.SERVICE_DETAIL)
                    }
                )

                Screen.REGISTER -> RegisterScreen(
                    onNavigateToLogin = {
                        navigateTo(Screen.LOGIN)
                    },
                    onRegisterSuccess = {
                        navigateTo(Screen.LOGIN)
                    }
                )

                Screen.FORGOT_PASSWORD -> ForgotPasswordScreen(
                    onBack = {
                        navigateTo(Screen.LOGIN)
                    },
                    onNavigateToOtp = { emailOrPhone ->
                        navigateTo(Screen.OTP) {
                            userEmailForReset = emailOrPhone
                        }
                    }
                )

                Screen.OTP -> OtpScreen(
                    onBack = {
                        navigateTo(Screen.FORGOT_PASSWORD)
                    },
                    onNavigateToResetPassword = {
                        navigateTo(Screen.RESET_PASSWORD)
                    }
                )

                Screen.RESET_PASSWORD -> ResetPasswordScreen(
                    emailOrPhone = userEmailForReset,
                    onBack = {
                        navigateTo(Screen.OTP)
                    },
                    onResetSuccess = {
                        navigateTo(Screen.LOGIN)
                    }
                )
            }

            // Dedicated Loading Screen Overlay during navigation
            AnimatedVisibility(
                visible = isNavigating,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LoadingOverlayScreen()
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
