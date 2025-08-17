package com.mimu_bird.ui.model

data class BriefMemeUiModel(
    val id: String, // 밈 식별자
    val imageUrl: String, // 밈 이미지 Url
    val title: String, // 밈 타이틀
    val rank: Int, //밈 순위
)

val TEST_BRIEF_MEME_UI = BriefMemeUiModel(
    id = "0",
    imageUrl = "https://i.namu.wiki/i/iDzb5TjuM88VOPX2HsrHkCS_y8JPiK5T5hcfwBkjBPb0uVypaNNOuQQXpjQU8VihDRUcr_cUpXGNTw1x8hcQbi4ifSM8f9bMLXELNMplFJthXDwIt2cHcVWLcROtql-P_I1j9ZczBMRr0iRNRISyHw.webp",
    title = "미안하다 이거 보여주려고 어그로끌었다",
    rank = 1
)
