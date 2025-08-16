package com.example.mymeme.ui.navigation

import com.mimu_bird.common.navigation.NavigationAction

/**
 * MyMeme 모듈 네비게이션 액션 정의
 */
sealed class MyMemeNavigationAction : NavigationAction() {
}

/**
 * MyMeme 모듈 네비게이션 인터페이스
 */
interface MyMemeNavigator {
    fun navigate(action: MyMemeNavigationAction)
}