package com.example.myapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.data.LoginResult
import com.example.myapp.data.User
import com.example.myapp.data.UserDatabaseHelper
import com.example.myapp.ui.components.AppTextField
import com.example.myapp.ui.components.OrDivider
import com.example.myapp.ui.components.PasswordTextField
import com.example.myapp.ui.components.PrimaryButton
import com.example.myapp.ui.components.SocialButton
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.PrimaryBlue
import com.example.myapp.ui.theme.TextDark
import com.example.myapp.ui.theme.TextMuted

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onLoginSuccess: (User) -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { UserDatabaseHelper(context) }

    var emailOrPhone by remember { mutableStateOf("nguyentrantaiphuong@gmail.com") }
    var password by remember { mutableStateOf("12345678") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun openSocialLogin(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun handleLogin() {
        when (val result = dbHelper.loginUser(emailOrPhone, password)) {
            is LoginResult.EmptyFields -> {
                errorMessage = "Vui lòng nhập đầy đủ Email/SĐT và Mật khẩu!"
            }
            is LoginResult.UserNotFound -> {
                errorMessage = "Tài khoản không tồn tại! Vui lòng kiểm tra lại Email/Số điện thoại."
            }
            is LoginResult.WrongPassword -> {
                errorMessage = "Mật khẩu không chính xác! Vui lòng thử lại."
            }
            is LoginResult.Success -> {
                errorMessage = null
                Toast.makeText(context, "Đăng nhập thành công! Xin chào ${result.user.name}", Toast.LENGTH_SHORT).show()
                onLoginSuccess(result.user)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))

        // Title
        Text(
            text = "Đăng nhập",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Social Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SocialButton(
                iconRes = R.drawable.ic_facebook,
                text = "Facebook",
                onClick = { openSocialLogin("https://m.facebook.com/login/") },
                modifier = Modifier.weight(1f)
            )
            SocialButton(
                iconRes = R.drawable.ic_google,
                text = "Google",
                onClick = { openSocialLogin("https://accounts.google.com/ServiceLogin") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Or Divider
        OrDivider()

        Spacer(modifier = Modifier.height(18.dp))

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
        AppTextField(
            value = emailOrPhone,
            onValueChange = {
                emailOrPhone = it
                errorMessage = null
            },
            placeholder = "Email/Số điện thoại"
        )

        Spacer(modifier = Modifier.height(14.dp))

        PasswordTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            placeholder = "Mật Khẩu"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password Link
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "Quên mật khẩu",
                fontSize = 12.sp,
                color = TextMuted,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .clickable { onNavigateToForgotPassword() }
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Submit Button
        PrimaryButton(
            text = "Đăng nhập",
            onClick = { handleLogin() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Register Link moved directly under the login button
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Bạn không có tài khoản?? ")
                    withStyle(style = SpanStyle(color = PrimaryBlue, fontWeight = FontWeight.Bold)) {
                        append("Đăng ký")
                    }
                },
                fontSize = 13.sp,
                color = TextDark,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun LoginScreenPreview() {
    MyappTheme {
        LoginScreen()
    }
}
