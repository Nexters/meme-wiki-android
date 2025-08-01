package com.mimu_bird.designsystem.typography

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mimu_bird.designsystem.theme.Body1
import com.mimu_bird.designsystem.theme.Body1_Point
import com.mimu_bird.designsystem.theme.Body2
import com.mimu_bird.designsystem.theme.Body_Long1
import com.mimu_bird.designsystem.theme.Body_Long2
import com.mimu_bird.designsystem.theme.Body_Long2_Point
import com.mimu_bird.designsystem.theme.Caption
import com.mimu_bird.designsystem.theme.Display1
import com.mimu_bird.designsystem.theme.Display1_Point
import com.mimu_bird.designsystem.theme.Display2
import com.mimu_bird.designsystem.theme.Display3
import com.mimu_bird.designsystem.theme.Display4
import com.mimu_bird.designsystem.theme.Display5
import com.mimu_bird.designsystem.theme.Headline1
import com.mimu_bird.designsystem.theme.Headline2
import com.mimu_bird.designsystem.theme.Subhead1
import com.mimu_bird.designsystem.theme.Subhead2
import com.mimu_bird.designsystem.theme.Subhead_Long1
import com.mimu_bird.designsystem.theme.Subhead_Long2

@Preview
@Composable
private fun TypographyPreview() {
    Column (
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        Text(
            text = "Display 5",
            style = Display5.toTextStyle()
        )
        Text(
            text = "Display 4",
            style = Display4.toTextStyle()
        )
        Text(
            text = "Display 3",
            style = Display3.toTextStyle()
        )
        Text(
            text = "Display 2",
            style = Display2.toTextStyle()
        )
        Text(
            text = "Display 1",
            style = Display1.toTextStyle()
        )
        Text(
            text = "Headline 1",
            style = Headline1.toTextStyle()
        )
        Text(
            text = "Headline 2",
            style = Headline2.toTextStyle()
        )
        Text(
            text = "Subhead 1",
            style = Subhead1.toTextStyle()
        )
        Text(
            text = "Subhead Long 1",
            style = Subhead_Long1.toTextStyle()
        )
        Text(
            text = "Subhead 2",
            style = Subhead2.toTextStyle()
        )
        Text(
            text = "Subhead Long 2",
            style = Subhead_Long2.toTextStyle()
        )
        Text(
            text = "Body 2",
            style = Body2.toTextStyle()
        )
        Text(
            text = "Body Long 2",
            style = Body_Long2.toTextStyle()
        )
        Text(
            text = "Body 1",
            style = Body1.toTextStyle()
        )
        Text(
            text = "Body Long 1",
            style = Body_Long1.toTextStyle()
        )
        Text(
            text = "Caption",
            style = Caption.toTextStyle()
        )
        Text(
            text = "Display 1 Point",
            style = Display1_Point.toTextStyle()
        )
        Text(
            text = "Body1 Point",
            style = Body1_Point.toTextStyle()
        )
        Text(
            text = "Body Long 2 Point",
            style = Body_Long2_Point.toTextStyle()
        )
    }
}