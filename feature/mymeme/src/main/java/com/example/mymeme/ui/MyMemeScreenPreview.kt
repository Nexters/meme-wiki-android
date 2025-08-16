package com.example.mymeme.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun MyMemeScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MyMemeScreen(imgUrl = "https://i.namu.wiki/i/iDzb5TjuM88VOPX2HsrHkCS_y8JPiK5T5hcfwBkjBPb0uVypaNNOuQQXpjQU8VihDRUcr_cUpXGNTw1x8hcQbi4ifSM8f9bMLXELNMplFJthXDwIt2cHcVWLcROtql-P_I1j9ZczBMRr0iRNRISyHw.webp")
        }
    }
}