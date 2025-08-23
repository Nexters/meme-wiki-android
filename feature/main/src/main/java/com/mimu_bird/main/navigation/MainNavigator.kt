package com.mimu_bird.main.navigation

import com.mimu_bird.common.navigation.NavigationAction

/**
 * Main 모듈 네비게이션 액션 정의
 */
sealed class MainNavigationAction : NavigationAction() {
    object NavigateToMain : MainNavigationAction()
    object NavigateToSearch : MainNavigationAction()
    data class NavigateToCategory(val categoryId: Int) : MainNavigationAction()
    data class NavigateToDetail(
        val memeId: Int
    ) : MainNavigationAction()

    data class NavigateToWebView(
        val url: String
    ) : MainNavigationAction()
}

/**
 * Main 모듈 네비게이션 인터페이스
 */
interface MainNavigator {
    fun navigate(action: MainNavigationAction)
} 