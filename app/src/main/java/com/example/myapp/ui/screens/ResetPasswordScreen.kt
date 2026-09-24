package com.example.myapp.ui.screens

import android.widget.Toast
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
import com.example.myapp.ui.components.PasswordTextField
import com.example.myapp.ui.components.PrimaryButton
import com.example.myapp.ui.components.StepIndicator
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.PrimaryBlue
import com.example.myapp.ui.theme.TextDark

@Composable
fun ResetPasswordScreen(
    emailOrPhone: String = "",
    onBack: () -> Unit = {},
    onResetSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { UserDatabaseHelper(context) }

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    fun handleResetPassword() {
        if (newPassword.isBlank() || confirmPassword.isBlank()) {
            errorMessage = "Vui lòng nhập đầy đủ mật khẩu mới!"
            return
        }
        if (newPassword != confirmPassword) {
            errorMessage = "Mật khẩu xác nhận không khớp!"
            return
        }

        isLoading = true
        val updated = dbHelper.updatePassword(emailOrPhone, newPassword)
        isLoading = false

        if (updated) {
            errorMessage = null
            Toast.makeText(context, "Đặt lại mật khẩu thành công! Vui lòng đăng nhập.", Toast.LENGTH_SHORT).show()
            onResetSuccess()
        } else {
            errorMessage = "Đặt lại mật khẩu thất bại. Vui lòng thử lại!"
        }
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
                text = "Thay đổi mật khẩu",
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

            // Input Fields
            PasswordTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    errorMessage = null
                },
                placeholder = "Mật khẩu mới"
            )

            Spacer(modifier = Modifier.height(14.dp))

            PasswordTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = null
                },
                placeholder = "Nhập lại mật khẩu"
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Submit Button
            PrimaryButton(
                text = if (isLoading) "Đang yêu cầu" else "Thay đổi mật khẩu",
                isLoading = isLoading,
                onClick = { handleResetPassword() }
            )
        }

        // Bottom Indicator
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth()
        ) {
            StepIndicator(activeStep = 1, totalSteps = 3)
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun ResetPasswordScreenPreview() {
    MyappTheme {
        ResetPasswordScreen()
    }
}
