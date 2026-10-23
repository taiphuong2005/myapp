package com.example.myapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.theme.MyappTheme
import com.example.myapp.ui.theme.PrimaryBlue
import com.example.myapp.ui.theme.ScreenBackground
import com.example.myapp.ui.theme.TextDark
import com.example.myapp.ui.theme.TextMuted
import kotlinx.coroutines.launch

data class HighlightItem(
    val title: String,
    val icon: ImageVector
)

data class ReviewItem(
    val author: String,
    val rating: String,
    val comment: String
)

@Composable
fun PackageInclusionsScreen(
    packageTitle: String = "Dịch vụ cơ bản",
    packagePrice: String = "800.000 VND",
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val highlights = listOf(
        HighlightItem("Mất 4 giờ", Icons.Default.DirectionsCar),
        HighlightItem("Mỗi 5000 Km hoặc 3 tháng", Icons.Default.Shield),
        HighlightItem("Đảm bảo chất lượng tốt nhất", Icons.Default.ThumbUp),
        HighlightItem("Đến nhận hoặc trả miễn phí", Icons.Default.Schedule)
    )

    val inclusions = listOf(
        "Thay dầu động cơ",
        "Thay thế bộ lọc dầu",
        "Vệ sinh bộ lọc khí",
        "Nạp thêm chất làm mát",
        "Thay thế chất lỏng gạt nước",
        "Nạp dầu cho ắc quy",
        "Kiểm tra bộ phận sưởi ấm/Bugi đánh lửa",
        "Rửa xe",
        "Hút bụi nội thất (Thảm và ghế)"
    )

    val reviews = listOf(
        ReviewItem(
            author = "Nguyễn văn thương",
            rating = "5.0",
            comment = "Gói dịch vụ cơ bản là lựa chọn tốt để giữ mọi thứ bình thường trong tầm kiểm soát. Rất khuyến khích!"
        ),
        ReviewItem(
            author = "Trần Bảo Hoàng",
            rating = "4.8",
            comment = "Chất lượng dịch vụ rất chu đáo, nhân viên làm việc cẩn thận, đúng giờ. Rất hài lòng!"
        ),
        ReviewItem(
            author = "Lê Hoàng Nam",
            rating = "5.0",
            comment = "Rửa xe và hút bụi rất sạch sẽ, xe chạy êm hơn sau khi thay dầu động cơ."
        ),
        ReviewItem(
            author = "Đặng Tuấn Anh",
            rating = "4.9",
            comment = "Đặt lịch cực kỳ tiện lợi, kỹ thuật viên nhiệt tình tư vấn kiểm tra tổng thể xe."
        )
    )

    val lazyListState = rememberLazyListState()
    val activeIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }

    Scaffold(
        bottomBar = {
            // Sticky Bottom Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(width = 1.dp, color = Color(0xFFE2E8F0))
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = packageTitle,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = packagePrice,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Đã thêm $packageTitle ($packagePrice) vào giỏ hàng!",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text(
                        text = "THÊM",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ScreenBackground)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Top Header
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
                    text = packageTitle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Highlights List
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                highlights.forEach { highlight ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFDBEAFE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = highlight.icon,
                                contentDescription = highlight.title,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = highlight.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))

            // Inclusions Title
            Text(
                text = "Bao gồm những gì",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Inclusions Check List
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                inclusions.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Bao gồm",
                            tint = Color(0xFF16A34A), // Green check mark
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = item,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reviews Section Header with Interactive Navigation Arrow Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Đánh giá",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                // Arrow buttons for next/previous review
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Previous Review Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEFF6FF))
                            .border(1.dp, PrimaryBlue.copy(alpha = 0.3f), CircleShape)
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    val prevIndex = (lazyListState.firstVisibleItemIndex - 1).coerceAtLeast(0)
                                    lazyListState.animateScrollToItem(prevIndex)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Bình luận trước",
                                tint = PrimaryBlue
                            )
                        }
                    }

                    // Next Review Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue)
                            .padding(4.dp)
                            .align(Alignment.CenterVertically),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    val nextIndex = (lazyListState.firstVisibleItemIndex + 1).coerceAtMost(reviews.size - 1)
                                    lazyListState.animateScrollToItem(nextIndex)
                                }
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Bình luận tiếp theo",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Horizontal Scrollable Reviews List
            LazyRow(
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(end = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(reviews) { _, review ->
                    Card(
                        modifier = Modifier
                            .width(260.dp)
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = review.author,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    repeat(5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Rating",
                                            tint = Color(0xFFEAB308),
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = review.comment,
                                fontSize = 11.sp,
                                color = TextMuted,
                                lineHeight = 15.sp,
                                maxLines = 4
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Page Indicator Dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(reviews.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .size(if (index == activeIndex) 7.dp else 5.dp)
                            .clip(CircleShape)
                            .background(if (index == activeIndex) PrimaryBlue else Color(0xFFCBD5E1))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PackageInclusionsScreenPreview() {
    MyappTheme {
        PackageInclusionsScreen()
    }
}
