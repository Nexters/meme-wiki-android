package com.mimu_bird.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.R
import com.mimu_bird.designsystem.theme.PastelGradientPalette
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.typography.toTextStyle


@Composable
fun CategoryView(drawableResId: Int, title: String, modifier: Modifier = Modifier) {
    Column {
        Column(Modifier.clip(RoundedCornerShape(8.dp))) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = drawableResId),
                contentDescription = "Category image",
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }
        Text(text = title, modifier = Modifier.padding(top = 8.dp), color = Color.White, style = Subhead2.toTextStyle())
    }
}

@Composable
@Preview
fun CategoryViewPreview() {
    Column(Modifier.fillMaxSize().background(Color.Black)) {
        CategoryView(
            drawableResId = R.drawable.business_products_magic_rabbit,
            title = "카테고리 이름",
            modifier = Modifier.fillMaxWidth(1f).aspectRatio(1f).background(
                brush = Brush.linearGradient(
                    listOf(
                        PastelGradientPalette.BLUE.leftTop,
                        PastelGradientPalette.BLUE.rightBottom
                    )
                ),
                alpha = 1f
            ).padding(15.dp)
        )
    }
}


