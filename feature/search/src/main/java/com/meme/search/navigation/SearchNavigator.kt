package com.meme.search.navigation

import com.mimu_bird.common.navigation.NavigationAction

/**
 * Search 모듈 네비게이션 액션 정의
 */
sealed class SearchNavigationAction : NavigationAction() {
    object NavigateToDetail : SearchNavigationAction()
}

/**
 * Search 모듈 네비게이션 인터페이스
 */
interface SearchNavigator {
    fun navigate(action: SearchNavigationAction)
} 