package com.example.myapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.PrimaryBlue
import com.example.myapp.ui.theme.ScreenBackground
import com.example.myapp.ui.theme.TextDark
import com.example.myapp.ui.theme.TextMuted

data class ServicePackage(
    val title: String,
    val kmPeriod: String,
    val timeDuration: String,
    val warranty: String,
    val includesCount: String,
    val price: String,
    val icon: ImageVector
)

@Composable
fun ServiceDetailScreen(
    serviceTitle: String = "Dịch vụ chăm sóc xe",
    onBack: () -> Unit = {},
    onSelectPackage: (String, String) -> Unit = { _, _ -> }
) {
    val packages = listOf(
        ServicePackage(
            title = "Dịch vụ cơ bản",
            kmPeriod = "Mỗi 5000 Km/3 tháng",
            timeDuration = "Mất 4 giờ",
            warranty = "Bảo hành 1 tháng",
            includesCount = "Bao gồm 9 dịch vụ",
            price = "800.000 VND",
            icon = Icons.Default.CarRepair
        ),
        ServicePackage(
            title = "Dịch vụ tiêu chuẩn",
            kmPeriod = "Mỗi 10000 Km/6 tháng",
            timeDuration = "Mất 6 giờ",
            warranty = "Bảo hành 1 tháng",
            includesCount = "Bao gồm 15 dịch vụ",
            price = "1.200.000 VND",
            icon = Icons.Default.Engineering
        ),
        ServicePackage(
            title = "Dịch vụ toàn diện",
            kmPeriod = "Mỗi 20000 Km/1 Năm",
            timeDuration = "Mất 8 giờ",
            warranty = "Bảo hành 1 tháng",
            includesCount = "Bao gồm 20 dịch vụ",
            price = "1.750.000 VND",
            icon = Icons.Default.Build
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Top Navigation Bar
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = serviceTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Service Packages List
        packages.forEach { pkg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable { onSelectPackage(pkg.title, pkg.price) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Left Column: Details
                    Column(
                        modifier = Modifier.weight(1.3f)
                    ) {
                        Text(
                            text = pkg.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Bullet Points
                        Text("•  ${pkg.kmPeriod}", fontSize = 12.sp, color = TextMuted)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text("•  ${pkg.timeDuration}", fontSize = 12.sp, color = TextMuted)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text("•  ${pkg.warranty}", fontSize = 12.sp, color = TextMuted)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text("•  ${pkg.includesCount}", fontSize = 12.sp, color = TextMuted)

                        Spacer(modifier = Modifier.height(14.dp))

                        // Price
                        Text(
                            text = pkg.price,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Right Column: Image Graphic + "THÊM" Button
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.weight(0.9f)
                    ) {
                        // Graphic Box
                        Box(
                            modifier = Modifier
                                .size(110.dp, 90.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF1F5F9)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = pkg.icon,
                                contentDescription = pkg.title,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // "THÊM" Button
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.5.dp, PrimaryBlue, RoundedCornerShape(8.dp))
                                .clickable { onSelectPackage(pkg.title, pkg.price) }
                                .padding(horizontal = 20.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "THÊM",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceDetailScreenPreview() {
    MyappTheme {
        ServiceDetailScreen()
    }
}
