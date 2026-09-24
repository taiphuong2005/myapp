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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.myapp.data.RegisterResult
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
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val dbHelper = remember { UserDatabaseHelper(context) }

    var name by remember { mutableStateOf("") }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun openSocialLogin(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun handleRegister() {
        when (val result = dbHelper.registerUser(name, emailOrPhone, password, isAgreed)) {
            is RegisterResult.EmptyFields -> {
                errorMessage = "Vui lòng điền đầy đủ Tên, Email/SĐT và Mật khẩu!"
            }
            is RegisterResult.TermsNotAgreed -> {
                errorMessage = "Bạn phải đồng ý với Điều khoản dịch vụ và Chính sách bảo mật!"
            }
            is RegisterResult.AlreadyExists -> {
                errorMessage = "Email/Số điện thoại này đã được đăng ký tài khoản!"
            }
            is RegisterResult.Error -> {
                errorMessage = result.message
            }
            is RegisterResult.Success -> {
                errorMessage = null
                Toast.makeText(context, "Đăng ký thành công! Vui lòng đăng nhập.", Toast.LENGTH_SHORT).show()
                onRegisterSuccess()
            }
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
            Spacer(modifier = Modifier.height(28.dp))

            // Title
            Text(
                text = "Đăng Ký",
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

            // Form Inputs
            AppTextField(
                value = name,
                onValueChange = {
                    name = it
                    errorMessage = null
                },
                placeholder = "Tên"
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppTextField(
                value = emailOrPhone,
                onValueChange = {
                    emailOrPhone = it
                    errorMessage = null
                },
                placeholder = "Email/Số điện thoại"
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                placeholder = "Mật Khẩu"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Terms Agreement Checkbox
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isAgreed = !isAgreed
                        errorMessage = null
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAgreed,
                    onCheckedChange = {
                        isAgreed = it
                        errorMessage = null
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimaryBlue,
                        uncheckedColor = TextMuted
                    )
                )
                Text(
                    text = "Tôi đồng ý với Điều khoản dịch vụ và Chính sách bảo mật",
                    fontSize = 11.sp,
                    color = TextMuted,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Button
            PrimaryButton(
                text = "Tạo tài khoản",
                onClick = { handleRegister() }
            )
        }

        // Bottom Navigation Link
        Box(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Bạn đã có tài khoản ? ")
                    withStyle(style = SpanStyle(color = PrimaryBlue, fontWeight = FontWeight.Bold)) {
                        append("Đăng nhập")
                    }
                },
                fontSize = 13.sp,
                color = TextDark,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 700)
@Composable
fun RegisterScreenPreview() {
    MyappTheme {
        RegisterScreen()
    }
}
