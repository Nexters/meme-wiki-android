package com.mimu_bird.ui.model

/**
 * 밈 정보 Ui Model
 */
data class MimUiModel(
    val id: String, // 밈 식별자
    val imageUrl: String, // 밈 이미지 Url
    val year: Int, // 유행 연도
    val title: String, // 밈 제목
    val tags: List<String>, // 해시 태그
    val usage: String, // 용도
    val source: String // 유래
)

val TEST_MEME = MimUiModel(
    id = "",
    imageUrl = "https://picsum.photos/seed/picsum/200/300",
    year = 2022,
    title = "죽겠어요",
    tags = listOf("직장인", "해탈짤"),
    usage = "원티드랩은 차별화된 데이터를 기반으로 각자에게 가장 잘 맞는 커리어 경로를 설계하고 커리어 성장 경험을 제공하여, 모두가 나답게 일하고 즐겁게 성장할 수 있도록 돕는 HR 테크 회사입니다.  UX 부문 프로덕트 디자이너는 각 팀에서 담당하고 있는 제품의 사용자 경험을 책임집니다. 원티드 유저가 제품을 사용하면서 느끼는 페인포인트를 정성, 정량적으로 분석하고, 개선하며 제품의 퀄리티를 높이고 있습니다. 퀄리티를 높이고 있습니다.\n" +
            "퀄리티를 높이고 있습니다. 퀄리티를 높이고 있습니다.\n" +
            "퀄리티를 높이고 있습니다. 퀄리티를 높이고 있습니다.",
    source = "원티드랩은 차별화된 데이터를 기반으로 각자에게 가장 잘 맞는 커리어 경로를 설계하고 커리어 성장 경험을 제공하여, 모두가 나답게 일하고 즐겁게 성장할 수 있도록 돕는 HR 테크 회사입니다. "
)