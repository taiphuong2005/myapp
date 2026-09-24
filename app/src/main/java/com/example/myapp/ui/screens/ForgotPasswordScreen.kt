package com.example.myapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.data.UserDatabaseHelper
import com.example.myapp.ui.components.AppTextField
import com.example.myapp.ui.components.PrimaryButton
import com.example.myapp.ui.components.StepIndicator
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.PrimaryBlue
import com.example.myapp.ui.theme.TextDark

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit = {},
    onNavigateToOtp: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { UserDatabaseHelper(context) }

    var emailOrPhone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun handleContinue() {
        if (emailOrPhone.isBlank()) {
            errorMessage = "Vui lòng nhập Email hoặc Số điện thoại!"
            return
        }

        if (!dbHelper.checkUserExists(emailOrPhone)) {
            errorMessage = "Email/Số điện thoại này chưa được đăng ký trong hệ thống!"
            return
        }

        errorMessage = null
        onNavigateToOtp(emailOrPhone.trim())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = TextDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "Quên mật khẩu",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Error Message Banner
            AnimatedVisibility(
                visible = errorMessage != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                errorMessage?.let { msg ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFEE2E2))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Lỗi",
                            tint = Color(0xFFDC2626),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = msg,
                            color = Color(0xFF991B1B),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Input Field
            AppTextField(
                value = emailOrPhone,
                onValueChange = {
                    emailOrPhone = it
                    errorMessage = null
                },
                placeholder = "Email\\Số điện thoại"
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Submit Button
            PrimaryButton(
                text = "Continue",
                onClick = { handleContinue() }
            )
        }

        // Bottom Indicator
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        ) {
            StepIndicator(activeStep = 0, totalSteps = 3)
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun ForgotPasswordScreenPreview() {
    MyappTheme {
        ForgotPasswordScreen()
    }
}
